package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalendarTest {
	// Add test methods here. 
	// You are not required to write tests for all classes.
	
	@Test
	public void testAddMeeting_holiday() {
		// Create Midsommar holiday
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			Meeting midsommar = new Meeting(6, 26, "Midsommar");
			calendar.addMeeting(midsommar);
			// Verify that it was added.
			Boolean added = calendar.isBusy(6, 26, 0, 23);
			assertTrue("Midsommar should be marked as busy on the calendar",added);
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

    @Test
    public void testAddMeeting_nonOverlapping_ok() {
        Calendar calendar = new Calendar();
        try {
            calendar.addMeeting(new Meeting(3, 10, 9, 10));
            calendar.addMeeting(new Meeting(3, 10, 11, 12));
            assertTrue(calendar.isBusy(3, 10, 9, 9));
            assertTrue(calendar.isBusy(3, 10, 11, 11));
            assertFalse(calendar.isBusy(3, 10, 10, 11));
        } catch (TimeConflictException e) {
            fail("Non-overlapping meetings should not conflict");
        }
    }

    @Test
    public void testAddMeeting_overlapping_throws() {
        Calendar calendar = new Calendar();
        try {
            calendar.addMeeting(new Meeting(4, 5, 14, 16));
            calendar.addMeeting(new Meeting(4, 5, 15, 17));
            fail("Expected overlap to throw TimeConflictException");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Overlap"));
        }
    }

    @Test
    public void testClearSchedule_emptiesDay() {
        Calendar calendar = new Calendar();
        try {
            calendar.addMeeting(new Meeting(5, 20, 8, 9));
            assertTrue(calendar.isBusy(5, 20, 8, 8));
            calendar.clearSchedule(5, 20);
            assertFalse(calendar.isBusy(5, 20, 8, 8));
        } catch (TimeConflictException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testPrintAgenda_includesMeeting() {
        Calendar calendar = new Calendar();
        try {
            Room room = new Room("2A01");
            Meeting m = new Meeting(7, 4, 10, 11, new java.util.ArrayList<Person>(), room, "Standup");
            calendar.addMeeting(m);
            String agendaDay = calendar.printAgenda(7, 4);
            assertTrue(agendaDay.contains("7/4"));
        } catch (TimeConflictException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidInputs_throw() {
        Calendar calendar = new Calendar();
        // Invalid day
        try {
            calendar.isBusy(1, 0, 9, 10);
            fail("Expected invalid day to throw");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Day"));
        }

        // Invalid month high
        try {
            calendar.isBusy(13, 1, 9, 10);
            fail("Expected invalid month to throw");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Month"));
        }

        // Invalid hours
        try {
            calendar.isBusy(1, 1, -1, 10);
            fail("Expected invalid hour to throw");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("Illegal hour"));
        }
        try {
            calendar.isBusy(1, 1, 10, 10);
            fail("Expected start>=end to throw");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("starts before it ends"));
        }
    }

    @Test
    public void testDecemberIsValidMonth_expectedBehavior() {
        Calendar calendar = new Calendar();
        try {
            calendar.addMeeting(new Meeting(12, 1, 9, 10));
            assertTrue(calendar.isBusy(12, 1, 9, 9));
        } catch (TimeConflictException e) {
            // If this fails due to implementation bug, this test will reveal it
            fail("December should be a valid month: " + e.getMessage());
        }
    }
}
