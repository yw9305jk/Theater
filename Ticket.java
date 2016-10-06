import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a ticket
 *
 */
public abstract class Ticket implements Serializable{
    private String serialNumber;
    private String ticketType;
    private Calendar date;
    private BigDecimal price;

    public Ticket(){
        System.out.println("No current tickets.");
    }
    
    /**
     * Creates a new Ticket
     * @param serialNumber
     * @param date
     * @param price
     * @param ticketType
     */
    public Ticket(String serialNumber, Calendar date, BigDecimal price, String ticketType){
        this.serialNumber = serialNumber;
        this.date = date;
        this.price = price;
        this.ticketType = ticketType;
    }
    
    /**
     * Copy constructor
     * 
     * @param tickets
     */
    public Ticket(Ticket ticket) {
        this.serialNumber = ticket.getSerialNumber();
        this.date = (Calendar)ticket.getDate().clone();
        this.price = (BigDecimal)ticket.getPrice();
        this.ticketType = ticket.getTicketType();
    }
    
    public String getSerialNumber(){
        return serialNumber;
    }
    
    public void setSerialNumber() {
        
    }
    
    public Calendar getDate(){
        return date;
    }
    
    public void setDate(){
        
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
    public void setPrice(){
        
    }
    
    public String getTicketType(){
        return ticketType;
    }
    
    public void setTicketType(){
        
    }
    

    /**
     * Displays info about Ticket
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        StringBuilder result = new StringBuilder();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        result.append("Ticket Number: " + serialNumber + " - ");
        result.append("Ticket Date: " + (date.get(Calendar.MONTH) + 1) +
                "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR) + " - ");
        result.append("Ticket Price: " + formatter.format(price) + " - ");
        result.append("Ticket Type: " + ticketType);
        return result.toString();
    }
}
