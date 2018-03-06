package modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import database.PlayMorphia;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;
import play.Environment;
import schedulers.TokenScheduler;
import services.*;

public class PlayModules extends AbstractModule {
	Logger logger = LoggerFactory.getLogger(playModules.class);

	private final Configuration configuration;

	public playModules(Environment environment, Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {

		bind(Datastore.class).annotatedWith(Names.named("todo")).toInstance(new PlayMorphia(configuration, "referral.mongodb.uri").get());

		bind(addData.class).to(addDataImpl.class);

		logger.info("completed module bindings for hello-play microservice");
	}
}