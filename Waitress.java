import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {


    private WaitingArea waitingArea;
    private Customer currentCustomer;

    public Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    //Timer for consuming at intervals
    private static Timer timer;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (waitingArea.getQueueSize()==0) {
                    try { wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                //Initiate timer and continue getting customers
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
        }
    }

    //TimerTasks:
    class FetchGuest extends TimerTask {
        public void run() {
            currentCustomer = waitingArea.next();
            //Notify Door that a customer is popped
            notify();
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