package databases;

import com.google.inject.Singleton;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import play.Configuration;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.typesafe.config.ConfigFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlayMorphia implements Provider<Datastore> {

    private String mongodbUri = "mongodb.uri";
    protected static final String MORPHIA_PACKAGE = "models";
    protected static final String ENVIRONMENT = "ENVIRONMENT";
    protected static final String MONGODB_CONNECTION_POOL_SIZE = "mongodb.connection.pool.size";
    protected static final String MONGODB_SOCKET_TIMEOUT_MS = "MONGODB_SOCKET_TIMEOUT_MS";
    protected static final int DEFAULT_CONNECTIONS_PER_HOST = 100;
    protected static final int DEFAULT_SOCKET_TIMEOUT_MS = 10 * 1000;
    protected static final String URI_HAS_BEEN_MASKED = "**** URI HAS BEEN MASKED ****";

    protected Configuration configuration;
    protected Logger logger = LoggerFactory.getLogger(PlayMorphia.class);

    @Inject
    public PlayMorphia(Configuration configuration, String mongoDBUri) {
        this.configuration = configuration;
        this.mongodbUri = mongoDBUri;
        Preconditions.checkNotNull(configuration.getString(mongodbUri), mongodbUri+" not provided");
    }

    public Datastore get() {
        Preconditions.checkNotNull(configuration.getString(mongodbUri), mongodbUri+" not provided");
        String mongoDBUris = configuration.getString(mongodbUri);

        final Morphia morphia = new Morphia();
        morphia.mapPackage(MORPHIA_PACKAGE);

        List<ServerAddress> serverAddressesList = Lists.newArrayList();
        List<MongoClientURI> mongoClientURIsList = Lists.newArrayList();

        for (String uri : mongoDBUris.split(",")) {

            System.out.println("uri : " + uri);
            final MongoClientURI mongoClient = new MongoClientURI(uri);
            mongoClientURIsList.add(mongoClient);
            serverAddressesList.add(new ServerAddress(mongoClient.getHosts().get(0)));
        }
        final Datastore datastore = getDatastore(morphia, mongoClientURIsList, serverAddressesList);
        datastore.ensureIndexes();

        logger.info("successfully connected to MongoDB " + printSafe(mongoDBUris));
        return datastore;
    }

    protected Datastore getDatastore(Morphia morphia, List<MongoClientURI> mongoClientURIsList,
            List<ServerAddress> addrs) {

        // For standalone/single server
        if (addrs.size() == 1) {
            Datastore a = null;
            try {

                a = morphia.createDatastore(
                        new MongoClient(addrs.get(0), this.getMongoCredentials(mongoClientURIsList),
                                this.getMongoClientOptions(mongoClientURIsList.get(0))),
                        mongoClientURIsList.get(0).getDatabase());
            } catch (Exception e) {
                System.out.println("hello" + e.getMessage());

                e.printStackTrace();
            }
            return a;
        }

        // For replica set servers
        return morphia.createDatastore(
                new MongoClient(addrs, this.getMongoCredentials(mongoClientURIsList),
                        this.getMongoClientOptions(mongoClientURIsList.get(0))),
                mongoClientURIsList.get(0).getDatabase());
    }

    protected MongoClientOptions getMongoClientOptions(MongoClientURI uri) {

        MongoClientOptions.Builder builder = MongoClientOptions.builder();

        if (!isLocalEnvironment()) {

            builder.sslEnabled(true).build();

            logger.info("force enabling SSL for MongoDB " + printSafe(uri));

            builder.sslInvalidHostNameAllowed(false).build();

            logger.info("force disallowing invalid hostnames for MongoDB " + printSafe(uri));

            builder.writeConcern(getWriteConcern(uri));

            builder.connectionsPerHost(getConnectionsPerHost(uri));

            builder.socketTimeout(getSocketTimeout(uri));
        }
        return builder.build();
    }

    protected int getSocketTimeout(MongoClientURI uri) {
        Integer socketTimeoutMs = DEFAULT_SOCKET_TIMEOUT_MS;
        try {
            socketTimeoutMs = Integer.parseInt(this.getEnvOrProperty(MONGODB_SOCKET_TIMEOUT_MS));
        } catch (Exception e) {
            // do nothing, returns default
        }
        logger.info("setting socket timout ms  " + socketTimeoutMs + " for MongoDB " + printSafe(uri));
        return socketTimeoutMs;
    }

    protected WriteConcern getWriteConcern(MongoClientURI uri) {
        try {
            if (uri.getOptions().getWriteConcern().getJournal()) {
                logger.info("enforcing journalled write concern for MongoDB " + printSafe(uri));
                return WriteConcern.JOURNALED;

            } else {
                logger.info("enforcing weak w1 write concern for MongoDB " + printSafe(uri));
                return WriteConcern.W1;
            }
        } catch (Exception e) {
            logger.info("falling back to enforcing journalled write concern for MongoDB " + printSafe(uri));
            return WriteConcern.JOURNALED;
        }
    }

    protected int getConnectionsPerHost(MongoClientURI uri) {
        Integer connections = DEFAULT_CONNECTIONS_PER_HOST;
        try {
            connections = Integer.parseInt(this.getEnvOrProperty(MONGODB_CONNECTION_POOL_SIZE));
        } catch (Exception e) {
            // return default
        }
        logger.info("setting connections per HOST " + connections + " for MongoDB " + printSafe(uri));
        return connections;
    }

    protected List<MongoCredential> getMongoCredentials(List<MongoClientURI> mongoClientURIsList) {

        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();

        for (MongoClientURI mongoClientURI : mongoClientURIsList) {
            // the database in here is the credentials database.

            MongoCredential credentials = mongoClientURI.getCredentials();

            credentialsList.add(credentials);

        }

        return credentialsList;
    }

    protected String printSafe(MongoClientURI uri) {
        try {
            return printSafe(uri.getURI());
        } catch (Exception e) {
            return URI_HAS_BEEN_MASKED;
        }
    }

    protected String printSafe(String uri) {
        try {
            return uri.substring(0, 15) + URI_HAS_BEEN_MASKED;
        } catch (Exception e) {
            return URI_HAS_BEEN_MASKED;
        }
    }

    /**
     * Disable ssl for local environment
     * @return
    */
    protected boolean isLocalEnvironment() {
        if (this.getEnvOrProperty(ENVIRONMENT) == null)
            return true;
        else
            return false;
    }

    protected String getEnvOrProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }

    /*
    private Morphia morphia;
    private Datastore datastore;

    public PlayMorphia() {
        System.out.println("PlayMorphia Constructor");
        this.morphia = new Morphia();
        MongoClient mongoClient = new MongoClient(ConfigFactory.load().getString("mongodb.host"), ConfigFactory.load().getInt("mongodb.port"));
        this.datastore = morphia.createDatastore(mongoClient, ConfigFactory.load().getString("mongodb.database"));
        this.datastore.ensureIndexes();
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        if(this.datastore == null)
            return new PlayMorphia().datastore;
        else
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
    */
}
