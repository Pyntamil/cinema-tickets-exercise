package uk.gov.dwp.uc.pairtest.domain;

/**
 * Should be an Immutable Object
 */
public final class TicketRequest {

    private final int noOfTickets;
    private final Type type;

    private TicketRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }
    public static TicketRequest getTicketRequestObj(Type type, int noOfTickets) {

        return new TicketRequest(type,noOfTickets);
    }
    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD , INFANT
    }

}
