import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a credit card for a customer
 *
 */
public class CreditCard implements Serializable {
    private String cardNumber;
    private Calendar expDate;

    /**
     * Creates a new credit card
     * @param cardNumber
     * @param expDate
     */
    public CreditCard(String cardNumber, Calendar expDate) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
    }

    /**
     * Copy constructor
     * 
     * @param creditCard
     */
    public CreditCard(CreditCard creditCard) {
        this.cardNumber = creditCard.getCardNumber();
        this.expDate = (Calendar)creditCard.getExpDate().clone();
    }

    /**
     * Get expiration date of credit card
     * @return
     */
    public Calendar getExpDate() {
        return expDate;
    }

    /**
     * Get credit card number
     * @return
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Displays info about credit card
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Credit Card Number: " + cardNumber + " - ");
        result.append("Credit Card Expiration: " + (expDate.get(Calendar.MONTH) + 1) +
                "/" + expDate.get(Calendar.YEAR));
        return result.toString();
    }
}
