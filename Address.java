import java.io.Serializable;

/**
 * Represents an address
 *
 */
public class Address implements Serializable {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    /**
     * Creates a new address
     * @param streetAddress
     * @param city
     * @param state
     * @param zipCode
     */
    public Address(String streetAddress, String city, String state, String zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    /**
     * Copy constructor
     * 
     * @param address
     */
    public Address(Address address) {
        this.streetAddress = address.getStreetAddress();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
    }

    /**
     * Set street address
     * @param streetName
     */
    public void setStreetAddress(String streetName) {
        streetAddress = streetName;
    }

    /**
     * Set city
     * @param cityName
     */
    public void setCity(String cityName) {
        city = cityName;
    }

    /**
     * Set state
     * @param stateName
     */
    public void setState(String stateName) {
        state = stateName;
    }

    /**
     * Set zip code
     * @param zipCodeNumber
     */
    public void setZipcode(String zipCodeNumber) {
        zipCode = zipCodeNumber;
    }

    /**
     * Get Street Address
     * @return
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Get city
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Get state
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * Get zipcode
     * @return
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Display formatted address
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", streetAddress, city, state, zipCode);
    }
}
