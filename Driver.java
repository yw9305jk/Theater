import java.util.List;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Driver {
    public static void main(String[] args) {
        
        int userChoice;
        boolean quit = false;

        Theater theater = new Theater();
        Scanner input = new Scanner(System.in);
        
        // Display commands to user
        displayHelp();
        
        // Loop program to wait on user for input until exited
        while (quit == false) {
            System.out.println("Enter a command:");

            try {
                userChoice = input.nextInt();
                input.nextLine(); // Skips line after input

                // Get user command choice
                while ((userChoice > 18) || (userChoice < 0)) {
                    System.out.println("Enter a number between 1-12: ");
                    userChoice = input.nextInt();
                    input.nextLine(); // Skips line after input
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid Input!");
                input = new Scanner(System.in);
                continue;
            }

            // Execute command based on user choice
            switch (userChoice) {
            case 0:
                System.out.println("Exiting...");
                quit = true;
                System.exit(0);
                break;
            case 1:
                addClient(theater, input);
                break;
            case 2:
                removeClient(theater, input);
                break;
            case 3:
                theater.listClients();
                break;
            case 4:
                addCustomer(theater, input);
                break;
            case 5:
                removeCustomer(theater, input);
                break;
            case 6:
                addCreditCard(theater, input);
                break;
            case 7:
                removeCreditCard(theater, input);
            case 8:
                theater.listCustomers();
                break;
            case 9:
                addShow(theater, input);
                break;
            case 10:
                theater.listShows();
                break;
            case 11:
                storeData(theater, input);
                break;
            case 12:
                theater = retrieveData(theater, input);
                break;
            case 13:
                sellTicket(RegularTicket.class, theater, input);
                break;
            case 14:
                sellTicket(AdvanceTicket.class, theater, input);
                break;
            case 15:
                System.out.println("Must show valid student id");
                sellTicket(StudentAdvanceTicket.class, theater, input);
                break;
            case 16:
                payClient(theater, input);
                break;
            case 17:
                printTicketsOnDate(theater, input);
                break;
            case 18:
                displayHelp();
                break;
            }
        }

    }

    /**
     * Allow user to add a client to the system
     * @param theater
     * @param input
     */
    private static void addClient(Theater theater, Scanner input) {
        System.out.println("Enter Client's Name: ");
        String name = input.nextLine();
        if (!isValidName(name)) {
            return;
        }

        System.out.println("Enter Client's Phone Number: ");
        String phoneNumber = input.nextLine();
        if (!isValidPhoneNumber(phoneNumber)) {
            return;
        }

        System.out.println("Enter Client's Street Address: ");
        String streetAddress = input.nextLine();

        System.out.println("Enter Client's City: ");
        String city = input.nextLine();

        System.out.println("Enter Client's State: ");
        String state = input.nextLine();

        System.out.println("Enter Client's Zipcode: ");
        String zipCode = input.nextLine();
        if (!isValidZipCode(zipCode)) {
            return;
        }

        Address clientAddress = new Address(streetAddress, city, state, zipCode);

        // Add client to hash map
        theater.addClient(name, clientAddress, phoneNumber);
        
        System.out.println("Client has been created");
    }
    
    /**
     * Allow user to remove a client from the system
     * @param theater
     * @param input
     */
    private static void removeClient(Theater theater, Scanner input) {
        System.out.println("Enter the Client's UUID: ");
        UUID clientId = UUID.fromString(input.nextLine());
        
        theater.removeClient(clientId);
        
        System.out.println("Client has been removed");
    }
    
    /**
     * Allow user to add a customer to the system
     * @param theater
     * @param input
     */
    private static void addCustomer(Theater theater, Scanner input) {
        System.out.println("Enter Customer's Name: ");
        String name = input.nextLine();
        if (!isValidName(name)) {
            return;
        }

        System.out.println("Enter Customer's Phone Number: ");
        String phoneNumber = input.nextLine();
        if (!isValidPhoneNumber(phoneNumber)) {
            return;
        }

        System.out.println("Enter Customer's's Street Address: ");
        String streetAddress = input.nextLine();

        System.out.println("Enter Customer's City: ");
        String city = input.nextLine();

        System.out.println("Enter Customer's State: ");
        String state = input.nextLine();

        System.out.println("Enter Customer's Zipcode: ");
        String zipCode = input.nextLine();
        if (!isValidZipCode(zipCode)) {
            return;
        }

        Address customerAddress = new Address(streetAddress, city, state, zipCode);
        
        CreditCard creditCard = createCreditCard(input);
        if (creditCard != null) {
            List<CreditCard> creditCardList = new ArrayList<CreditCard>();
            creditCardList.add(creditCard);
                
            theater.addCustomer(name, customerAddress, phoneNumber, creditCardList);
            
            System.out.println("Customer has been created");
        }
    }
    
    /**
     * Allow user to remove a customer from the system
     * @param theater
     * @param input
     */
    private static void removeCustomer(Theater theater, Scanner input) {
        System.out.println("Enter the Customer's ID: ");
        UUID customerId = setIdFromString(input.nextLine());
        if (customerId == null) {
            return;
        }
        
        theater.removeCustomer(customerId);
        
        System.out.println("Customer has been removed");
    }
    
    /**
     * Allow user to add a credit card to a customer in the system
     * @param theater
     * @param input
     */
    private static void addCreditCard(Theater theater, Scanner input) {
        System.out.println("Enter the Customer's ID: ");
        UUID customerId = setIdFromString(input.nextLine());
        if (customerId == null) {
            return;
        }
        
        CreditCard creditCard = createCreditCard(input);
        if (creditCard != null) {
            theater.addCreditCard(customerId, creditCard);
            System.out.println("Credit Card has been added to Customer");
        }
    }
    
    /**
     * Helper method to help in the creation of a credit card
     * TODO: Need to refactor so that theater controls creation of Credit Card
     * @param input
     * @return
     */
    private static CreditCard createCreditCard(Scanner input) {
        System.out.println("Enter Customer's Credit Card Number (no dashes): ");
        String creditCardNumber = input.nextLine();
        
        if (!Pattern.matches("[^a-zA-Z]*", creditCardNumber)) {
            System.out.println("Card number cannot contains letters");
            return null;
        }
        
        if (creditCardNumber.length() != 19) {
            System.out.println("Card number must be 19 digits");
            return null;
        }

        System.out.println("Enter Customer's Credit Card Expiration Month (1 - 12): ");
        int expMonth = -1;
        try {
            expMonth = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (expMonth < 1 || expMonth > 12) {
            System.out.println("Expiration month must be from 1 - 12");
            return null;
        }
        input.nextLine();

        System.out.println("Enter Customer's Credit Card Expiration Year (Ex. 1970): ");
        int expYear = -1;
        try {
            expYear = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (Calendar.getInstance().get(Calendar.YEAR) > expYear) {
            System.out.println("Expiration year must be greater than current year");
            return null;
        }
        input.nextLine();
        
        Calendar creditCardExpDate = Calendar.getInstance();
        creditCardExpDate.set(Calendar.MONTH, expMonth - 1);
        creditCardExpDate.set(Calendar.YEAR, expYear);
        return new CreditCard(creditCardNumber, creditCardExpDate);
    }
    
    /**
     * Allow user to remove a credit card from a customer in the system
     * @param theater
     * @param input
     */
    private static void removeCreditCard(Theater theater, Scanner input) {
        System.out.println("Enter Customer's Credit Card Number: ");
        String cardNumber = input.nextLine();
        
        theater.removeCreditCard(cardNumber);
        
        System.out.println("Credit Card has been removed");
    }
    
    /**
     * Allow user to add a show to a client in the system
     * @param theater
     * @param input
     */
    private static void addShow(Theater theater, Scanner input) {
        System.out.println("Enter the Client's ID: ");
        UUID clientId = setIdFromString(input.nextLine());
        if (clientId == null) {
            return;
        }
        
        System.out.println("Enter the Show's Name: ");
        String name = input.nextLine();
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        
        System.out.println("Enter the Show's Start Date (Ex. 08-23-1991): ");
        try {
            date = sdf.parse(input.nextLine());
        } catch (Exception ex) {
            System.out.println("Date formatted incorrectly");
            return;
        }
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        
        System.out.println("Enter the Show's End Date (Ex. 09-23-1991): ");
        try {
            date = sdf.parse(input.nextLine());
        } catch (Exception ex) {
            System.out.println("Date formatted incorrectly");
            return;
        }
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(date);
        
        System.out.println("Enter the price for a show ticket: ");
        BigDecimal ticketPrice = input.nextBigDecimal();
        
        theater.addShow(clientId, name, startDate, endDate, ticketPrice);
        
        System.out.println("Show has been added for Client");
    }
    
    /**
     * Allow user to save the current system data
     * @param theater
     * @param input
     */
    private static void storeData(Theater theater, Scanner input) {
        System.out.println("Enter the File Name: ");
        String fileName = input.nextLine();
        
        try {
            fileName += ".txt";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(theater);
            oos.close();
            System.out.println("Data has been saved");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
    
    /**
     * Allow user to retrieve saved system data
     * @param theater
     * @param input
     * @return
     */
    private static Theater retrieveData(Theater theater, Scanner input) {
        System.out.println("Enter the File Name: ");
        String fileName = input.nextLine();
        
        try {
            fileName += ".txt";
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Theater newTheater = (Theater)ois.readObject();
            ois.close();
            System.out.println("Data has been retrieved");
            return newTheater;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Sell regular tickets
     * @param theater
     * @param input
     * @return
     */
    private static <T extends Ticket> void sellTicket(Class<T> ticketType, Theater theater, Scanner input) {
        
        System.out.println("Enter the Amount of Regular Tickets to sell: ");
        int amount = input.nextInt();
        input.nextLine();
        
        System.out.println("Enter the Customer's ID Number: ");
        UUID customerId = setIdFromString(input.nextLine());
        if (customerId == null) {
            return;
        }
        
        System.out.println("Enter Customer's Credit Card Number (no dashes): ");
        String creditCardNumber = input.nextLine();
        
        
        if (!Pattern.matches("[^a-zA-Z]*", creditCardNumber)) {
            System.out.println("Card number cannot contains letters");
            return;
        }
        
        if (creditCardNumber.length() != 19) {
            System.out.println("Card number must be 19 digits");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        
        System.out.println("Enter the Show's Date (Ex. 08-23-1991): ");
        try {
            date = sdf.parse(input.nextLine());
        } 
        catch (Exception ex) {
            System.out.println("Date formatted incorrectly");
            return;
        }
        Calendar ticketShowDate = Calendar.getInstance(); // Ticket Date
        ticketShowDate.setTime(date);
        
        theater.<T>sellTicket(ticketType, customerId, amount, ticketShowDate,
                creditCardNumber);
    }
    
    /**
     * Pay the client
     * @param theater
     * @param input
     */
    private static void payClient(Theater theater, Scanner input) {
        System.out.println("Enter the Client's ID Number: ");
        UUID clientId = setIdFromString(input.nextLine());
        if (clientId == null) {
            return;
        }
        
        System.out.println("Client balance: " + theater.getClientBalance(clientId));
        
        System.out.println("Enter the amount to pay to the client: ");
        BigDecimal amount = new BigDecimal(input.nextInt());
        
        theater.payClient(clientId, amount);
    }
    
    /**
     * Print tickets on specific date
     * @param theater
     * @param input
     */
    private static void printTicketsOnDate(Theater theater, Scanner input) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        System.out.println("Enter a Date to display Tickets (Ex. 08-23-1991): ");
        try {
            date = sdf.parse(input.nextLine());
        } 
        catch (Exception ex) {
            System.out.println("Date formatted incorrectly");
            return;
        }
        Calendar ticketDate = Calendar.getInstance(); // Ticket Date
        ticketDate.setTime(date);
        
        theater.PrintTicketsOnDate(ticketDate);
    }
    
    /**
     * Allow user to display all available commands
     */
    private static void displayHelp() {
        System.out.println("0. Exit\n" + "1. Add Client\n" + "2. Remove Client\n" +
                "3. List all Clients\n" + "4. Add Customer\n" + "5. Remove Customer\n" +
                "6. Add a Credit Card\n" + "7. Remove a Credit Card\n" +
                "8. List all Customers\n" + "9. Add a Show/Play\n" + "10. List all Shows\n" +
                "11. Save Data\n" + "12. Retrieve Data\n" + "13. Sell Regular Tickets\n" +
                "14. Sell Advance Tickets\n" + "15. Sell Student Advance Tickets\n" + 
                "16. Pay Client\n" + "17. Print Tickets for Certain Day\n" + "18. Help\n");
    }
    
    /**
     * Check if name is valid
     * @param name
     * @return
     */
    private static boolean isValidName(String name) {
        if (Pattern.matches("[A-Z].*[0-9]", name)) {
            System.out.println("Name cannot contain numbers");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Check if phone number is valid
     * @param phoneNumber
     * @return
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("\\d{10}")) {
            return true;
        } else if(phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        } else if(phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        } else if(phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        } else {
            System.out.println("Phone number is not in the correct format");
            return false;
        }
    }
    
    /**
     * Check if zip code is valid
     * @param zipCode
     * @return
     */
    private static boolean isValidZipCode(String zipCode) {
        if (!Pattern.matches("[^a-zA-Z]*", zipCode)) {
            System.out.println("Zip code cannot contain letters");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Set UUID from string
     * @param id
     * @return
     */
    private static UUID setIdFromString(String id) {
        try {
            UUID testId = UUID.fromString(id);
            return testId;
        } catch (Exception ex) {
            System.out.println("ID must be a valid GUID");
            return null;
        }
    }
}