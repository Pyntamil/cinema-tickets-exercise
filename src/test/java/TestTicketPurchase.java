import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTicketPurchase {
    @Mock
    TicketServiceImpl mock = mock(TicketServiceImpl.class);
    @Test
    public void validPurchase()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 19),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        doCallRealMethod().when(mock).purchaseTickets(any(TicketPurchaseRequest.class));
        mock.purchaseTickets(ticketPurchaseRequest);
        verify(mock, times(1)).purchaseTickets(ticketPurchaseRequest);
    }

    @Test
    public void inValidAccount()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 10),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(-11,ticketRequest);
        doCallRealMethod().when(mock).purchaseTickets(any(TicketPurchaseRequest.class));
        Throwable thrown = assertThrows(InvalidPurchaseException.class, () -> mock.purchaseTickets(ticketPurchaseRequest));
        assertEquals("Invalid account id", thrown.getMessage());
    }

    @Test
    public void onlyChildInfant()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 10),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        doCallRealMethod().when(mock).purchaseTickets(any(TicketPurchaseRequest.class));
        Throwable thrown = assertThrows(InvalidPurchaseException.class, () -> mock.purchaseTickets(ticketPurchaseRequest));
        assertEquals("Child and Infant tickets cannot be purchased without purchasing an Adult ticket", thrown.getMessage());
    }

    @Test
    public void maxTicketCheck()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{TicketRequest.getTicketRequestObj(TicketRequest.Type.CHILD, 20),TicketRequest.getTicketRequestObj(TicketRequest.Type.ADULT, 1),TicketRequest.getTicketRequestObj(TicketRequest.Type.INFANT, 1)};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        doCallRealMethod().when(mock).purchaseTickets(any(TicketPurchaseRequest.class));
        Throwable thrown = assertThrows(InvalidPurchaseException.class, () -> mock.purchaseTickets(ticketPurchaseRequest));
        assertEquals("Only a maximum of 20 tickets that can be purchased at a time", thrown.getMessage());
    }

    @Test
    public void emptyReq()
    {
        TicketRequest[] ticketRequest = new TicketRequest[]{};
        TicketPurchaseRequest  ticketPurchaseRequest= TicketPurchaseRequest.getTicketPurchaseRequestObj(11,ticketRequest);
        doCallRealMethod().when(mock).purchaseTickets(any(TicketPurchaseRequest.class));
        mock.purchaseTickets(ticketPurchaseRequest);
        verify(mock, times(1)).purchaseTickets(ticketPurchaseRequest);
    }
}
