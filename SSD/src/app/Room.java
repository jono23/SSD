package app;




public class Room extends Bookable {
	
	/**
	 * 
	 */
	public static final int SINGLE = 0;
	public static final int DOUBLE = 1;
	public static final int FAMILY = 2;
	
	
	private static final long serialVersionUID = 342949304131015398L;
	int number;
	int type;

	
	public Room(Accommodation accommodation) {
		super(accommodation);
	}


	public Room(Accommodation accommodation, int number, int type) {
		super(accommodation);
		this.number = number;
		this.type = type;

	}


	@Override
	public String toString() {
		String roomType ="";
		if(type == 0)
			roomType = "Single";
		if(type == 1)
			roomType = "Double";
		if(type == 2)
			roomType = "Family";
		return "Room number=" + number + "," + roomType;
	}
	
	public int getRoomNo(){
		return number;
	}
	

	
}
