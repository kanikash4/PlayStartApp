package services;

import models.addDataModel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class addDataImpl implements addData {

    protected Datastore dataStore;

    @Inject
    public ProductCategoryRepoImpl(@Named("todo") Datastore dataStore) {
        this.dataStore = dataStore;
    }

    @OverrideData(addDataModel data) {
        dataStore.save(data);
    }

}