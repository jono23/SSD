package app;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

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
	
	public void startCustomerInfoGui() {
		new CustomerInfoGui(this);
	}
	
	public void deleteCustomer(Customer customer){
		customers.remove(customer);
		// persist all data
		SSD_app.saveState();
	}
	
	public void deactivateCustomer(Customer customer){
		ArrayList<Booking> eraseList = customer.deactivate();
		for(Booking booking : eraseList){
			removeBooking(booking);
		}
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
		if (!phoneNo.equals("") && !phoneNo.matches("^[0-9]+$"))  
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
	
	public void startSearchForCustomer(InsertCustomer insertCustomer) {
		new SearchForCustomerGui(this, insertCustomer);
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
		if (rating.equals("") || !rating.matches("^[0-9]+$"))
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
//		System.out.println(accommodations);
		// persist all data
		SSD_app.saveState();
	return errors;		
	}

	public ArrayList<Customer> customerSearch(String searchString,
			int searchType) {
		return Customer.customerSearch(customers, searchString, searchType);
	}

	public void startCreateBooking(Customer customer, Refreshable refreshable){
		new CreateBookingGui(this, customer, accommodations, refreshable);
	}
	
	public void startCreateBooking(Accommodation accommodation,
			LocalDate date, int room,
			Refreshable refreshable, boolean facilities) {
		new CreateBookingGui(this, accommodation, date, room, accommodations, refreshable, facilities);
		
	}
	
	public ArrayList<Bookable> searchAccommodation(Accommodation accommodation, Date date, boolean facilities){
		if(facilities)
			return accommodation.getFreeFacilities(date);
		else
			return accommodation.getFreeRooms(date);
	}
	
	public boolean bookRoom(Bookable room, Customer customer, Date date){
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
	
	public void alterCustomerDetails(Customer customer, Refreshable refreshable){
		new CreateCustomerGui(this, customer, refreshable);
	}
	
	
	public void startAccommodationInfoGui()
	{
		new AccommodationInfoGui(accommodations, this);
	}
	
	
	public Object[][] getBookings(Accommodation accommodation, Date startDate)
	{
		int len = accommodation.getRooms().size();
		Object[][] returnArray = new Object[len][8];
		DateTime dt = new DateTime(startDate);
		LocalDate ld = dt.toLocalDate();
		LocalDate sld = ld;
//		ld.toDate();
//		Calendar c = Calendar.getInstance();
//		c.setTime(startDate);
		
		for(Room room : accommodation.getRooms()){
			ld = sld;
			//insert number / desc of room to table
			returnArray[room.getRoomNo()-1][0] = room.toString().substring(12);
			for(int i = 1; i < 8; i++){
				returnArray[room.getRoomNo()-1][i] = room.getBooking(ld.toDate());
				ld = ld.plusDays(1);
//				System.out.println(ld);
			}
		}
		
		return returnArray;
	}
	
	public Object[][] getBookings(Accommodation accommodation, Date startDate, boolean facilities)
	{
		//choose to overload this method as most will be changed as from above (returning rooms)
		// this one will return facilities
		int len = accommodation.getFeatures().size();
		Object[][] returnArray = new Object[len][8];
		DateTime dt = new DateTime(startDate);
		LocalDate ld = dt.toLocalDate();
		LocalDate sld = ld;

		int j = 0;
		for(Feature feature : accommodation.getFeatures()){
			ld = sld;
			//insert number / desc of room to table
			returnArray[j][0] = feature.toString().substring(12);
			for(int i = 1; i < 8; i++){
				returnArray[j][i] = feature.getBooking(ld.toDate());
				ld = ld.plusDays(1);
//				System.out.println(ld);
			}
			j++;
		}
		
		return returnArray;
	}	
	
	
	public Object[][] getBookings(Customer customer){
		int len = customer.getBookings().size();
		Object[][] returnArray = new Object[len][2];
		int i = 0;
		for(Booking booking : customer.getBookings()){
			DateTime dt = new DateTime(booking.getDate());
			LocalDate ld = dt.toLocalDate();
			returnArray[i][0] = ld.toString("MM/dd/yyyy");
			returnArray[i][1] = booking;
			i ++;
		}
		return returnArray;
	}
	

	
	public void editBooking(Refreshable refreshable, Booking booking)
	{
		new BookingInfoGui(booking, this, refreshable);
	}
	
	public void removeBooking(Booking booking)
	{
		//if booking is in the future
		if(booking.date.compareTo(new Date()) > 0)
			booking.remove();
	}

	public void useCustomer(Customer customer, InsertCustomer insertCustomer){
		insertCustomer.setCustomerDetails(customer);
	}

	
	
	public void quit() {
		// persist all data
		SSD_app.saveState();
		// end the program
		System.exit(0);
	}

	public void refresh(Refreshable refreshable) {
		refreshable.refresh();		
	}

	public void addReview(Booking booking, String review, Refreshable refreshable){
		booking.setReview(review);
		refreshable.refresh();
		// persist all data
		SSD_app.saveState();
	}


}
