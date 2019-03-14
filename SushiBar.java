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



        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity); //initiate the waitingArea
        Thread producer = new Thread(new Door(waitingArea)); //initiate the producer thread

        List<Thread> consumers = new ArrayList<>(); //create a list for the consumer threads(waitresses)
        for (int i = 0; i < waitressCount; i++) consumers.add(new Thread(new Waitress(waitingArea))); //initiate the consumers thread

        Clock clock = new Clock(duration); //initiate a new clock, timer starts and stops after "duration" seconds
        producer.start(); //start producer thread(door)

        consumers.forEach(thread -> thread.start()); //start consumer threads(waitresses)

        consumers.forEach(thread -> { //join consumer threads, after this they will stop when the while() is finished in their run method
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        SushiBar.write("***** NO MORE CUSTOMERS - THE DOOR IS CLOSED NOW *****");
        SushiBar.write("------ STATISTICS ----- \nTotal number of customers: "+customerCounter.get()+"\nTotal number of orders: "+totalOrders.get()+"\nTotal number of takeaway orders: "+takeawayOrders.get()+"\nTotal number of eaten orders: "+servedOrders.get());
    }

    //Writes actions in the log file and console
    public synchronized static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
