package api.core.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vincent on 22/2/2017.
 */
public class DataManager {

    static Set<Enrollee> enrollees = new HashSet<>();
    static Map<Course, String> courseInstructors = new HashMap<>();

    static Course findCourse(String name, int year) {
        Course dummy = new Course(name, year);
        for (Course c : courseInstructors.keySet()) {
            if (c.equals(dummy)) return c;
        }
        return null;
    }

    static Enrollee findStudent(String name) {
        for (Enrollee s : enrollees) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }

}
