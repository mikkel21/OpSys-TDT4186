import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    //Giving customer an id which is unique to the process
    private AtomicInteger id;
    private boolean ordered;
    private int eating_delay;

    public Customer() {
        id = new AtomicInteger();
        ordered = false;
        eating_delay = (5 + new Random().nextInt(5)) * 1000;
    }

    //TODO: implement orders as described in assignment
    public synchronized void order(){
        ordered = true;
    }

    public int getCustomerID() { return id.intValue(); }

    public int getEating_delay() {
        return eating_delay;
    }
}
