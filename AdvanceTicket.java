import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Represents an Advance Ticket
 *
 */
public class AdvanceTicket extends Ticket implements Serializable {
    private static BigDecimal discount = new BigDecimal(.70);
    private static String ticketType = "Advance";
    
    public AdvanceTicket(AdvanceTicket ticket) {
        super(ticket);
    }
    
    /**
     * Creates a new Advance Ticket
     * @param serialNumber
     * @param date
     * @param price
     */
    public AdvanceTicket(String serialNumber, Calendar date, BigDecimal price) {
        super(serialNumber, date, price.multiply(discount), ticketType);
    }
}
