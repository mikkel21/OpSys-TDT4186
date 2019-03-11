import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    private int capacity;
    private Queue<Customer> customers;

    public WaitingArea(int capacity) {
        this.capacity = capacity;
        customers = new LinkedList<Customer>();
    }

    //Add customer to queue
    public synchronized void enter(Customer customer) {
        customers.add(customer);
    }

    //Remove customer from queue and return
    public synchronized Customer next() { return customers.poll(); }

    public int getQueueSize() { return customers.size(); }

    public int getCapacity() { return capacity; }
}
