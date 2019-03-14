import java.util.Random;

public class Customer {

    private int id;

    private int max_orders; //Total orders for this customer
    private int eaten_orders; //Orders eaten in restaurant
    private int takeAway_orders; //Orders taken away

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

        //Decide number of orders for each customer(minimum 1 order)
        max_orders = rand.nextInt(SushiBar.maxOrder + 1);
        eaten_orders = rand.nextInt(max_orders  + 1);
        takeAway_orders = max_orders - eaten_orders;
        SushiBar.write("Customer #" + this.getCustomerID() + " is now eating");
    }

    public int getCustomerID() { return id; }

}
