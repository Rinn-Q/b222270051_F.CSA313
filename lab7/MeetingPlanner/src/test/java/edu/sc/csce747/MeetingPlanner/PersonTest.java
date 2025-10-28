package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class PersonTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void testAddMeeting_conflictMessageIncludesName() {
        Person alice = new Person("Alice");
        try {
            alice.addMeeting(new Meeting(1, 2, 9, 11));
            alice.addMeeting(new Meeting(1, 2, 10, 12));
            fail("Expected conflict");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Conflict for attendee Alice"));
        }
    }

    @Test
    public void testIsBusy_delegatesToCalendar() throws Exception {
        Person bob = new Person("Bob");
        bob.addMeeting(new Meeting(2, 14, 8, 9));
        assertTrue(bob.isBusy(2, 14, 8, 8));
        assertFalse(bob.isBusy(2, 14, 9, 10));
    }

    @Test
    public void testPrintAgenda_containsMeeting() throws Exception {
        Person carol = new Person("Carol");
        ArrayList<Person> attendees = new ArrayList<Person>();
        attendees.add(carol);
        Meeting m = new Meeting(3, 3, 15, 16, attendees, new Room("2A05"), "1:1");
        carol.addMeeting(m);
        String agenda = carol.printAgenda(3, 3);
        assertTrue(agenda.contains("3/3"));
    }
}
