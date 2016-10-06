import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Represents a Student Advance Ticket
 *
 */
public class StudentAdvanceTicket extends Ticket implements Serializable{
    private static BigDecimal discount = new BigDecimal(.35);
    private static String ticketType = "Student Advance";
    
    public StudentAdvanceTicket(StudentAdvanceTicket ticket) {
        super(ticket);
    }
    
    /**
     * Creates a Student Advance Ticket
     * @param serialNumber
     * @param date
     * @param price
     */
    public StudentAdvanceTicket(String serialNumber, Calendar date, BigDecimal price){
        super(serialNumber, date, price.multiply(discount), ticketType);
    }
    
}
