package sga.core;

import com.google.gson.Gson;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class StudentDaoImpl implements StudentDao {
    private MongoCollection<Document> studentsColl;
    private Gson gson;

    public StudentDaoImpl(MongoClient mClient) {
        studentsColl = mClient.getDatabase("test").getCollection("students");
        System.out.println(studentsColl.toString());

        gson = new Gson();
    }

    @Override
    public void addStudent(Student student) {
        Document doc = Document.parse(student.toString());
        studentsColl.insertOne(doc);
    }

    @Override
    public Student getStudent(int id) {
        Document doc = studentsColl.find(eq("id", id)).first();
        if (doc == null) return null;

        System.out.println("Get Student");
        System.out.println(doc.toJson());

        return new Student(id, doc.get("name").toString(), doc.get("surname").toString());
    }

    @Override
    public void removeStudent(int id) {
        Bson filter = eq("id", id);
        DeleteResult result = studentsColl.deleteOne(filter);
    }

    @Override
    public void updateStudent(Student student) {
        Bson filter = eq("id", student.id());
        Bson updateName = set("name", student.name());
        Bson updateSurname = set("surname", student.surname());
        Bson updateOperation = combine(updateName, updateSurname);
        UpdateResult updateResult = studentsColl.updateOne(filter, updateOperation);
    }

    @Override
    public List<Student> getAllStudents() {
        MongoCursor<Document> cursor = studentsColl.find().iterator();
        ArrayList<Student> students = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                students.add(gson.fromJson(doc.toJson(), Student.class));
            }
        } finally {
            cursor.close();
        }

        return students;
    }
}
