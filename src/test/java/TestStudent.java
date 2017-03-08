import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Test",2017,"Instructor",1);
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Test
    public void testRegisterForClass() {
        this.student.registerForClass("stud","Test",2017);
        assertTrue(this.student.isRegisteredFor("stud","Test",2017));
    }

    @Test
    public void testRegisterClassFullCapacity() {
        IStudent student2 = new Student();
        this.student.registerForClass("stud1", "Test", 2017);
        student2.registerForClass("stud2", "Test", 2017);
        assertFalse(student2.isRegisteredFor("stud2", "Test", 2017));
    }

    @Test
    public void testRegisterClassNoClass() {
        this.student.registerForClass("stud","Test2",2017);
        assertFalse(this.student.isRegisteredFor("stud","Test2",2017));
    }

    @Test
    public void testDropClass() {
        this.student.registerForClass("stud", "Test",2017);
        assertTrue(this.student.isRegisteredFor("stud","Test",2017));

        this.student.dropClass("stud", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("stud", "Test", 2017));
    }

    @Test
    public void testSubmitHomework() {
        this.student.registerForClass("stud", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw", "desc");
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertTrue(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkNotRegisteredForClass() {
        this.instructor.addHomework("Instructor", "Test", 2017, "hw", "desc");
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkNotAssigned() {
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkDiffYear() {
        this.admin.createClass("Test2",2018,"Instructor",1);
        this.instructor.addHomework("Instructor", "Test2", 2018, "hw", "desc");
        this.student.registerForClass("stud", "Test2", 2018);
        this.student.submitHomework("stud", "hw", "answer", "Test2", 2018);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test2", 2018));
    }
}
