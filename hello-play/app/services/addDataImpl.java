package services;

import org.mongodb.morphia.Datastore;

import javax.inject.Inject;
import javax.inject.Named;

public class AddDataImpl implements AddData {

    protected Datastore dataStore;

    @Inject
    public AddDataImpl(@Named("todo") Datastore dataStore) {
        this.dataStore = dataStore;
    }

//    @OverrideData(addDataModel data) {
//        dataStore.save(data);
//    }

}