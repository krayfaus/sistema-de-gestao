package sga.core;

import java.util.List;

public interface StudentDao {
    public void addStudent(Student student);

    public Student getStudent(int id);

    public void removeStudent(int id);

    public void updateStudent(Student student);

    public List<Student> getAllStudents();
}