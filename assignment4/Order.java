package assignment4;

/**
 * Defines the Order class
 * 
 * @version Assignment 4, 2 April 2014
 */

public class Order {

	// Declarations of the instance variable of Order class
	private String name;
	private int numOrdered;
	private int numToShip;
	private double orderCost;

	/**
	 * Default constructor for the Order class.
	 */
	public Order(){
		name = "O##";
		numOrdered = 0;
		numToShip = numOrdered;
		orderCost = 0;
	}

	/*
	 * Customer constructor for the Order class
	 * 
	 * @param name the name of the order for widgets
	 * @param numOrdered the number of widgets ordered
	 */
	public Order(String name, int numOrdered){
		this.name = name;
		this.numOrdered = numOrdered;
		numToShip = numOrdered;
		orderCost = 0;
	}

	/**
	 * Modifies the name of the order for widgets
	 * 
	 * @param name the new name of the order for widgets
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Modifies the number of widgets ordered
	 * 
	 * @param numOrdered the new number of widgets ordered
	 */
	public void setNumOrdered(int numOrdered){
		this.numOrdered = numOrdered;
	}

	/**
	 * Modifies the number of widgets needed to complete an order
	 * 
	 * @param numToShip the number of widgets needed to complete an order
	 */
	public void setNumToShip(int numToShip){
		this.numToShip = numToShip;
	}

	/**
	 * Modifies the total cost of the order
	 * 
	 * @param orderCost the new total cost of the order
	 */
	public void setOrderCost(double orderCost){
		this.orderCost = orderCost;
	}

	/*
	 * Returns the name of the order for widgets
	 * 
	 * @return the name of the order for widgets
	 */
	public String getName(){
		return name;
	}

	/*
	 * Returns the number of widgets ordered
	 * 
	 * @return the number of widgets ordered
	 */
	public int getNumOrdered(){
		return numOrdered;
	}

	/*
	 * Returns the number of widgets needed to complete an order
	 * 
	 * @return the number of widgets needed to complete an order
	 */
	public int getNumToShip(){
		return numToShip;
	}

	/*
	 * Returns the total cost of the order
	 * 
	 * @return the total cost of the order
	 */
	public double getOrderCost(){
		return orderCost;
	}

	/**
	 * Adds an amount greater than zero to the total cost of an order of widgets.
	 *
	 * @param amount the cost of widgets being added to an order
	 */
	public void addCost(double amount){
		if(amount > 0)
			orderCost = orderCost + amount;
	}

	/**
	 * Returns a text description of an Order object
	 *
	 * @return A text description of an Order object
	 */
	@Override
	public String toString(){
		return name + " " + numOrdered + " " + String.format("%.2f", orderCost);
	}



}
