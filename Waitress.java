import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
        while (SushiBar.isOpen) {
            synchronized (waitingArea) {
                while (waitingArea.isEmpty()) { // wait while the area is empty
                    try { waitingArea.wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                try {
                    currentCustomer = waitingArea.next();
                    SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now fetched");

                    TimeUnit.MILLISECONDS.sleep( new Random().nextInt(SushiBar.waitressWait) );
                    currentCustomer.order();
                    SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now eating");
                    //add to the statistics:
                    SushiBar.servedOrders.add(currentCustomer.getEaten_orders());
                    SushiBar.takeawayOrders.add(currentCustomer.getTakeAway_orders());
                    SushiBar.totalOrders.add(currentCustomer.getMax_orders());

                    Thread.sleep(SushiBar.customerWait);
                    //TimeUnit.SECONDS.sleep((5 + new Random().nextInt(SushiBar.customerWait)) * 1000);

                    SushiBar.write("Customer #"+currentCustomer.getCustomerID()+" is now leaving");

                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }

}