import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 15;
    private static int waitressCount = 8;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2000; // Used to calculate the time the customer spends eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        // TODO initialize the bar and start the different threads

        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity); //initiate the waitingarea
        Thread producer = new Thread(new Door(waitingArea)); //initiate the producer thread
        List<Thread> consumers = new ArrayList<>();
        for (int i = 0; i < waitressCount; i++) consumers.add(new Thread(new Waitress(waitingArea))); //initiate the consumers thread

        Clock clock = new Clock(duration);
        producer.start();
        for (Thread t : consumers) {
            t.start();
        }

        if (!isOpen) { // stop door when sushibar is closed
            //Is a try/catch really necessary?
            try {
                producer.join(); //stop producer thread
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        if (!isOpen && waitingArea.isEmpty()) { //stop waitresses when sushibar is closed and there are no more customers in queue
            SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW *****");

            //Is a try/catch really necessary?
            try {
                for (Thread t : consumers) {
                    t.join(); //stop consumer thread
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }


    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
