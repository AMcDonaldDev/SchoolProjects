package org.cmsc495.bpo.controllers.response;

public class ServerStatus {
    private Status status;

    public ServerStatus() {
    }

    public ServerStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ServerStatus status(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " status='" + getStatus() + "'" +
            "}";
    }

    public enum Status {
        UP_AND_RUNNING,
        UNDER_MAINTENANCE,
        DEGRADED
    }
}
