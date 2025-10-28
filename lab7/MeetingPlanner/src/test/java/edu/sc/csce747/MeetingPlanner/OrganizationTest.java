package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class OrganizationTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void testGetRoom_success() throws Exception {
        Organization org = new Organization();
        Room r = org.getRoom("2A01");
        assertEquals("2A01", r.getID());
    }

    @Test
    public void testGetRoom_notFound_throws() {
        Organization org = new Organization();
        try {
            org.getRoom("X999");
            fail("Expected exception for non-existent room");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("room"));
        }
    }

    @Test
    public void testGetEmployee_success() throws Exception {
        Organization org = new Organization();
        Person p = org.getEmployee("Greg Gay");
        assertEquals("Greg Gay", p.getName());
    }

    @Test
    public void testGetEmployee_notFound_throws() {
        Organization org = new Organization();
        try {
            org.getEmployee("Non Existent");
            fail("Expected exception for non-existent employee");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("employee"));
        }
    }
}
