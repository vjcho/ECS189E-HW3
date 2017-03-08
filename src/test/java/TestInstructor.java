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
    public void testAddHomework() {
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        assertTrue(instructor.homeworkExists("Test", 2017, "hw1"));
    }

    @Test
    public void testAddHomeworkUnassignedInstructor() {
        instructor.addHomework("Instructor2", "Test", 2017, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test", 2017, "hw1"));
    }

    @Test
    public void testAddHomeworkClassNotExists() {
        instructor.addHomework("Instructor", "Test2", 2017, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test2",2017,"hw1"));
    }

    @Test
    public void testAddHomeworkWrongYear() {
        instructor.addHomework("Instructor", "Test", 2016, "hw1", "hw desc");
        assertFalse(instructor.homeworkExists("Test",2016,"hw1"));
    }

    @Test
    public void testAssignGrade() {
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        student.submitHomework("stud","hw1","answer","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertEquals(new Integer(1), instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNoStudentSubmission() {
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNonexistentStudent() {
        instructor.addHomework("Instructor","Test",2017,"hw1","hw desc");
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeNoHomework() {
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.assignGrade("Instructor","Test",2017,"hw1","stud",1);
        assertNull(instructor.getGrade("Test",2017,"hw1","stud"));
    }

    @Test
    public void testAssignGradeUnassignedInstructor() {
        IStudent student = new Student();
        student.registerForClass("stud","Test",2017);
        instructor.addHomework("Instructor2","Test",2017,"hw1","hw desc");
        student.submitHomework("stud","hw1","answer","Test",2017);
        instructor.assignGrade("Instructor2","Test",2017,"hw1","stud",1);

        assertNull(instructor.getGrade("Test", 2017,"hw1","stud"));
    }
}
