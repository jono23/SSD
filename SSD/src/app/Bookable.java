package app;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class Bookable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7970866783865381408L;
	HashMap<Date, Booking> calendar;	
	Accommodation accommodation;
	
	public Bookable(Accommodation accommodation) {
		calendar = new HashMap<Date, Booking>();
		this.accommodation = accommodation;
	}

	public boolean isBooked(Date date){
		if (calendar.containsKey(date))
			return true;
		return false;
	}
	
	public Accommodation getAccommodation() {
		return accommodation;
	}

	public Booking book(Date date, Customer customer){
		if (!isBooked(date)){
			Booking booking = new Booking(this, date, customer, new Date(System.currentTimeMillis()));
			calendar.put(date, booking);
			return booking;
		}
		return null;
	}
	
	public Booking getBooking(Date date){
		if (calendar.containsKey(date))
			return calendar.get(date);
		return null;
	}
	
	public boolean cancel(Date date, Customer customer){
		if (calendar.get(date).getCustomer() == customer){
			calendar.put(date, null);
			return true;
		}
		return false;
	}	
	public void removeBooking(Booking booking){
		System.out.println(calendar.get(booking.getDate()));
		System.out.println(booking);
		if(calendar.get(booking.getDate()) == booking)
			System.out.println(calendar.remove(booking.getDate()));
		
	}

	public ArrayList<Booking> getBookings(){
		ArrayList<Booking> returnArray = new ArrayList<Booking>();
		for(Booking booking : calendar.values())
			returnArray.add(booking);
		return returnArray;
	}
	
}
