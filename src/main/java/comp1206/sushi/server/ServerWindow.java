package comp1206.sushi.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import javax.swing.*;

import comp1206.sushi.common.*;
import comp1206.sushi.mock.MockServer;
import comp1206.sushi.server.ServerInterface.UnableToDeleteException;

/**
 * Provides the Sushi Server user interface
 *
 */
public class ServerWindow extends JFrame implements UpdateListener {

	private static final long serialVersionUID = -4661566573959270000L;
	private ServerInterface server;
	
	/**
	 * Create a new server window
	 * @param server instance of server to interact with
	 */
	public ServerWindow(ServerInterface server) {
		super("Sushi Server");
		this.server = server;
		this.setTitle(server.getRestaurantName() + " Server");
		server.addUpdateListener(this);
		
		// create mock server
		MockServer mockServer = (MockServer) server;
		
		//Create tabbedpane for all functions
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		Postcodes postcodes = new Postcodes(server);
		postcodes.setOpaque(true);
		Drones drones = new Drones();
		drones.setOpaque(true);
		Staffs staff = new Staffs();
		staff.setOpaque(true);
		Suppliers suppliers = new Suppliers(mockServer.suppliers);
		Ingredients ingredients = new Ingredients(mockServer.ingredients);
		JPanel dishes = new JPanel();
		JPanel orders = new JPanel();
		JPanel users = new JPanel();
		tabbedPane.add("Postcodes", postcodes);
		tabbedPane.add("Drones", drones);
		tabbedPane.add("Staff", staff);
		tabbedPane.add("Suppliers", suppliers);
		tabbedPane.add("Ingredients", ingredients);
		tabbedPane.add("Dishes",dishes);
		tabbedPane.add("Orders", orders);
		tabbedPane.add("Dishes", users);
		this.add(tabbedPane);
		
		//Display window
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//Start timed updates
		startTimer();
	}
	
	/**
	 * Start the timer which updates the user interface based on the given interval to update all panels
	 */
	public void startTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);     
        int timeInterval = 5;
        
        scheduler.scheduleAtFixedRate(() -> refreshAll(), 0, timeInterval, TimeUnit.SECONDS);
	}
	
	/**
	 * Refresh all parts of the server application based on receiving new data, calling the server afresh
	 */
	public void refreshAll() {
		
	}
	
	@Override
	/**
	 * Respond to the model being updated by refreshing all data displays
	 */
	public void updated(UpdateEvent updateEvent) {
		refreshAll();
	}
	
}
