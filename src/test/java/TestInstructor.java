import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor = new Instructor();
    }

    @Test
    public void testAddHomework() { // test that addHomework() works
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        assertTrue(instructor.homeworkExists("Test", 2017, "hw1"));
    }

    @Test
    public void testAddHomeworkUnassignedInstructor() { // should not be able to add homework if not assigned to class
        instructor.addHomework("Instructor2", "Test", 2017, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test", 2017, "hw1"));
    }

    @Test
    public void testAddHomeworkClassNotExists() { // should not be able to add homework to a class that doesn't exist
        instructor.addHomework("Instructor", "Test2", 2017, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test2",2017,"hw1"));
    }

    @Test
    public void testAddHomeworkWrongYear() { // should not be able to add homework to a class in the past
        instructor.addHomework("Instructor", "Test", 2016, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test",2016,"hw1"));
    }

    @Test
    public void testAssignGrade() { // test that assignGrade() works
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        student.submitHomework("stud","hw1","answer","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertEquals(new Integer(1), instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNoStudentSubmission() { // should not be able to assign grade to a student who did not submit homework
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNonexistentStudent() { // should not be able to assign grade to a student that doesn't exist
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNoHomework() { // should not be able to assign grade if student didn't submit homework
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);
        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeUnassignedInstructor() { // should not be able to assign grade if instructor is not assigned to the class
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor2","Test",2017,"hw1","hw desc");
        student.submitHomework("stud","hw1","answer","Test",2017);
        instructor.assignGrade("Instructor2","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test", 2017,"hw1","stud"));
    }
}
