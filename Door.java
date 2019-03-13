import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
        while (SushiBar.isOpen) {
            synchronized (waitingArea) {
                while (waitingArea.isFull()) { // wait while the area is full
                    try { waitingArea.wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep( new Random().nextInt(SushiBar.doorWait) );
                    Customer customer = new Customer();
                    waitingArea.enter(customer);
                    SushiBar.write("Customer #"+customer.getCustomerID()+" is now waiting");
                    SushiBar.customerCounter.increment(); //increment customerCounter
                } catch (InterruptedException e) {
                    System.out.println(e);
                }

            }
        }
        SushiBar.write("***** DOOR CLOSED *****");

    }

}
