import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a client that can play shows at the theater
 *
 */
public class Client implements Serializable {
    private UUID clientId;
    private String name;
    private Address address;
    private String phoneNumber;
    private BigDecimal balance;
    private List<Show> shows = new ArrayList<Show>();

    /**
     * Creates a new client
     * @param clientId
     * @param name
     * @param address
     * @param phoneNumber
     */
    public Client(UUID clientId, String name, Address address, String phoneNumber) {
        this.clientId = clientId;
        this.name = name;
        this.address = new Address(address);
        this.phoneNumber = phoneNumber;
        this.balance = new BigDecimal(0);
    }
    
    /**
     * Add balance to client
     * @param balance
     */
    public void addBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }
    
    /**
     * Get client's balance
     * @return
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Gets a list of shows for the client
     * @return
     */
    public List<Show> getShows() {
        return shows;
    }

    /**
     * Displays info about the client
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Client Id: " + clientId + "\n");
        result.append("Name: " + name + "\n");
        result.append("Address: " + address.toString() + "\n");
        result.append("Phone Number: " + phoneNumber + "\n");
        result.append("Balance: " + balance + "\n");
        return result.toString();
    }
}
