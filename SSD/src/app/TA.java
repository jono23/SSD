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
	
	public void deleteCustomer(Customer customer){
		customers.remove(customer);
		// persist all data
		SSD_app.saveState();
	}
	
	boolean[] checkCustomer(String firstname, String surname, String address,
			String phoneNo, String secretAnswer){
		boolean[] errors = new boolean[6];
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
		for (int i = 0; i < 5; i++)
			if (errors[i] == true)
				errors[5] = true;		
			
		return errors;
	}

	boolean[] newCustomer(String firstname, String surname, String address,
			String phoneNo, String secretAnswer) {
		
		boolean errors[] = checkCustomer(firstname, surname, address,
				phoneNo, secretAnswer);

		if (!errors[5]){
			// passes verification so create new customer and add to list
			customers.add(new Customer(firstname, surname, address, phoneNo,
					secretAnswer));
			// persist all data
			SSD_app.saveState();
		}
		return errors;
	}

	
	boolean[] amendCustomer(Customer customer, String firstname, String surname, String address,
			String phoneNo, String secretAnswer) {
		
		boolean errors[] = checkCustomer(firstname, surname, address,
				phoneNo, secretAnswer);

		if (!errors[5])
		{
			// passes verification so amend the customer 
			customer.setFirstname(firstname);
			customer.setSurname(surname);
			customer.setAddress(address);
			customer.setPhoneNo(phoneNo);
			customer.setSecretAnswer(secretAnswer);
			// persist all data
			SSD_app.saveState();
		}
		return errors;
	}	
	
	public void startSearchForCustomer() {
		new SearchForCustomerGui(this);
	}
	
	public void startAddAccommodation(){
		new AddAccommodationGui(this);
	}
	
	boolean[] addAccommodation(String name, String rating, String singleRooms, String doubleRooms, String familyRooms, ArrayList<String> features){
			
		//first make empty strings into 0's
		if (singleRooms.equals(""))
			singleRooms = "0";
		if (doubleRooms.equals(""))
			doubleRooms = "0";
		if (familyRooms.equals(""))
			familyRooms = "0";		
		
		boolean[] errors = new boolean[5];
		// start of verify code
		if (name.equals(""))
			errors[0] = true;
		if (rating.equals(""))
			errors[1] = true;
		if (!singleRooms.equals("") && !singleRooms.matches("^[0-9]+$"))
			errors[2] = true;
		if (!doubleRooms.equals("") && !doubleRooms.matches("^[0-9]+$"))
			errors[3] = true;
		if (!familyRooms.equals("") && !familyRooms.matches("^[0-9]+$"))
			errors[4] = true;

	
		// check if any errors
		boolean isErrors = false;
		for (int i = 0; i < 5; i++)
			if (errors[i] == true)
				isErrors = true;
	
		if (!isErrors)
			// passes verification so create new accommodation and add to list
			accommodations.add(new Accommodation(name, Integer.parseInt(rating), Integer.parseInt(singleRooms), Integer.parseInt(doubleRooms), Integer.parseInt(familyRooms), features));
		//log
		System.out.println(accommodations);
		// persist all data
		SSD_app.saveState();
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
			// persist all data
			SSD_app.saveState();
			return true;
		}
		return false;
		
	}
	
	public boolean verifyCustomer(Customer customer, String msg){
		return (customer.getSecretAnswer().equalsIgnoreCase(msg));
	}
	
	public void alterCustomerDetails(Customer customer){
		new CreateCustomerGui(this, customer);
	}
	
	public void quit() {
		// persist all data
		SSD_app.saveState();
		// end the program
		System.exit(0);
	}

}
