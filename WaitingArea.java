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
        customers = new LinkedList<>();
    }

    //Add customer to queue
    public synchronized void enter(Customer customer) {
        customers.add(customer);
        System.out.println(customers);
        notifyAll();
    }

    public synchronized boolean isFull() {
        return getQueueSize() == getCapacity();
    }

    public synchronized boolean isEmpty() {
        return getQueueSize()==0;
    }

    //Remove customer from queue and return
    public synchronized Customer next() {
        notifyAll();
        return customers.poll();
    }

    public int getQueueSize() { return customers.size(); }

    public int getCapacity() { return capacity; }
}
