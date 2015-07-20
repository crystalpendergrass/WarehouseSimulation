package assignment4;

/**
 * Defines the Widget class
 * 
 * @version Assignment 4, 2 April 2014
 */

public class Widget {

	// Declarations of the instance variable of Widget class
	private String name;
	private int numReceived;
	private int numInInventory;
	private double price;
	
	final private double VALID_WIDGET_PRICE = 5;

	/**
	 * Default constructor for the Widget class.
	 */
	public Widget(){
		name = "S##";
		numReceived = 0;
		numInInventory = numReceived;
		price = 0;
	}

	/*
	 * Customer constructor for the Widget class
	 * 
	 * @param name the name of the shipment of widgets
	 * @param numReceived the number of widgets contained in the shipment
	 * @param price the cost of each widget in the shipment
	 */
	public Widget(String name, int numReceived, double price){
		this.name = name;
		this.numReceived = numReceived;
		numInInventory = numReceived;
		if(price <= 0){
			this.price = VALID_WIDGET_PRICE;
		}
		else
			this.price = price;
	}

	/**
	 * Modifies the name of the shipment of widgets
	 * 
	 * @param name the new name of shipment of widgets
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Modifies the number of widgets received in a shipment
	 * 
	 * @param numReceived the new number of widgets received in a shipment
	 */
	public void setNumReceived(int numReceived){
		this.numReceived = numReceived;
	}

	/**
	 * Modifies the number of widgets from a shipment that are currently in stock
	 * 
	 * @param numInInventory the new number of widgets from a shipment that are currently in stock
	 */
	public void setNumInInventory(int numInInventory){
		this.numInInventory = numInInventory;
	}

	/**
	 * Modifies the price of each widget.  If price is less than or equal to zero, set price to valid value
	 * 
	 * @param price the new price of each widget
	 */
	public void setPrice(double price){
		if(price <= 0){
			this.price = VALID_WIDGET_PRICE;
		}
		else
			this.price = price;
	}

	/*
	 * Returns the current name of the shipment of widgets
	 * 
	 * @return the name of the shipment of widgets
	 */
	public String getName(){
		return name;
	}

	/*
	 * Returns the current number of widgets received in a shipment
	 * 
	 * @return the number of widgets received in a shipment
	 */
	public int getNumReceived(){
		return numReceived;
	}

	/*
	 * Returns the current number of widgets from a shipment that are currently in stock
	 * 
	 * @return the number of widgets from a shipment that are currently in stock
	 */
	public int getNumInInventory(){
		return numInInventory;
	}

	/*
	 * Returns the current price of each widget
	 * 
	 * @return the price of each widget
	 */
	public double getPrice(){
		return price;
	}

	/**
	 * Returns a text description of a Widget object
	 *
	 * @return A text description of a Widget object
	 */
	@Override
	public String toString(){
		return name + " " + numReceived + " $" + String.format("%.2f", price);
	}
}
