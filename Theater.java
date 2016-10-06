import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Represents the theater inventory system and contains all of its commands
 *
 */
public class Theater implements Serializable {
    private String name;
    private HashMap<UUID, Client> clients = new HashMap<UUID, Client>();
    private HashMap<UUID, Customer> customers = new HashMap<UUID, Customer>();
    private BigDecimal balance = new BigDecimal(0);

    public Theater() {

    }

    /**
     * Adds a client to the theater
     * @param name
     * @param address
     * @param phoneNumber
     */
    public void addClient(String name, Address address, String phoneNumber) {
        // Generate new unique ID and use it to hash an instantiated client
        UUID id = UUID.randomUUID();
        clients.put(id, new Client(id, name, address, phoneNumber));
    }

    /**
     * Removes a client from the theater
     * @param clientId
     * @return
     */
    public boolean removeClient(UUID clientId) {
        if (customers.containsKey(clientId)) {
            List<Show> clientShows = clients.get(clientId).getShows();
            for (Show show : clientShows) {
                Date currentDate = Calendar.getInstance().getTime();
    
                // If the current date is in-between the show's start date
                // and end date, then we cannot remove the client
                if (show.getStartDate().before(currentDate) && show.getEndDate().after(currentDate)) {
                    return false;
                }
            }
        } else {
            System.out.println("Client does not exist");
        }
        clients.remove(clientId);
        return true;
    }

    /**
     * Lists all clients in the theater
     */
    public void listClients() {
        for (Client client : clients.values()) {
            System.out.println(client.toString());
        }
    }

    /**
     * Adds a customer to the theater
     * @param name
     * @param address
     * @param phoneNumber
     * @param creditCard
     * @param tickets
     * @return
     */
    public UUID addCustomer(String name, Address address, String phoneNumber, List<CreditCard> creditCard) {
        UUID customerId = UUID.randomUUID();
        customers.put(customerId, new Customer(customerId, name, address, phoneNumber, creditCard));
        return customerId;
    }

    /**
     * Removes a customer from the theater
     * @param customerId
     */
    public void removeCustomer(UUID customerId) {
        if (customers.containsKey(customerId)) {
            customers.get(customerId).removeCreditCards();
            customers.remove(customerId);
        } else {
            System.out.println("Customer does not exist");
            return;
        }
    }

    /**
     * Adds a credit card to a customer
     * @param customerId
     * @param creditCard
     */
    public void addCreditCard(UUID customerId, CreditCard creditCard) {
        if (customers.containsKey(customerId)) {
            customers.get(customerId).addCreditCard(creditCard);
        } else {
            System.out.println("Customer does not exist");
            return;
        }
    }

    /**
     * Removes a credit card from a customer
     * @param cardNumber
     */
    public void removeCreditCard(String cardNumber) {
        for (Customer customer : customers.values()) {
            List<CreditCard> customerCreditCards = customer.getCreditCards();
            if (customerCreditCards.size() > 1) {
                for (int i = 0; i < customerCreditCards.size(); i++) {
                    if (customerCreditCards.get(i).getCardNumber().equals(cardNumber)) {
                        customerCreditCards.remove(i);
                    }
                }
            }
        }
    }

    /**
     * Lists all customers in the theater
     */
    public void listCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println(customer.toString());
        }
    }

    /**
     * Adds a show to a client
     * @param clientId
     * @param name
     * @param startDate
     * @param endDate
     * @param ticketPrice
     * @return
     */
    public boolean addShow(UUID clientId, String name, Calendar startDate, Calendar endDate, BigDecimal ticketPrice) {
        for (Client client : clients.values()) {
            for (Show show : client.getShows()) {
                Calendar currentShowStartDate = show.getStartDate();
                Calendar currentShowEndDate = show.getEndDate();
                
                // If an existing show's start and end date conflict with a new
                // show's start and end date, don't let that show get created
                SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
                if (sdf.format(startDate.getTime()).equals(sdf.format(currentShowStartDate.getTime())) ||
                        sdf.format(startDate.getTime()).equals(sdf.format(currentShowEndDate.getTime())) ||
                        sdf.format(endDate.getTime()).equals(sdf.format(currentShowStartDate.getTime())) ||
                        sdf.format(endDate.getTime()).equals(sdf.format(currentShowEndDate.getTime())) ||
                        (startDate.after(currentShowStartDate) && startDate.before(currentShowEndDate)) ||
                        (endDate.after(currentShowStartDate) && endDate.before(currentShowEndDate))) {
                    return false;
                }
            }
        }
        if (clients.containsKey(clientId)) {
            clients.get(clientId).getShows().add(new Show(name, startDate, endDate, ticketPrice));
        } else {
            System.out.println("Client does not exist");
            return false;
        }
        return true;
    }

    /**
     * List all shows for all clients
     */
    public void listShows() {
        for (Client client : clients.values()) {
            for (Show show : client.getShows()) {
                System.out.println(show);
            }
        }
    }
    
    /**
     * Sell a ticket to a customer
     * @param ticketType
     * @param customerId
     * @param amount
     * @param date
     * @param creditCardNumber
     */
    public <T extends Ticket> void sellTicket(Class<T> ticketType, UUID customerId, int amount, Calendar date,
            String creditCardNumber) {
        
        boolean isValidCreditCard = false;
        try {
            List<CreditCard> customerCreditCards = customers.get(customerId).getCreditCards();
            if (customerCreditCards.size() > 0) {
                for (int i = 0; i < customerCreditCards.size(); i++) {
                    if (customerCreditCards.get(i).getCardNumber().equals(creditCardNumber)) {
                        isValidCreditCard = true;
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
        if (!isValidCreditCard) {
            System.out.println("The Credit Card Number entered does not exist and/or does"
                    + "not belong to that customer. Sale Canceled.");
            return;
        }
        
        for (int i = 0; i < amount; i++) {
            Random rand = new Random();
            
            //Generate a unique serial number for the ticket
            boolean isSerialInUse = false;
            String serialNumberString = "000000000";
            do {
                int serialNumber = rand.nextInt(999999999);
                serialNumberString = Integer.toString(serialNumber);
                
                for (Customer customer : customers.values()) {
                    for (Ticket ticket : customer.getTickets()) {
                        if (ticket.getSerialNumber() == serialNumberString) {
                            isSerialInUse = true;
                        }
                    }
                }
            } while (isSerialInUse);
            
            boolean isShowDuringPeriod = false;
            
            // Get the ticket price from the show date
            Client sellingClient = null;
            BigDecimal ticketPrice = new BigDecimal(0);
            ticketPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            for (Client client : clients.values()) {
                for (Show show : client.getShows()) {
                    Calendar currentShowStartDate = show.getStartDate();
                    Calendar currentShowEndDate = show.getEndDate();
                    
                    if (isShowDuringPeriod(date, currentShowStartDate, currentShowEndDate)) {
                        ticketPrice = show.getPrice();
                        sellingClient = client;
                        isShowDuringPeriod = true;
                    }
                }
            }
            
            // Check if show is scheduled for that time frame and exit method if not
            if (!isShowDuringPeriod) {
                System.out.println("A show is not scheduled for that time frame.");
                return;
            }
            
            // Add the ticket to the customer
            Ticket ticket = null;
            if (ticketType == AdvanceTicket.class) {
                ticket = customers.get(customerId).addAdvanceTicket(new AdvanceTicket(serialNumberString, date, ticketPrice));
            }
            
            if (ticketType == StudentAdvanceTicket.class) {
                ticket = customers.get(customerId).addStudentAdvanceTicket(new StudentAdvanceTicket(serialNumberString, date, ticketPrice));
            }
            
            if (ticketType == RegularTicket.class) {
                ticket = customers.get(customerId).addRegularTicket(new RegularTicket(serialNumberString, date, ticketPrice));
            }
            
            // Add the money from the ticket sale to the client and theater
            BigDecimal newTicketPrice = ticket.getPrice();
            sellingClient.addBalance(newTicketPrice.divide(new BigDecimal(2)));
            balance = balance.add(newTicketPrice.divide(new BigDecimal(2)));
        }
    }
    
    /**
     * Gives money from theater to client
     * @param clientId
     * @param amount
     */
    public void payClient(UUID clientId, BigDecimal amount) {
        if (balance.compareTo(amount) > 0) {
            clients.get(clientId).addBalance(amount);
            balance.subtract(amount);
        } else {
            System.out.println("Theater does not have enough funds to pay client");
            return;
        }
    }
    
    /**
     * Gets the current client balance
     * @param clientId
     * @return
     */
    public BigDecimal getClientBalance(UUID clientId) {
        return clients.get(clientId).getBalance();
    }
    
    /**
     * Print tickets from a specific date
     * @param date
     */
    public void PrintTicketsOnDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        for (Customer customer : customers.values()) {
            for (Ticket ticket : customer.getTickets()) {
                if (sdf.format(ticket.getDate().getTime()).equals(sdf.format(date.getTime()))) {
                    System.out.println(ticket.toString());
                }
            }
        }
    }
    
    /**
     * Verify if show is playing during a specific period
     * @param showDate
     * @param startDate
     * @param endDate
     * @return
     */
    private boolean isShowDuringPeriod(Calendar showDate, Calendar startDate, Calendar endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        if (sdf.format(showDate.getTime()).equals(sdf.format(startDate.getTime())) ||
                sdf.format(showDate.getTime()).equals(sdf.format(endDate.getTime())) ||
                sdf.format(showDate.getTime()).equals(sdf.format(startDate.getTime())) ||
                sdf.format(showDate.getTime()).equals(sdf.format(endDate.getTime())) ||
                (showDate.after(startDate) && showDate.before(endDate)) ||
                (showDate.after(startDate) && showDate.before(endDate))) {
            return true;
        } else {
            return false;
        }
    }
}
