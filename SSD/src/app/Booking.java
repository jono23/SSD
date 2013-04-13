package app;
import java.io.Serializable;
import java.util.Date;


public class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1186456523738959662L;
	Customer customer;
	Date dateOfBooking;
	Bookable room;
	
	public Booking(Bookable room, Customer customer, Date date) {
		super();
		this.customer = customer;
		this.dateOfBooking = date;
		this.room = room;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(Date dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	@Override
	public String toString() {
		return "Booking [customer=" + customer + ", dateOfBooking="
				+ dateOfBooking + ", room=" + room + ", hotel=" + room.getAccommodation() + "]";
	}
	

}

	
