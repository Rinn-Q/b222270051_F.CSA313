package com.example;

import java.util.TreeSet;

public class Elevator {

    public enum State { IDLE, MOVING_UP, MOVING_DOWN, DOOR_OPEN }

    private State state;
    private int currentFloor;
    private final int minFloor;
    private final int maxFloor;

    // Хүсэлтүүд
    private final TreeSet<Integer> requestsUp = new TreeSet<>();
    private final TreeSet<Integer> requestsDown = new TreeSet<>();

    public Elevator(int minFloor, int maxFloor, int initialFloor) {
        if (initialFloor < minFloor || initialFloor > maxFloor)
            throw new IllegalArgumentException("initial floor out of range");
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = initialFloor;
        this.state = State.IDLE;
    }

    public State getState() { return state; }
    public int getCurrentFloor() { return currentFloor; }
    public boolean isDoorOpen() { return state == State.DOOR_OPEN; }
    public boolean hasPendingRequests() {
        return !requestsUp.isEmpty() || !requestsDown.isEmpty();
    }

    private void validateFloor(int floor) {
        if (floor < minFloor || floor > maxFloor)
            throw new IllegalArgumentException("floor out of range");
    }

    // ----------------------
    //  REQUEST METHODS
    // ----------------------

    public void selectFloor(int floor) {
        validateFloor(floor);
        if (state == State.DOOR_OPEN && floor == currentFloor) return;

        if (floor > currentFloor) requestsUp.add(floor);
        else if (floor < currentFloor) requestsDown.add(floor);

        tryStartMoving();
    }

    public void callUp(int floor) {
        validateFloor(floor);
        if (floor >= currentFloor) requestsUp.add(floor);
        else requestsDown.add(floor);
        tryStartMoving();
    }

    public void callDown(int floor) {
        validateFloor(floor);
        if (floor <= currentFloor) requestsDown.add(floor);
        else requestsUp.add(floor);
        tryStartMoving();
    }

    // ----------------------
    //  MOVEMENT LOGIC
    // ----------------------

    public void stepOnce() {
        if (state == State.DOOR_OPEN) return;
        startNextRequest();
    }

    private void tryStartMoving() {
        if (state == State.IDLE) startNextRequest();
    }

    private void startNextRequest() {
        Integer next = null;

        if (!requestsUp.isEmpty())
            next = requestsUp.first();       // хамгийн бага up
        else if (!requestsDown.isEmpty())
            next = requestsDown.last();      // хамгийн их down

        if (next == null) {
            state = State.IDLE;
            return;
        }

        moveTo(next);
    }

    private void moveTo(int floor) {
        if (state == State.DOOR_OPEN) return;

        if (floor > currentFloor) state = State.MOVING_UP;
        else state = State.MOVING_DOWN;

        currentFloor = floor;

        // хүрмэгц хүсэлтийг арилгана
        requestsUp.remove(floor);
        requestsDown.remove(floor);

        state = State.DOOR_OPEN;
    }

    // ----------------------
    //  DOOR EVENTS
    // ----------------------

    public void doorClose() {
        if (state != State.DOOR_OPEN) return;

        if (hasPendingRequests()) {
            state = State.IDLE; // Idle → tryStartMoving() шиг ажиллана
            startNextRequest();
        } else {
            state = State.IDLE;
        }
    }
}
