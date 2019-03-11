import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    private int size;
    private Queue<Customer> customers;

    public WaitingArea(int size) {
        this.size = size;
        customers = new LinkedList<Customer>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        if(customers.size() < size) {
            customers.add(customer);
        }
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        // TODO Implement required functionality
        if (customers.size() > 0) {
            return customers.poll();
        }
        System.out.println("There are no more customers in the queue");
        return null;
    }

    // Add more methods as you see fit
}
