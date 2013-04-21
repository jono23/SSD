package app;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


import org.jdesktop.swingx.JXDatePicker;
import org.joda.time.LocalDate;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class CreateBookingGui extends JFrame implements InsertCustomer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1204502079576831053L;
	TA context;
	Customer customer;
	ArrayList<Accommodation> availableAccommodations;
	private JComboBox<Room> cboAvailableRooms;
	private JComboBox<Accommodation> cboAccommodations;
	private JXDatePicker datePicker;
	private JLabel lblMessageArea;
	private Refreshable refreshable;
	private JLabel lblCustomer;
//	private Room lastBooked;

	/**
	 * @wbp.parser.constructor
	 */
	public CreateBookingGui(TA context, Accommodation accommodation, LocalDate date,
			int room, ArrayList<Accommodation> accommodations,
			Refreshable refreshable) {
		this(context, null, accommodations, refreshable);
//		lastBooked = accommodation.getRooms().get(room);
		cboAccommodations.setSelectedItem(accommodation);
		System.out.println(accommodation);
		datePicker.setDate(date.toDate());
		getRooms();
		cboAvailableRooms.setSelectedItem(accommodation.getRooms().get(room));
		
	}
	
	public CreateBookingGui(TA context, Customer customer,
			ArrayList<Accommodation> availableAccommodations, Refreshable refreshable) {
		/**
		 * 
		 */
		this.context = context;
		this.customer = customer;
		this.availableAccommodations = availableAccommodations;
		this.refreshable = refreshable;

		cboAvailableRooms = new JComboBox<Room>();
		cboAvailableRooms.setBounds(137, 99, 175, 20);
		getContentPane().add(cboAvailableRooms);

		getContentPane().setLayout(null);

		datePicker = new JXDatePicker(new Date(System.currentTimeMillis()));
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateBookingGui.this.getRooms();
			}
		});
		datePicker.setBounds(81, 46, 109, 20);
		getContentPane().add(datePicker);

		cboAccommodations = new JComboBox<Accommodation>();
		cboAccommodations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateBookingGui.this.getRooms();
			}
		});
		cboAccommodations.setBounds(238, 46, 157, 20);
		getContentPane().add(cboAccommodations);

		//fill the combobox with accommdations
		
		for(Accommodation accommodation : availableAccommodations)
			cboAccommodations.addItem(accommodation);
		
		
		JLabel lblNewBookingFor = new JLabel("New booking for customer :");
		lblNewBookingFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewBookingFor.setBounds(10, 21, 190, 14);
		getContentPane().add(lblNewBookingFor);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHotel.setBounds(164, 49, 70, 14);
		getContentPane().add(lblHotel);

		JLabel lblAvailableRooms = new JLabel("Available rooms:");
		lblAvailableRooms.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAvailableRooms.setBounds(0, 102, 127, 14);
		getContentPane().add(lblAvailableRooms);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(10, 49, 61, 14);
		getContentPane().add(lblDate);



		lblMessageArea = new JLabel("");
		lblMessageArea.setForeground(Color.RED);
		lblMessageArea.setBounds(118, 72, 216, 14);
		getContentPane().add(lblMessageArea);

		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CreateBookingGui.this.customer != null){
				if(CreateBookingGui.this.context.bookRoom(
						(Room)CreateBookingGui.this.cboAvailableRooms
								.getSelectedItem(),
						CreateBookingGui.this.customer,
						CreateBookingGui.this.datePicker.getDate())){
					CreateBookingGui.this.lblMessageArea.setText("Room booked");
//					lastBooked = (Room)CreateBookingGui.this.cboAvailableRooms.getSelectedItem();
				CreateBookingGui.this.context.refresh(CreateBookingGui.this.refreshable);
				}else
					CreateBookingGui.this.lblMessageArea.setText("Error: No room booked");
					//refresh list of rooms
					CreateBookingGui.this.getRooms();
				}
			}
		});
		btnBook.setBounds(322, 97, 78, 23);
		getContentPane().add(btnBook);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateBookingGui.this.dispose();
			}
		});
		btnClose.setBounds(322, 143, 78, 23);
		getContentPane().add(btnClose);

		lblCustomer = new JLabel();
		lblCustomer.setText("(Please search for customer)");
		if(customer != null)
			lblCustomer.setText(customer.getFirstname() + " " + customer.getSurname());
		lblCustomer.setBounds(210, 21, 185, 14);
		getContentPane().add(lblCustomer);
		
		JButton btnCustomerSearch = new JButton("Customer Search");
		btnCustomerSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateBookingGui.this.context.startSearchForCustomer(CreateBookingGui.this);
			}
		});
		btnCustomerSearch.setBounds(149, 143, 163, 23);
		getContentPane().add(btnCustomerSearch);

		this.setSize(430, 213);
		this.setLocationRelativeTo(ProgramChoiceGui.getFrames()[1]);
		//this.setLocationByPlatform(true);
		this.setVisible(true);
	}


	private void getRooms() {
		cboAvailableRooms.removeAllItems();
		for (Room room : context.searchAccommodation(
				(Accommodation) cboAccommodations.getSelectedItem(), datePicker.getDate()))
			cboAvailableRooms.addItem(room);
//		cboAccommodations.setSelectedItem(lastBooked);
	}

	@Override
	public void setCustomerDetails(Customer customer) {
		this.customer = customer;	
		lblCustomer.setText(customer.getFirstname() + " " + customer.getSurname());
	}
	
}
