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


    //Timer for producing at intervals
    private static Timer timer;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (waitingArea.getQueueSize() == waitingArea.getCapacity()) {
                    try { wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                timer = new Timer();
                //Wait before pushing a guest
                int delay = (5 + new Random().nextInt(5)) * 1000;
                timer.schedule(new PushCustomer(), delay);
            }
        }
    }

    class PushCustomer extends TimerTask {
        public void run() {
            waitingArea.enter(new Customer());
            //Notify waitress that a new customer is pushed
            notify();
            timer.cancel();
        }
    }
}
