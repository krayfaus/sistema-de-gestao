package sga.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLab {
    @Test
    public void testCreation() {
        Lab lab = new Lab(1, "Sala 1");

        assertEquals((Integer) 1, lab.id());
        assertEquals("Sala 1", lab.title());
        assertEquals(0, lab.students().size());
    }

    @Test
    public void testAddStudent() {
        Lab lab = new Lab(1, "Sala 1");
        Student student = new Student(1, "John", "Doe");
        lab.addStudent(student);

        System.out.println(student.toString());
        System.out.println(lab.toString());

        assertEquals(1, lab.students().size());
    }

    @Test
    public void testRemoval() {
        Lab lab = new Lab(1, "Sala 1");
        Student student = new Student(1, "John", "Doe");
        lab.addStudent(student);
        lab.removeStudent(student);

        assertEquals(0, lab.students().size());
    }
}