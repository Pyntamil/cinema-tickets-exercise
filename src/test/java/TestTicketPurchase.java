import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;


public class TestTicketPurchase {
    TicketServiceImpl TicketServiceImpl=new TicketServiceImpl();

    @Test
    public void validPurchase()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 19),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketRequest[] ticketRequest1 = new TicketRequest[]{};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        TicketServiceImpl.purchaseTickets(ticketPurchaseRequest);
    }

    @Test
    public void inValidAccount()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 10),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(-11,ticketRequest);
        TicketServiceImpl.purchaseTickets(ticketPurchaseRequest);
    }

    @Test
    public void onlyChildInfant()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 10),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        TicketServiceImpl.purchaseTickets(ticketPurchaseRequest);
    }

    @Test
    public void maxTicketCheck()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 20),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        TicketServiceImpl.purchaseTickets(ticketPurchaseRequest);
    }

    @Test
    public void emptyReq()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        TicketServiceImpl.purchaseTickets(ticketPurchaseRequest);
    }
}
