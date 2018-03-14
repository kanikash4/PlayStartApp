package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.mongodb.morphia.annotations.Entity;

import org.bson.types.ObjectId;
// import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
// import org.mongodb.morphia.annotations.Index;
// import org.mongodb.morphia.annotations.Indexes;

import java.io.Serializable;


@Entity
public class addDataModel implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId _id;
    public ObjectId myId;
    private String prodName;

    //getters and setter functions
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getmyId() {
        return myId;
    }

    public void setmyId(ObjectId myId){
    	this.myId = myId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName){
        this.prodName = prodName;
    }

}