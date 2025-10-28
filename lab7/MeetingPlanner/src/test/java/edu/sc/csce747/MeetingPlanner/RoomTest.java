package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void testAddMeeting_conflictMessageIncludesRoom() {
        Room r = new Room("2A02");
        try {
            r.addMeeting(new Meeting(4, 1, 9, 11));
            r.addMeeting(new Meeting(4, 1, 10, 12));
            fail("Expected conflict");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Conflict for room 2A02"));
        }
    }

    @Test
    public void testIsBusy_delegatesToCalendar() throws Exception {
        Room r = new Room("2A03");
        ArrayList<Person> attendees = new ArrayList<Person>();
        attendees.add(new Person("A"));
        Meeting m = new Meeting(6, 6, 10, 11, attendees, r, "Sync");
        r.addMeeting(m);
        assertTrue(r.isBusy(6, 6, 10, 10));
        assertFalse(r.isBusy(6, 6, 11, 12));
    }

    @Test
    public void testPrintAgenda_containsMeeting() throws Exception {
        Room r = new Room("2A04");
        ArrayList<Person> attendees = new ArrayList<Person>();
        attendees.add(new Person("B"));
        Meeting m = new Meeting(7, 7, 14, 15, attendees, r, "Review");
        r.addMeeting(m);
        String agenda = r.printAgenda(7, 7);
        assertTrue(agenda.contains("7/7"));
    }
}
