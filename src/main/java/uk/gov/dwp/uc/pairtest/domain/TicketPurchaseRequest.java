package uk.gov.dwp.uc.pairtest.domain;

/**
 * Should be an Immutable Object
 */
public final class TicketPurchaseRequest {

    private final long accountId;
    private final TicketRequest[] ticketRequests;

    private TicketPurchaseRequest(long accountId, TicketRequest[] ticketRequests) {
        super();
        this.accountId = accountId;
        this.ticketRequests = ticketRequests;
    }
    public static TicketPurchaseRequest getTicketPurchaseRequestObj(long accountId, TicketRequest[] ticketRequests) {

        return new TicketPurchaseRequest(accountId,ticketRequests);
    }
    public long getAccountId() {
        return accountId;
    }

    public TicketRequest[] getTicketTypeRequests() {
        return ticketRequests;
    }
}
