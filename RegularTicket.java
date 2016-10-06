import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Represents a Regular ticket
 *
 */
public class RegularTicket extends Ticket implements Serializable {
    private static String ticketType = "Regular";
    
    public RegularTicket(RegularTicket ticket) {
        super(ticket);
    }
    
    /**
     * Creates a Regular Ticket
     * @param serialNumber
     * @param date
     * @param price
     */
    public RegularTicket(String serialNumber, Calendar date, BigDecimal price) {
        super(serialNumber, date, price, ticketType);
    }
}
