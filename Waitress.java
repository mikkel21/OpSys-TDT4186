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
                while (waitingArea.isEmpty()) { // wait while the area is empty
                    try { wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                timer = new Timer(); //Initiate timer and continue getting customers

                int delay = (5 + new Random().nextInt(SushiBar.waitressWait)) * 1000; //Wait before fetching a guest
                timer.schedule(new FetchGuest(), delay);

                delay = delay + (5 + new Random().nextInt(5)) * 1000;
                timer.schedule(new TakeOrder(), delay); //Wait before taking the order

                timer.schedule(new WaitToEat(), currentCustomer.getEating_delay()); //Wait for customer to eat
            }
        }
    }

    //TimerTasks:
    class FetchGuest extends TimerTask {
        public void run() {
            currentCustomer = waitingArea.next();
            //Notify Door that a customer is popped
            notify();
            SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now fetched");
        }
    }
    class TakeOrder extends TimerTask {
        public void run() {
            currentCustomer.order();
            SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now eating");
            //add to the statistics:
            SushiBar.servedOrders.add(currentCustomer.getEaten_orders());
            SushiBar.takeawayOrders.add(currentCustomer.getTakeAway_orders());
            SushiBar.totalOrders.add(currentCustomer.getMax_orders());
        }
    }
    class WaitToEat extends TimerTask {
        public void run() {
            timer.cancel();
            SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now leaving");

        }
    }
}