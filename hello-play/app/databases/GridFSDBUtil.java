package database;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import javafx.util.Pair;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridFSUtils {
    private DB db;

    public GridFSUtils(Datastore datastore) {
        this.db=datastore.getDB();
    }

    public String saveImageIntoMongoDB(String bucketName, Map<String,String> metadata, File imageFile, String dbFileName,String contentType) throws IOException {

        GridFS gfsPhoto = new GridFS(db,bucketName);
        GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
        gfsFile.setFilename(dbFileName);
        ObjectId objectId=new ObjectId();
        gfsFile.setId(objectId);
        gfsFile.setContentType(contentType);

        if (metadata!=null)
        {
            for (Map.Entry<String,String> entry:metadata.entrySet()){
                gfsFile.put(entry.getKey(), entry.getValue());
            }
        }
        gfsFile.save();

        return objectId.toString();
    }

    public Pair<String, byte[]> getSingleImage(String imageId, String bucketName) throws IOException {
        GridFS gfsPhoto = new GridFS(db, bucketName);
        BasicDBObject query = new BasicDBObject();
        query.put("_id",new ObjectId(imageId));
        GridFSDBFile imageForOutput = gfsPhoto.findOne(query);
        String contentType = imageForOutput.getContentType();

        return new Pair<String,byte[]>(contentType,readFile(imageForOutput));
    }


    private byte[] getAllUsersImages(String idKey, String idValue,String bucketName) throws IOException {

        BasicDBObject query = new BasicDBObject();
        if (idKey!=null){
            query.put(idKey,idValue);
        }
        GridFS gfsPhoto = new GridFS(db, bucketName);
        List<GridFSDBFile> imageForOutput = gfsPhoto.find(query);

        return readFile(imageForOutput.get(0));
    }

    private void listAllImages(String bucketName) {
        GridFS gfsPhoto = new GridFS(db, bucketName);
        DBCursor cursor = gfsPhoto.getFileList();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private void deleteImageFromMongoDB(String imageId,String bucketName) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id",new ObjectId(imageId));
        GridFS gfsPhoto = new GridFS(db, bucketName);
        gfsPhoto.remove(gfsPhoto.findOne(query));
    }

    public byte[] readFile(GridFSDBFile gridFSDBFile) throws IOException {
        byte[] file = new byte[new Long(gridFSDBFile.getLength()).intValue()];
        InputStream inputStream = gridFSDBFile.getInputStream();
        try {
            int bytesRead = inputStream.read(file);
            while (bytesRead != -1) {
                bytesRead = inputStream.read(file);
            }
        } finally {
            inputStream.close();
        }
        return file;
    }

}
