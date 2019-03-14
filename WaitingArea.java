import java.util.LinkedList;
import java.util.Queue;


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
        notify();
    }

    //Remove customer from queue and return
    public synchronized Customer next() {
        Customer c = customers.poll();
        SushiBar.write("Customer #" + c.getCustomerID() + " is now fetched");
        notify();
        return c;
    }

    public synchronized boolean isFull() {
        return getQueueSize() == getCapacity();
    }

    public synchronized boolean isEmpty() {
        return getQueueSize()==0;
    }

    public int getQueueSize() { return customers.size(); }

    public int getCapacity() { return capacity; }
}
