import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ElevatorTest {
	private int floor, minfloor, maxfloor;

	private String elevatoridentifier;
	
	final static String Manufacturer = "Kobayashi inc.";
	
	private ArrayList<Integer> upqueue = new ArrayList<Integer>();
	private ArrayList<Integer> downqueue = new ArrayList<Integer>();
	private ArrayList<Integer> watcher = new ArrayList<Integer>();
	
	private BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
	
	private boolean stationary = true;
	private boolean goingdown =false, goingup = false;
	
	
	protected ElevatorTest(int floor, int minfloor, int maxfloor, String elevatoridentifier) {
		this.floor = floor;
		this.minfloor = minfloor;
		this.maxfloor = maxfloor;
		this.elevatoridentifier = elevatoridentifier;
	}
	
	private void up(int tofloor) {
		this.setStationary(false);
		this.goingup = true;
			int tempfloor = floor;
			for(int currentfloor = tempfloor+1; currentfloor <= tofloor; currentfloor++) {
				boolean sleep = true;
				try {
					if(sleep) {
						Thread.sleep(1500);
						sleep = false;
					}
					sleep = true;
					while(scanner.ready())
						this.inputhandler();
					floor++;
					if(upqueue.contains(currentfloor)) {
						System.out.println("PING elevator has arrived at floor "+currentfloor);
						Thread.sleep(2000);
						this.upqueue.remove(Integer.valueOf(currentfloor));
						this.watcher.remove(Integer.valueOf(currentfloor));

					}
					else
						System.out.println("PING (floor "+currentfloor+")");
				}
				catch(InterruptedException | IOException ex) {
					Thread.currentThread().interrupt();
				}
			}
			this.goingup = false;
			this.upordowncheck();
	}
		
	


	private void down(int tofloor) {
		this.setStationary(false);
		this.goingdown = true;
			int tempfloor = floor;
		for(int currentfloor = tempfloor-1; currentfloor >= tofloor; currentfloor--) {
			boolean sleep = true;
			try {
				if(sleep) {
					Thread.sleep(1500);
					sleep = false;
				}
				sleep = true;
				while(scanner.ready())
					this.inputhandler();
				floor--;
				if(downqueue.contains(currentfloor)) {
					System.out.println("PING elevator has arrived at floor "+currentfloor);
					Thread.sleep(2000);
					this.downqueue.remove(Integer.valueOf(currentfloor));
					this.watcher.remove(Integer.valueOf(currentfloor));
				}
				else
					System.out.println("PING (floor "+currentfloor+")");
			}
			catch(InterruptedException | IOException ex) {
				Thread.currentThread().interrupt();
			}
		}
		this.goingdown = false;
		this.upordowncheck();
	}
	
	
	private void starter(int newfloor) {
		if(newfloor > floor && !goingdown)
			this.up(newfloor);
		else if(newfloor < floor && !goingup)
			this.down(newfloor);
	}

	
	protected void inputhandler() throws IOException {
		String strtofloor = scanner.readLine();
		if(strtofloor.equals("info"))
			System.out.println(this.getFullElevatorinfo());
		int tofloor;
		try {
			tofloor = Integer.parseInt(strtofloor);
			if(tofloor > maxfloor || tofloor < minfloor)
				System.out.println("That floor does not exist");
			else if(tofloor == floor) {
				if(!upqueue.contains(tofloor) && this.goingdown) {
					upqueue.add(tofloor);
					watcher.add(tofloor);
				}
				else if(!downqueue.contains(tofloor) && this.goingup) {
					downqueue.add(tofloor);
					watcher.add(tofloor);
				}
				else	
					System.out.println("Elevator is already on floor "+floor);
			}
			else {
				if(tofloor > floor && !upqueue.contains(tofloor)) {
					upqueue.add(tofloor);
					watcher.add(tofloor);
				}
				else if(tofloor < floor &&!downqueue.contains(tofloor)) {
					downqueue.add(tofloor);
					watcher.add(tofloor);
				}
				if(this.isStationary()) {
					this.setStationary(false);
					this.starter(tofloor);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Please enter only full numbers or info");
		}
	}
	
	
	private void upordowncheck() {
		if(watcher.size() != 0) {
		if(this.watcher.get(0) > floor) {
			this.upqueuechecker();
		}
		else if(this.watcher.get(0) < floor) {
			this.downqueuechecker();
		}
	
		}
	}
	
	private void upqueuechecker() {
		if(upqueue.size() != 0 && Collections.max(upqueue) > floor) 
			this.up(Collections.max(upqueue));
	}
	
	private void downqueuechecker() {
		if(downqueue.size() != 0 && Collections.min(downqueue) < floor)
			this.down(Collections.min(downqueue));
	}
	
	protected String getFullElevatorinfo() {
		return "--------------------------------\nCurrent floor: "+floor+"\nLowest floor: "+minfloor+
				"\nHighest floor: "+maxfloor+"\nElevator Identifier: "+elevatoridentifier+
				"\nManufacturer: "+Manufacturer+"\n--------------------------------";
	}
	
	

	protected int getFloor() {
		return floor;
	}


	protected void setFloor(int floor) {
		this.floor = floor;
	}


	protected String getElevatorName() {
		return elevatoridentifier;
	}


	protected void setElevatorName(String elevatorName) {
		elevatoridentifier = elevatorName;
	}
	
	protected int getMinfloor() {
		return minfloor;
	}


	protected void setMinfloor(int minfloor) {
		this.minfloor = minfloor;
	}


	protected int getMaxfloor() {
		return maxfloor;
	}


	protected void setMaxfloor(int maxfloor) {
		this.maxfloor = maxfloor;
	}
	
	public String getManufacturer() {
		return Manufacturer;
	}
	
	protected boolean isStationary() {
		return stationary;
	}


	protected void setStationary(boolean stationary) {
		this.stationary = stationary;
	}
	
}
