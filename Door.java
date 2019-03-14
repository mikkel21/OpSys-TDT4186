import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Door implements Runnable {


    private WaitingArea waitingArea;

    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }


    @Override
    public void run() {
        while (SushiBar.isOpen) {
            synchronized (waitingArea) {
                while (waitingArea.isFull()) { // wait while the area is full
                    try {
                        waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(new Random().nextInt(SushiBar.doorWait));
                    Customer customer = new Customer();
                    waitingArea.enter(customer);
                    SushiBar.write("Customer #" + customer.getCustomerID() + " is now waiting");
                    SushiBar.customerCounter.increment(); //increment customerCounter
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
        SushiBar.write("***** DOOR CLOSED *****");
    }
}
