package app;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Accommodation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7261161063880756652L;
	String name;
	int rating;
//	int no_of_rooms;
	ArrayList<Room> rooms;
	ArrayList<Feature> features;
	
	public Accommodation(String name, int rating, int singleRooms, int doubleRooms, int familyRooms,
			ArrayList<String> featureString) {
		super();
		rooms = new ArrayList<Room>();
		features = new ArrayList<Feature>();
		this.name = name;
		this.rating = rating;
		int roomNumber = 0;
		for (int i = 0; i < singleRooms; i++){
			roomNumber ++;
			rooms.add(new Room(this, roomNumber, Room.SINGLE));			
		}
		for (int i = 0; i < doubleRooms; i++){
			roomNumber ++;
			rooms.add(new Room(this, roomNumber, Room.DOUBLE));
		}
		for (int i = 0; i < familyRooms; i++){
			roomNumber ++;
			rooms.add(new Room(this, roomNumber, Room.FAMILY));
		}
		for (String featureName : featureString){
			features.add(new Feature(this, featureName));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

//	//public int getNo_of_rooms() {
//		return no_of_rooms;
//	}

//	public void setNo_of_rooms(int no_of_rooms) {
//		this.no_of_rooms = no_of_rooms;
//	}

	public ArrayList<Feature> getFeatures() {
		return features;
	}
	
	public ArrayList<Bookable> getFreeRooms(Date date){
		ArrayList<Bookable> returnList = new ArrayList<Bookable>();
		for (Room room : rooms)
			if(!room.isBooked(date))
				returnList.add(room);
		return returnList;
	}
	
	public ArrayList<Bookable> getFreeFacilities(Date date){
		ArrayList<Bookable> returnList = new ArrayList<Bookable>();
		for (Feature feature : features)
			if(!feature.isBooked(date))
				returnList.add(feature);
		return returnList;
	}
	
	public ArrayList<Room> getRooms(){
		return rooms;
	}
	
	public ArrayList<Booking> getBookings(){
		ArrayList<Booking> returnArray = new ArrayList<Booking>();
		for (Room room : rooms)
			returnArray.addAll(room.getBookings());
		for (Feature feature : features)
			returnArray.addAll(feature.getBookings());
		return returnArray;
	}
	
	@Override
	public String toString() {
		return name + ", " + rating + " Stars";
	}

	
}
