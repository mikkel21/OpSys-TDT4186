import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Door implements Runnable {


    private final WaitingArea waitingArea;

    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    @Override
    public void run() {
        while (SushiBar.isOpen) { //run as long as SushiBar is open
            try {
                Thread.sleep(new Random().nextInt(SushiBar.doorWait)); //sleep for a maximum of doorWait seconds
                Customer customer = new Customer();
                waitingArea.enter(customer); //put customer in the waitingArea queue
                SushiBar.write("Customer #" + customer.getCustomerID() + " is now waiting");
                SushiBar.customerCounter.increment(); //increment customerCounter
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        SushiBar.write("***** DOOR CLOSED *****");
    }
}
