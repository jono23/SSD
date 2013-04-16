package app;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


import org.jdesktop.swingx.JXDatePicker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class CreateBookingGui extends JFrame {
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

	public CreateBookingGui(TA context, Customer customer,
			ArrayList<Accommodation> availableAccommodations) {
		/**
		 * 
		 */
		this.context = context;
		this.customer = customer;
		this.availableAccommodations = availableAccommodations;

		cboAvailableRooms = new JComboBox<Room>();
		cboAvailableRooms.setBounds(132, 111, 175, 20);
		getContentPane().add(cboAvailableRooms);

		getContentPane().setLayout(null);

		datePicker = new JXDatePicker(new Date(System.currentTimeMillis()));
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateBookingGui.this.getRooms();
			}
		});
		datePicker.setBounds(81, 51, 109, 20);
		getContentPane().add(datePicker);

		cboAccommodations = new JComboBox<Accommodation>();
		cboAccommodations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateBookingGui.this.getRooms();
			}
		});
		cboAccommodations.setBounds(238, 51, 157, 20);
		getContentPane().add(cboAccommodations);

		//fill the combobox with accommdations
		
		for(Accommodation accommodation : availableAccommodations)
			cboAccommodations.addItem(accommodation);
		
		
		JLabel lblNewBookingFor = new JLabel("New booking for customer :");
		lblNewBookingFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewBookingFor.setBounds(24, 21, 190, 14);
		getContentPane().add(lblNewBookingFor);

		JLabel lblHotel = new JLabel("Hotel:");
		lblHotel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHotel.setBounds(164, 54, 70, 14);
		getContentPane().add(lblHotel);

		JLabel lblAvailableRooms = new JLabel("Available rooms:");
		lblAvailableRooms.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAvailableRooms.setBounds(-5, 114, 127, 14);
		getContentPane().add(lblAvailableRooms);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(10, 54, 61, 14);
		getContentPane().add(lblDate);



		lblMessageArea = new JLabel("");
		lblMessageArea.setForeground(Color.RED);
		lblMessageArea.setBounds(91, 86, 216, 14);
		getContentPane().add(lblMessageArea);

		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CreateBookingGui.this.context.bookRoom(
						(Room)CreateBookingGui.this.cboAvailableRooms
								.getSelectedItem(),
						CreateBookingGui.this.customer,
						CreateBookingGui.this.datePicker.getDate()))
					CreateBookingGui.this.lblMessageArea.setText("Room booked");
				else
					CreateBookingGui.this.lblMessageArea.setText("Error: No room booked");
				//refresh list of rooms
				CreateBookingGui.this.getRooms();
			}
		});
		btnBook.setBounds(317, 109, 78, 23);
		getContentPane().add(btnBook);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateBookingGui.this.dispose();
			}
		});
		btnClose.setBounds(317, 143, 78, 23);
		getContentPane().add(btnClose);

		JLabel lblCustomer = new JLabel("customer");
		lblCustomer.setText(customer.getFirstname() + " " + customer.getSurname());
		lblCustomer.setBounds(224, 21, 171, 14);
		getContentPane().add(lblCustomer);

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
	}
	
}
