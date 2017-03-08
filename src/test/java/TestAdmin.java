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
    public void testCreateClass() { // test that createClass() works
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClassOldYear() { // createClass should not work with year < 2017
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testCreateClassTwoInstructorsSameYear() { // the same instructor should not be able to teach > 2 classes in a year
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    @Test
    public void testCreateClassDiffInstructor() { // class cannot have multiple instructors
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
    }

    @Test
    public void testCreateClassCapacityMustBeGreaterThanZero() { // class capacity must be > 0
        this.admin.createClass("Test",2017,"Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClassNegativeCapacity() { // class cannot have negative capacity
        this.admin.createClass("Test",2017,"Instructor",-1);
        assertFalse(this.admin.classExists("Test",2017));
    }

    @Test
    public void testChangeCapacity() { // test that changeCapacity() works
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacitySameNumber() { // test that changing capacity to the same number should work
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 1);
        assertEquals(1, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacityToSmallerThanEnrolled() { // should not be able to change capacity to < # of students enrolled
        this.admin.createClass("Test",2017,"Instructor",2);
        IStudent student = new Student();
        student.registerForClass("stud", "Test", 2017);
        IStudent student2 = new Student();
        student2.registerForClass("stud", "Test", 2017);
        this.admin.changeCapacity("Test",2017,1);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testChangeCapacityToZero() { // should not be able to change class capacity to 0
        this.admin.createClass("Test",2017,"Instructor",2);
        this.admin.changeCapacity("Test",2017,0);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testChangeCapacityNegative() { // should not be able to change class capacity to negative number
        this.admin.createClass("Test",2017,"Instructor",2);
        this.admin.changeCapacity("Test",2017,-1);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testChangeCapacityClassNotExist() { // should not be able to change the capacity of a class that doesn't exist
        this.admin.changeCapacity("Test",2017,20);
        assertEquals(-1, this.admin.getClassCapacity("Test",2017));
    }

}
