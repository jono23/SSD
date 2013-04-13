package app;
import java.util.ArrayList;
import java.util.Date;

public class TA implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3732067769893006562L;
	private ArrayList<Customer> customers;
	private ArrayList<Accommodation> accommodations;

	public TA() {
		customers = new ArrayList<Customer>();
		accommodations = new ArrayList<Accommodation>();
	}

	public void startCreateProgramChoice() {
		new ProgramChoiceGui(this);
	}

	public void startCreateNewCustomer() {
		new CreateCustomerGui(this);
	}

	boolean[] newCustomer(String firstname, String surname, String address,
			String phoneNo, String secretAnswer) {
		boolean[] errors = new boolean[5];
		// start of verify code
		if (firstname.equals(""))
			errors[0] = true;
		if (surname.equals(""))
			errors[1] = true;
		if (address.equals(""))
			errors[2] = true;
		if (phoneNo.equals(""))
			errors[3] = true;
		if (secretAnswer.equals(""))
			errors[4] = true;

		// check if any errors
		boolean isErrors = false;
		for (int i = 0; i < 5; i++)
			if (errors[i] == true)
				isErrors = true;

		if (!isErrors)
			// passes verification so create new customer and add to list
			customers.add(new Customer(firstname, surname, address, phoneNo,
					secretAnswer));
		return errors;
	}

	public void startSearchForCustomer() {
		new SearchForCustomerGui(this);
	}
	
	public void startAddAccommodation(){
		new AddAccommodationGui(this);
	}
	
	boolean[] addAccommodation(String name, String rating, String noOfRooms, ArrayList<String> features){
			
		boolean[] errors = new boolean[3];
		// start of verify code
		if (name.equals(""))
			errors[0] = true;
		if (rating.equals(""))
			errors[1] = true;
		if (noOfRooms.equals(""))
			errors[2] = true;
	
		// check if any errors
		boolean isErrors = false;
		for (int i = 0; i < 3; i++)
			if (errors[i] == true)
				isErrors = true;
	
		if (!isErrors)
			// passes verification so create new customer and add to list
			accommodations.add(new Accommodation(name, Integer.parseInt(rating), Integer.parseInt(noOfRooms), features));
		//log
		System.out.println(accommodations);
	return errors;		
	}

	public ArrayList<Customer> customerSearch(String searchString,
			int searchType) {
		return Customer.customerSearch(customers, searchString, searchType);
	}

	public void startCreateBooking(Customer customer){
		new CreateBookingGui(this, customer, accommodations);
	}
	
	public ArrayList<Room> searchAccommodation(Accommodation accommodation, Date date){
		return accommodation.getFreeRooms(date);
	}
	
	public boolean bookRoom(Room room, Customer customer, Date date){
		Booking booking = null;
		if(room != null)
			booking = room.book(date, customer);
		if(booking != null){
			customer.addBooking(booking);
			return true;
		}
		return false;
		
	}
	
	public boolean verifyCustomer(Customer customer, String msg){
		return (customer.getSecretAnswer().equalsIgnoreCase(msg));
	}
	
	public void quit() {
		// persist all data
		SSD_app.saveState();
		// end the program
		System.exit(0);
	}

}
