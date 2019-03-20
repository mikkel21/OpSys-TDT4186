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
        while (isFull()) { //wait while the area is full
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        customers.add(customer);
        notifyAll();
    }

    //Remove customer from queue and return
    public synchronized Customer next() {
        while (isEmpty()) { // wait while the area is empty
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Customer c = customers.poll();
        SushiBar.write("Customer #" + c.getCustomerID() + " is now fetched");
        notify();
        return c;
    }

    public boolean isFull() {
        return getQueueSize() == getCapacity();
    }

    public boolean isEmpty() {
        return getQueueSize()==0;
    }

    public int getQueueSize() { return customers.size(); }

    public int getCapacity() { return capacity; }
}
