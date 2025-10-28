package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void testToString_includesFields() {
        ArrayList<Person> attendees = new ArrayList<Person>();
        attendees.add(new Person("Alice"));
        attendees.add(new Person("Bob"));
        Room room = new Room("2A03");
        Meeting m = new Meeting(8, 15, 13, 14, attendees, room, "Design Review");
        String s = m.toString();
        assertTrue(s.contains("8/15"));
        assertTrue(s.contains("13 - 14"));
        assertTrue(s.contains("2A03"));
        assertTrue(s.contains("Design Review"));
        assertTrue(s.contains("Alice"));
        assertTrue(s.contains("Bob"));
    }

    @Test
    public void testAttendeeAddRemove() {
        ArrayList<Person> attendees = new ArrayList<Person>();
        Room room = new Room("2A04");
        Meeting m = new Meeting(9, 1, 9, 10, attendees, room, "Scrum");
        Person c = new Person("Carol");
        m.addAttendee(c);
        assertEquals(1, m.getAttendees().size());
        assertEquals("Carol", m.getAttendees().get(0).getName());
        m.removeAttendee(c);
        assertEquals(0, m.getAttendees().size());
    }

    @Test
    public void testGettersSetters() {
        Meeting m = new Meeting();
        m.setMonth(10);
        m.setDay(31);
        m.setStartTime(7);
        m.setEndTime(8);
        m.setRoom(new Room("2A01"));
        m.setDescription("Daily");
        assertEquals(10, m.getMonth());
        assertEquals(31, m.getDay());
        assertEquals(7, m.getStartTime());
        assertEquals(8, m.getEndTime());
        assertEquals("2A01", m.getRoom().getID());
        assertEquals("Daily", m.getDescription());
    }
}
