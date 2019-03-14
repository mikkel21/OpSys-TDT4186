import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Waitress implements Runnable {


    private final WaitingArea waitingArea;

    public Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    @Override
    public void run() {
        while (SushiBar.isOpen || (!waitingArea.isEmpty())) { //Run as long as sushibar is open or there are customers waiting
            Customer currentCustomer; //Customer being waited
            synchronized (waitingArea) { //Synchronize and monitor waitingArea
                while (waitingArea.isEmpty()) { // wait while the area is empty
                    try {
                        waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentCustomer = waitingArea.next(); //get next customer
            }
            try {
                Thread.sleep(new Random().nextInt(SushiBar.waitressWait)); //sleep for maximum waitressWait seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCustomer.order(); //calculate the order

            //add to the statistics:
            SushiBar.servedOrders.add(currentCustomer.getEaten_orders());
            SushiBar.takeawayOrders.add(currentCustomer.getTakeAway_orders());
            SushiBar.totalOrders.add(currentCustomer.getMax_orders());

            try {
                Thread.sleep(new Random().nextInt(SushiBar.customerWait)); //sleep while customer is eating/ordering
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SushiBar.write("Customer #" + currentCustomer.getCustomerID() + " is now leaving");
        }
    }
}