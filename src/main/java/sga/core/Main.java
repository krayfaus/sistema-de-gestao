package sga.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello MongoDB!");

        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            StudentDao studentDao = new StudentDaoImpl(mongoClient);
            Student stu = studentDao.getStudent(1);
            if (stu != null) System.out.println(stu.toString());
        }
    }
}
