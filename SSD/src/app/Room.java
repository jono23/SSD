package app;



public class Room extends Bookable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 342949304131015398L;
	int number;
	int noOfBeds;

	
	public Room(Accommodation accommodation) {
		super(accommodation);
	}


	public Room(Accommodation accommodation, int number, int noOfBeds) {
		super(accommodation);
		this.number = number;
		this.noOfBeds = noOfBeds;

	}


	@Override
	public String toString() {
		return "Room number=" + number + ", Beds=" + noOfBeds;
	}
	

	
}
