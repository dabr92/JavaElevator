import java.io.IOException;

public class ElevatorMainTest {

	public static void main(String[] args) {
		
		
		ElevatorMainTest em = new ElevatorMainTest();
		em.controls(5, -5, 10, "Elevate8943");
		
		
	}

	
	private void controls(int currentfloor, int minfloor, int maxfloor, String elevatoridentifier) {
		ElevatorTest e = new ElevatorTest(currentfloor, minfloor, maxfloor, elevatoridentifier);
		boolean running = true;
		System.out.println("Welcome to the elevator, you are currently on floor "+e.getFloor());
		while(running) {
			e.setStationary(true);
			System.out.print("Enter floor >> ");
			try {
				e.inputhandler();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		System.out.println("Shutting down");
	}
}
