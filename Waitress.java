import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Waitress implements Runnable {


    private WaitingArea waitingArea;
    private Customer currentCustomer;

    public Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    @Override
    public void run() {
        while (SushiBar.isOpen || (!waitingArea.isEmpty())) { //Run as long as sushibar is open or there are customers waiting
            synchronized (waitingArea) {
                while (waitingArea.isEmpty()) { // wait while the area is empty
                    try {
                        waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentCustomer = waitingArea.next();
            }
            try {
                Thread.sleep(new Random().nextInt(SushiBar.waitressWait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCustomer.order();

            //add to the statistics:
            SushiBar.servedOrders.add(currentCustomer.getEaten_orders());
            SushiBar.takeawayOrders.add(currentCustomer.getTakeAway_orders());
            SushiBar.totalOrders.add(currentCustomer.getMax_orders());

            try {
                Thread.sleep(new Random().nextInt(SushiBar.customerWait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SushiBar.write("Customer #" + currentCustomer.getCustomerID() + " is now leaving");
        }
    }
}