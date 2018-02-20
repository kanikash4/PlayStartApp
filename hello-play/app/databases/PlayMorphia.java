package databases;

import com.google.inject.Singleton;

import com.mongodb.MongoClient;
import com.typesafe.config.ConfigFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

@Singleton
public class PlayMorphia {

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
}
