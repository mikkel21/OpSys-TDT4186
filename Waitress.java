import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    private WaitingArea waitingArea;
    private static Timer timer;
    private Customer currentCustomer;

    public Waitress(WaitingArea waitingArea) {
        // TODO Implement required functionality
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        //Initiate timer
        timer = new Timer();
        //Wait before fetching a guest
        int delay = (5 + new Random().nextInt(5)) * 1000;
        timer.schedule(new FetchGuest(), delay);
        //Wait before taking the order
        delay = delay + (5 + new Random().nextInt(5)) * 1000;
        timer.schedule(new TakeOrder(), delay);
        //Wait for customer to eat
        timer.schedule(new WaitToEat(), currentCustomer.getEating_delay());
    }

    class FetchGuest extends TimerTask {
        public void run() {
            currentCustomer = waitingArea.next();
        }
    }
    class TakeOrder extends TimerTask {
        public void run() {
            currentCustomer.order();
        }
    }
    class WaitToEat extends TimerTask {
        public void run() {
            timer.cancel();
        }
    }
}