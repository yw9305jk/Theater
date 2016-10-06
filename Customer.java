import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a customer in the theater
 *
 */
public class Customer implements Serializable {
    private UUID customerId;
    private String name;
    private Address address;
    private String phoneNumber;
    private List<CreditCard> creditCards = new ArrayList<CreditCard>();
    private List<Ticket> tickets = new ArrayList<Ticket>();

    /**
     * Creates a new customer
     * @param customerId
     * @param name
     * @param address
     * @param phoneNumber
     * @param creditCards
     */
    public Customer(UUID customerId, String name, Address address, String phoneNumber, 
            List<CreditCard> creditCards) {
        this.customerId = customerId;
        this.name = name;
        this.address = new Address(address);
        this.phoneNumber = phoneNumber;
        this.creditCards = new ArrayList<CreditCard>(creditCards);
    }

    /**
     * Adds a credit card to the customer
     * @param creditCard
     */
    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(new CreditCard(creditCard));
    }

    /**
     * Removes a credit card from the customer
     */
    public void removeCreditCards() {
        creditCards.clear();
    }

    /**
     * Gets all credit cards for the customer
     * @return
     */
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }
    
    /**
     * Adds a regular ticket to the customer
     * @param ticket
     */
    public Ticket addRegularTicket(RegularTicket ticket){
        tickets.add(new RegularTicket(ticket));
        return ticket;
    }
    
    public Ticket addAdvanceTicket(AdvanceTicket ticket){
        tickets.add(new AdvanceTicket(ticket));
        return ticket;
    }
    
    public Ticket addStudentAdvanceTicket(StudentAdvanceTicket ticket){
        tickets.add(new StudentAdvanceTicket(ticket));
        return ticket;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Displays info about customer
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Customer Id: " + customerId + "\n");
        result.append("Name: " + name + "\n");
        result.append("Address: " + address.toString() + "\n");
        result.append("Phone Number: " + phoneNumber + "\n");
        for (CreditCard creditCard : creditCards) {
            result.append(creditCard.toString() + "\n");
        }
        for (Ticket tickets : tickets) {
            result.append(tickets + "\n");
        }
        return result.toString();
    }
}
