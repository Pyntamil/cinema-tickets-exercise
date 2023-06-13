package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.HashMap;


public class TicketServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */
    private final static int ADULTFARE =20;
    private final static int CHILDFARE=10;
    private final static int INFANTFARE=0;
    private static final int MAXTICKET = 20;
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {
        try {
            TicketPaymentServiceImpl TicketPaymentServiceImpl=new TicketPaymentServiceImpl();
            SeatReservationServiceImpl seatReservationService=new SeatReservationServiceImpl();
            HashMap  ticketCountDetails=validatePurchaseRequest(ticketPurchaseRequest);
            int totalSeatsToAllocate = calSeatCount(ticketCountDetails);
            int paymentAmount=calculatePaymentAmount(ticketCountDetails);
            System.out.println("Ticket Info \n-----------------------------------------------");
            System.out.println("" +TicketRequest.Type.ADULT +" : "+ticketCountDetails.get(TicketRequest.Type.ADULT) +"\n"+TicketRequest.Type.CHILD +" : "+ticketCountDetails.get(TicketRequest.Type.CHILD)+"\n"+TicketRequest.Type.INFANT +" : "+ticketCountDetails.get(TicketRequest.Type.INFANT));
            System.out.println("-----------------------------------------------");
            System.out.println("Payment amount "+paymentAmount);
            TicketPaymentServiceImpl.makePayment(ticketPurchaseRequest.getAccountId(),paymentAmount);
            seatReservationService.reserveSeat(ticketPurchaseRequest.getAccountId(),totalSeatsToAllocate);
            System.out.println("Seats allocated " + totalSeatsToAllocate);
        }
        catch (InvalidPurchaseException invalidRequestException)
        {
            //System.out.println( invalidRequestException.getMessage());
            throw  new InvalidPurchaseException(invalidRequestException.getMessage());
        }

    }

    private HashMap validatePurchaseRequest(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException
    {
        int adultCount=0,childCount=0,infantCount=0,totalTicketCount=0;
        HashMap<TicketRequest.Type,Integer> ticketCountDetails=new HashMap<>();
        if(ticketPurchaseRequest.getAccountId()<=0)
            throw new InvalidPurchaseException("Invalid account id");
        for (TicketRequest ticketRequest:ticketPurchaseRequest.getTicketTypeRequests())
        {
            switch (ticketRequest.getTicketType())
            {
                case ADULT -> {
                    adultCount= adultCount+ ticketRequest.getNoOfTickets();
                }
                case CHILD -> {
                    childCount= childCount+ ticketRequest.getNoOfTickets();
                }
                case INFANT -> {
                    infantCount=infantCount+ ticketRequest.getNoOfTickets();;
                }
            }
        }
        if((childCount >0 || infantCount >0) && adultCount<=0)
            throw  new InvalidPurchaseException("Child and Infant tickets cannot be purchased without purchasing an Adult ticket");
        totalTicketCount=childCount+adultCount;
        if(totalTicketCount>MAXTICKET)
            throw new InvalidPurchaseException("Only a maximum of 20 tickets that can be purchased at a time");

        ticketCountDetails.put(TicketRequest.Type.ADULT,adultCount);
        ticketCountDetails.put(TicketRequest.Type.CHILD,childCount);
        ticketCountDetails.put(TicketRequest.Type.INFANT,infantCount);
        return ticketCountDetails;
    }
    private int calSeatCount(HashMap ticketCountDetails) throws InvalidPurchaseException
    {
        int totalSeatsToAllocate= (int) ticketCountDetails.get(TicketRequest.Type.ADULT)+(int) ticketCountDetails.get(TicketRequest.Type.CHILD);
        if(totalSeatsToAllocate>MAXTICKET)
            throw new InvalidPurchaseException("Only a maximum of 20 tickets that can be purchased at a time");
        return totalSeatsToAllocate;
    }

    private int calculatePaymentAmount(HashMap ticketCountDetails )
    {
        int paymentAmount=((int)ticketCountDetails.get(TicketRequest.Type.ADULT)* ADULTFARE)+((int)ticketCountDetails.get(TicketRequest.Type.CHILD)*CHILDFARE);

        return paymentAmount;
    }
}
