package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mongodb.morphia.annotations.Entity;

import org.bson.types.ObjectId;
// import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
// import org.mongodb.morphia.annotations.Index;
// import org.mongodb.morphia.annotations.Indexes;

import java.io.Serializable;


@Entity
public class addDataModel implements Serializable{
@Id
public ObjectId myId;
public String prodName;


    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName){
    	this.prodName = prodName;
    }

    public ObjectId getmyId() {
        return myId;
    }

    public void setmyId(ObjectId myId){
    	this.myId = myId;
    }

}