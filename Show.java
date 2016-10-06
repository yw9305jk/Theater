
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a show that can be played by a client at the theater
 *
 */
public class Show implements Serializable {
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private BigDecimal ticketPrice;

    /**
     * Creates a new show
     * @param name
     * @param startDate
     * @param endDate
     * @param ticketPrice
     */
    public Show(String name, Calendar startDate, Calendar endDate, BigDecimal ticketPrice) {
        this.name = name;
        this.startDate = (Calendar) startDate.clone();
        this.endDate = (Calendar) endDate.clone();
        this.ticketPrice = ticketPrice;
    }

    /**
     * Get start date of show
     * @return
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * Get end date of show
     * @return
     */
    public Calendar getEndDate() {
        return endDate;
    }
    
    /**
     * Get price of show
     * @return
     */
    public BigDecimal getPrice(){
        return ticketPrice;
    }
    
    /**
     * Set price of show
     * @return
     */
    public void setPrice(){
        
    }

    
    /**
     * Displays info about the show
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        StringBuilder result = new StringBuilder();
        result.append("Show: " + name + "\n");
        result.append("Start Date: " + sdf.format(startDate.getTime()) + "\n");
        result.append("End Date: " + sdf.format(endDate.getTime()) + "\n");
        result.append("Ticket Price: " + ticketPrice + "\n");
        return result.toString();
    }
}
