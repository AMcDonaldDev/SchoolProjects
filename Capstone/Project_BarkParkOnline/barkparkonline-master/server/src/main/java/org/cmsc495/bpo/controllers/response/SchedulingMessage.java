package org.cmsc495.bpo.controllers.response;

/**
 * Simple Message for 
 */
public class SchedulingMessage {
    private Boolean accepted;
    private Object message;

    public SchedulingMessage() {
    }

    public SchedulingMessage(Boolean accepted, Object message) {
        this.accepted = accepted;
        this.message = message;
    }

    public Boolean isAccepted() {
        return this.accepted;
    }

    public Boolean getAccepted() {
        return this.accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public SchedulingMessage wasAccepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public SchedulingMessage withMessage(Object message) {
        this.message = message;
        return this;
    }
}
