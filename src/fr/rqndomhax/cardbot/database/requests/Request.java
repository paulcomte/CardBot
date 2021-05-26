package fr.rqndomhax.cardbot.database.requests;

public class Request {
    private final Requests requestType;

    public Request(Requests requestType) {
        this.requestType = requestType;
    }

    public Requests getRequestType() {
        return requestType;
    }
}