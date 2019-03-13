import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    //Giving customer an id which is unique to the process
    private AtomicInteger id;

    private int max_orders;
    private int eaten_orders;
    private int takeAway_orders;

    public int getMax_orders() {
        return max_orders;
    }

    public int getEaten_orders() {
        return eaten_orders;
    }

    public int getTakeAway_orders() {
        return takeAway_orders;
    }

    private int eating_delay;

    public Customer() {
        id = new AtomicInteger();

        eating_delay = (5 + new Random().nextInt(SushiBar.customerWait)) * 1000;
    }

    //TODO: implement orders as described in assignment
    public synchronized void order(){
        // Number of orders should be random and less than max orders
        // Number of orders = Eaten orders + TakeAway orders
        Random rand = new Random();
        max_orders = rand.nextInt(SushiBar.maxOrder);
        eaten_orders = rand.nextInt(max_orders);
        takeAway_orders = max_orders - eaten_orders;
    }

    public int getCustomerID() { return id.intValue(); }

    public int getEating_delay() {
        return eating_delay;
    }
}
