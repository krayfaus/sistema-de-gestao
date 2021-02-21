package sga.core;

import com.google.gson.Gson;

import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class LabDaoImpl implements LabDao {
    private MongoCollection<Document> labsColl;
    private Gson gson;

    public LabDaoImpl(MongoClient mClient) {
        labsColl = mClient.getDatabase("test").getCollection("labs");
        gson = new Gson();
    }

    @Override
    public void addLab(Lab lab) {
        Document doc = Document.parse(lab.toString());
        labsColl.insertOne(doc);
    }

    @Override
    public Lab getLab(int id) {
        Document doc = labsColl.find(eq("id", id)).first();
        return gson.fromJson(doc.toJson(), Lab.class);
    }

    @Override
    public void removeLab(int id) {
        Bson filter = eq("id", id);
        DeleteResult result = labsColl.deleteOne(filter);
    }

    @Override
    public void updateLab(Lab lab) {
        Bson filter = eq("id", lab.id());
        Bson updateTitle = set("title", lab.title());
        // THIS IS WRONG FIX IT
        Bson updateStudents = set("students", gson.toJson(lab.students()));
        Bson updateOperation = combine(updateTitle, updateStudents);
        UpdateResult updateResult = labsColl.updateOne(filter, updateOperation);

        System.out.println(gson.toJson(lab.students()));
    }

    @Override
    public List<Lab> getAllLabs() {
        // Retrieving the documents.
        MongoCursor<Document> cursor = labsColl.find().iterator();
        ArrayList<Lab> labs = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                labs.add(gson.fromJson(doc.toJson(), Lab.class));
            }
        } finally {
            cursor.close();
        }

        return labs;
    }
}
