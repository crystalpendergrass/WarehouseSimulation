package assignment4;

/**
 * Program simulates receiving shipments of widgets and using those
 * shipments to fulfill orders.  
 * 
 * @version Assignment 4, 2 April 2014
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;


public class WarehouseRunner {

	static Queue<Order> ordersToBeFilled = new LinkedList<>();
	static Stack<Widget> widgetsOnHand = new Stack<>();
	static Scanner in;
	static int widgetCount = 0;
	static Widget currentWidget;
	static Order currentOrder;
	static final boolean TESTING = false;
	static boolean processing = false;
	
	final static private double VALID_WIDGET_PRICE = 5;


	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null,	"Locate file containing warehouse data.", "LOCATE FILE", JOptionPane.PLAIN_MESSAGE);
		String storeFileName = chooseFile();
		
		try {
			int hour = 0;

			// Opening a file containing warehouse data
			// File warehouseFile = new File("warehouse_data.txt");

			File warehouseFile = new File(storeFileName);
			in = new Scanner(warehouseFile);
			// Only reading letters, numbers, and periods
			in.useDelimiter("[^\\p{Alnum}.]+");
			// If the file contains a token and the hour is not greater than 24, keeping reading file
			while (in.hasNext() && hour <= 24) {
				System.out.println("HOUR " + hour + " DETAILS");

				String name = in.next();
				// the line contains information for an order
				if(name.charAt(0) == 'O'){
					int numOrdered = Integer.parseInt(in.next());
					currentOrder = new Order(name, numOrdered);
					if(widgetsOnHand.isEmpty()){
						ordersToBeFilled.add(currentOrder);
						System.out.println("Order " + name + " for " + numOrdered + " widgets ordered added to queue.");
					}

					else{
						processing = true;
						while(processing){
							currentWidget = widgetsOnHand.pop();
							processOrder(currentOrder, currentWidget);
						}
					}
				}
				
				// the line contains information for a shipment
				else if(name.charAt(0) == 'S'){
					int numReceived = Integer.parseInt(in.next());
					double price = Double.parseDouble(in.next());
					// Verify price is greater than zero dollars
					if(price <= 0){
						double invalidPrice = price;
						price = VALID_WIDGET_PRICE;
						System.out.println("ERROR: Invalid price, $" + String.format("%.2f", invalidPrice)+ ". Price set to $" +
								String.format("%.2f", VALID_WIDGET_PRICE) + ".");
					}
					currentWidget = new Widget(name, numReceived, price);
					widgetCount = widgetCount + numReceived;

					if(ordersToBeFilled.isEmpty()){
						widgetsOnHand.add(currentWidget);
						System.out.println("Shipment " + name + " containing " + numReceived + " widgets added to stack.");
					}

					else{
						processing = true;
						while(processing){
							currentOrder = ordersToBeFilled.remove();
							processOrder(currentOrder, currentWidget);
						}
					}
				}
				
				// there is an error if the line does not begin with an "O" or "S"
				else{
					System.out.println("ERROR READING INPUT DATA!");
				}

				if(TESTING){
					System.out.println("\n***** CURRENT STATE OF STACK AND QUEUE *****");

					System.out.println("THE WIDGET STACK");
					if(widgetsOnHand.isEmpty()){
						System.out.println("   No widget(s) on hand.");
					}
					else{
						for(Widget w: widgetsOnHand){
							if(w.getNumInInventory()!=0)
								System.out.print("   " + w.getName() + "(" + w.getNumInInventory() + ")");					
						}
						System.out.println();
					}				

					System.out.println("THE ORDER QUEUE");
					if(ordersToBeFilled.isEmpty()){
						System.out.println("   No order(s) to be filled.");
					}
					else {
						for(Order o: ordersToBeFilled){
							System.out.print("   " + o.getName() + "(" + o.getNumToShip() + ")");				
						}
						System.out.println();
					}
					System.out.println();
				} // end printing queue and stack
				System.out.println();
				if(widgetCount > 0){
					System.out.println( widgetCount + " widgets remain in stock at the end of hour " + hour + ".\n");
				}
				else{
					System.out.println("There are no widgets in stock at the end of hour " + hour + ".\n");
				}
				
				hour++; // increase hour
			}
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: File containing warehouse data not found.");
		}
		in.close();

		System.out.println("END OF 24 HOURS: There are " + widgetCount + " widgets left in stock.");
	}

	public static String chooseFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			// User can only select files
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showOpenDialog(null);

			String path = chooser.getSelectedFile().getAbsolutePath();

			return path;

		} catch (NullPointerException e) {
			System.out.println("ERROR: No file selected! Operation canceled by user?");
		}

		return "File not selected!";
	}

	public static void processOrder(Order o, Widget w){
		if(TESTING){
			System.out.println("The current widget shipment " + currentWidget.getName() + " has " + currentWidget.getNumInInventory() + " widgets.");
			System.out.println("The current order " + currentOrder.getName() + " is missing " + currentOrder.getNumToShip() + " widgets.\n");
		}
		// CASE 1: Stock is too low to complete entire order
		if(widgetCount < o.getNumToShip()){	
			// order partially filled
			o.setNumToShip(o.getNumToShip() - w.getNumInInventory());
			System.out.println(w.getNumInInventory() + " widgets from Shipment " + w.getName() + " added to Order " + o.getName() + ".");
			// cost of partial filling added to order
			o.addCost(w.getNumInInventory() * w.getPrice());
			// set total number in widget inventory to zero
			widgetCount = widgetCount - w.getNumInInventory(); // This should be zero
			w.setNumInInventory(0);

			// if there are no widget shipments on stack, return order to queue
			if(widgetsOnHand.isEmpty()){
				ordersToBeFilled.add(o);
				System.out.println("Order " + o.getName() + " returned to queue with " + o.getNumToShip() + " widgets needed to complete the order.");
				processing = false;
			}

			// if the widget stack still has shipments, get new widget shipment and continue processing order
			else{
				System.out.println();
				currentWidget = widgetsOnHand.pop(); 
				processOrder(o, currentWidget);
			}
		}

		// CASE 2:  Total widget stock is high enough to complete entire order
		else{
			// CASE 2A:  Number in stock for a shipment high enough to complete an order
			if(w.getNumInInventory() >= o.getNumToShip()){
				System.out.println(o.getNumToShip() + " widgets from Shipment " + w.getName() + " added to Order " + o.getName() + ".");
				// add order cost to the order
				o.addCost(o.getNumToShip() * w.getPrice()); 
				// lower the total number of widgets in inventory
				widgetCount = widgetCount - o.getNumToShip();
				// lower the number of widget in inventory from that shipment
				w.setNumInInventory(w.getNumInInventory() - o.getNumToShip());
				// print message that order has been shipped
				System.out.println("Order " + o.getName() + " for " + o.getNumOrdered() + " widgets shipped. Total cost: $" + 
						String.format("%.2f", o.getOrderCost()));

				// if the widget stack is empty  and currentWidget has no widgets left (i.e. widgetCount = 0)
				if(widgetsOnHand.isEmpty() && w.getNumInInventory() <= 0){
					processing = false;
				}

				// if the order queue is empty, but the currentWidget still has widgets left in stock
				// return the widget shipment back to the stack
				else if(ordersToBeFilled.isEmpty() && w.getNumInInventory() > 0){
					widgetsOnHand.add(w);
					System.out.println(w.getNumInInventory() + " widgets from Shipment " + w.getName() + " returned to stock.");
					processing = false;
				}

				// if there are still order(s) in queue and widgets in stock
				// get a new order for processing
				else{
					System.out.println();
					currentOrder = ordersToBeFilled.remove();
					processOrder(currentOrder, w);
				}
			}
			// CASE 2B:  Number in stock for a shipment is too low to complete the entire order
			else{
				System.out.println(w.getNumInInventory() + " widgets from Shipment " + w.getName() + " added to Order " + o.getName() + ".");
				// add order cost to new Order
				o.addCost(w.getNumInInventory() * w.getPrice()); 
				// lower the number needed to ship from that order
				o.setNumToShip(o.getNumToShip() - w.getNumInInventory());
				// lower the widgetCount
				widgetCount = widgetCount - w.getNumInInventory();
				// get new widget shipment and continue processing order
				System.out.println();
				currentWidget = widgetsOnHand.pop(); 
				processOrder(o, currentWidget);
			}
		}
	}
}
