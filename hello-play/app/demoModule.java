/*
 * MongoDbModule
 */

import databases.PlayMorphia.*;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import databases.PlayMorphia;
import play.Configuration;
import play.Environment;
// import schedulers.TokenScheduler;


public class demoModule extends AbstractModule{

    /*private final Configuration configuration;
    
    public demoModule(Environment environment, Configuration configuration) {
    	System.out.println("**********");
        this.configuration = configuration;
    }*/
    @Override
	protected void configure(){
//		PlayMorphia p = new PlayMorphia();
//        System.out.println(p.getDatastore().toString());
//		bind(Datastore.class).toInstance(p.getDatastore());
	}

}

