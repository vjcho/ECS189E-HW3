import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClassOldYear() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testMakeClassTwoInstructorsSameYear() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test2", 2017));
    }

    @Test
    public void testChangeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacityToSmallerThanEnrolled() {
        this.admin.createClass("Test",2017,"Instructor",1);
        IStudent student = new Student();
        student.registerForClass("stud", "Test", 2017);
        this.admin.changeCapacity("Test",2017,0);
        assertEquals(1, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testUniqueClasses() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
    }
}
