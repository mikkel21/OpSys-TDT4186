import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    //Giving customer an id which is unique to the process
    private AtomicInteger id;
    private boolean ordered;
    private int eating_delay;

    public Customer() {
        // TODO Implement required functionality
        id = new AtomicInteger();
        ordered = false;
        eating_delay = (5 + new Random().nextInt(5)) * 1000;
    }

    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
        // TODO Implement required functionality
        ordered = true;
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        // TODO Implement required functionality
        return id.intValue();
    }

    public int getEating_delay() {
        return eating_delay;
    }
    // Add more methods as you see fit
}
