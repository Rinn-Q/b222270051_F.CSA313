import org.junit.jupiter.api.Test;

import com.example.Elevator;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    // Helper: elevator with floors 0..2, initial at 0
    private Elevator makeElevator() {
        return new Elevator(0, 2, 0);
    }

    @Test
    void testIdleInitially() {
        Elevator e = makeElevator();
        assertEquals(Elevator.State.IDLE, e.getState());
        assertEquals(0, e.getCurrentFloor());
        assertFalse(e.hasPendingRequests());
    }

    @Test
    void testSelectFloorMovesUpAndOpensDoor() {
        Elevator e = makeElevator();
        e.selectFloor(2);            // request to floor 2
        // stepOnce triggers movement to nearest request
        e.stepOnce();
        assertEquals(Elevator.State.DOOR_OPEN, e.getState());
        assertEquals(2, e.getCurrentFloor());
        assertTrue(e.isDoorOpen());
    }

    @Test
    void testCallDownMovesDownAndOpensDoor() {
        Elevator e = new Elevator(0, 2, 2); // start on top floor
        e.callDown(1);
        e.stepOnce();
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Elevator.State.DOOR_OPEN, e.getState());
    }

    @Test
    void testDoorOpenPreventsMovement() {
        Elevator e = makeElevator();
        e.selectFloor(1);
        e.stepOnce(); // should arrive and open door at floor 1
        assertTrue(e.isDoorOpen());
        // now attempt to select another floor while door open
        e.selectFloor(2);
        // door is still open; stepOnce should do nothing until doorClose
        e.stepOnce();
        assertEquals(Elevator.State.DOOR_OPEN, e.getState());

        // close door and then step
        e.doorClose();
        // after closing, it should move to floor 2
        e.stepOnce();
        assertEquals(2, e.getCurrentFloor());
        assertEquals(Elevator.State.DOOR_OPEN, e.getState());
    }

    @Test
    void testSequentialRequests() {
        Elevator e = makeElevator();
        e.callUp(2);  // external call to 2
        e.stepOnce(); // move to 2 and open
        assertEquals(2, e.getCurrentFloor());
        e.doorClose();
        // now from inside select 1
        e.selectFloor(1);
        e.stepOnce();
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Elevator.State.DOOR_OPEN, e.getState());
    }

    @Test
    void testInvalidFloorThrows() {
        Elevator e = makeElevator();
        assertThrows(IllegalArgumentException.class, () -> e.selectFloor(-1));
        assertThrows(IllegalArgumentException.class, () -> e.callUp(10));
    }
}
