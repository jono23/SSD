package app;
import java.io.Serializable;
import java.util.Date;


public class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1186456523738959662L;
	Customer customer;
	Date date;
	Date dateOfBooking;
	Bookable room;
	
	public Booking(Bookable room, Date date ,Customer customer, Date dateOfBooking) {
		super();
		this.customer = customer;
		this.date = date;
		this.dateOfBooking = dateOfBooking;
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

	
	public void remove(){
	System.out.println("remove");
		room.removeBooking(this);
		customer.removeBooking(this);
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return customer.getSurname();
	}
	

}

	
