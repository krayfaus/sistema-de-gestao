package sga.core;

import java.util.ArrayList;
import com.google.gson.Gson;

public record Lab(Integer id,
                  String title,
                  ArrayList<Student> students) {

    public Lab(Integer id, String title) {
        this(id, title, new ArrayList<Student>());
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        if (students.size() == 0) return;

        students.remove(student);
    }

    public Integer studentCount() {
        return students.size();
    }

    /**
     * Returns a JSON object that can be used by MongoDB.
     *
     * @return      JSON object generated from POJO
     */
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Lab.class);
    }
}