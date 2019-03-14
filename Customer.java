import java.util.Random;

public class Customer {

    //Giving customer an id which is unique to the process
    private int id;

    private int max_orders;
    private int eaten_orders;
    private int takeAway_orders;

    public int getMax_orders() {
        return max_orders;
    }

    public int getEaten_orders() {
        return eaten_orders;
    }

    public int getTakeAway_orders() {
        return takeAway_orders;
    }

    public Customer() {
        id = SushiBar.customerCounter.get();
    }

    //TODO: implement orders as described in assignment
    public synchronized void order(){
        Random rand = new Random();
        max_orders = rand.nextInt(SushiBar.maxOrder + 1);
        eaten_orders = rand.nextInt(max_orders  + 1);
        takeAway_orders = max_orders - eaten_orders;
        SushiBar.write("Customer #" + this.getCustomerID() + " is now eating");
    }

    public int getCustomerID() { return id; }

}
