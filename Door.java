import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {


    private WaitingArea waitingArea;

    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    private static Timer timer; //Timer for producing at intervals

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (waitingArea.isFull()) { // wait while the area is full
                    try { wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                timer = new Timer(); //Wait before pushing a guest

                int delay = (5 + new Random().nextInt(SushiBar.doorWait)) * 1000;
                timer.schedule(new PushCustomer(), delay);
            }
        }
    }

    class PushCustomer extends TimerTask {
        public void run() {
            Customer customer = new Customer();
            waitingArea.enter(customer);
            SushiBar.write("Customer #"+customer.getCustomerID()+" is now waiting");
            SushiBar.customerCounter.increment(); //increment customercounter
            notify(); //Notify waitress that a new customer is pushed
            timer.cancel();
        }
    }
}
