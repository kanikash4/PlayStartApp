package services;

import models.addDataModel;

public interface addData {

	// boolean userExists(String username);
    // String usrName(final String username);

    void upsertData(addDataModel data);

}
