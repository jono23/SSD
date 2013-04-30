//TODO possibility for abstract class that contains table, inherited by accommodationInfo & customer bookings info.
package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class AccommodationInfoGui extends JFrame implements Refreshable{
/**
	 * 
	 */
	private static final long serialVersionUID = -1347480171386358513L;
ArrayList<Accommodation> accommodations;
TA context;
private JTable tblBookings;
private JScrollPane srlBookingsPane;
private JComboBox<Accommodation> cboAccommodations;
private JXDatePicker datePicker;
private Date startDate;
private TextArea txtFeatures;
private Object[][] bookings;
private LocalDate ld;
private TextArea txtReviews;
private boolean viewFacilities;
private JLabel lblView;

public AccommodationInfoGui(ArrayList<Accommodation> accommodations, TA context)
		throws HeadlessException {
	super();
	addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			AccommodationInfoGui.this.context.startCreateProgramChoice();
			AccommodationInfoGui.this.dispose();
		}
	});	
	
	this.accommodations = accommodations;
	this.context = context;
	getContentPane().setLayout(null);
	
	startDate = new Date(System.currentTimeMillis());
	
	datePicker = new JXDatePicker(startDate);
	datePicker.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			startDate = datePicker.getDate();
			AccommodationInfoGui.this.updateTblBookings(startDate);
		}
	});
	datePicker.setBounds(334, 36, 137, 20);
	getContentPane().add(datePicker);	
	
	
	
	cboAccommodations = new JComboBox<Accommodation>();
	cboAccommodations.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			refresh();
		}
	});
	cboAccommodations.setBounds(148, 36, 163, 20);
	getContentPane().add(cboAccommodations);
	
	srlBookingsPane = new JScrollPane();
	
	srlBookingsPane.setBounds(21, 67, 590, 146);
	getContentPane().add(srlBookingsPane);
	
	tblBookings = new JTable();
	
	
	srlBookingsPane.setViewportView(tblBookings);
	
	JLabel lblHeadingAvailability = new JLabel("Bookings");
	lblHeadingAvailability.setFont(new Font("Tahoma", Font.PLAIN, 23));
	lblHeadingAvailability.setBounds(10, 5, 115, 51);
	getContentPane().add(lblHeadingAvailability);
	
	txtFeatures = new TextArea();
	txtFeatures.setBounds(21, 266, 248, 81);
	getContentPane().add(txtFeatures);
	
	JLabel lblHeadingFeatures = new JLabel("Accommodation features");
	lblHeadingFeatures.setFont(new Font("Tahoma", Font.PLAIN, 19));
	lblHeadingFeatures.setBounds(21, 219, 216, 41);
	getContentPane().add(lblHeadingFeatures);
	
	JButton btnClose = new JButton("Close");
	btnClose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			AccommodationInfoGui.this.context.startCreateProgramChoice();
			AccommodationInfoGui.this.dispose();
		}
	});
	btnClose.setBounds(529, 324, 89, 23);
	getContentPane().add(btnClose);
	
	JLabel lblWeekCommencing = new JLabel("in the week commencing");
	lblWeekCommencing.setFont(new Font("Tahoma", Font.PLAIN, 11));
	lblWeekCommencing.setBounds(336, 11, 135, 30);
	getContentPane().add(lblWeekCommencing);
	
	JLabel lblAccommodation = new JLabel("For Accommodation");
	lblAccommodation.setFont(new Font("Tahoma", Font.PLAIN, 11));
	lblAccommodation.setBounds(161, 11, 135, 30);
	getContentPane().add(lblAccommodation);
	
	JButton btnPlusDate = new JButton("<=");
	btnPlusDate.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dateShift(1);
		}
	});
	btnPlusDate.setBounds(499, 35, 51, 23);
	getContentPane().add(btnPlusDate);
	
	JButton btnMinusDate = new JButton("=>");
	btnMinusDate.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dateShift(0);
		}
	});
	btnMinusDate.setBounds(560, 35, 51, 23);
	getContentPane().add(btnMinusDate);
	
	JLabel lblReviews = new JLabel("Reviews");
	lblReviews.setFont(new Font("Tahoma", Font.PLAIN, 19));
	lblReviews.setBounds(290, 219, 137, 41);
	getContentPane().add(lblReviews);
	
	txtReviews = new TextArea();
	txtReviews.setBounds(275, 266, 248, 81);
	getContentPane().add(txtReviews);
	
	lblView = new JLabel("Rooms");
	lblView.setHorizontalAlignment(SwingConstants.TRAILING);
	lblView.setForeground(Color.GRAY);
	lblView.setFont(new Font("Tahoma", Font.BOLD, 24));
	lblView.setBounds(437, 220, 174, 35);
	getContentPane().add(lblView);
	
	JRadioButton rdbtnRooms = new JRadioButton("Rooms");
	rdbtnRooms.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			viewFacilities = false;
			refresh();
		}
	});
	rdbtnRooms.setSelected(true);
	rdbtnRooms.setBounds(543, 255, 109, 23);
	getContentPane().add(rdbtnRooms);
	
	JRadioButton rdbtnFacilities = new JRadioButton("Facilities");
	rdbtnFacilities.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			viewFacilities = true;
			refresh();
		}
	});
	rdbtnFacilities.setBounds(543, 281, 109, 23);
	getContentPane().add(rdbtnFacilities);
	
	ButtonGroup group = new ButtonGroup();
	group.add(rdbtnRooms);
	group.add(rdbtnFacilities);
	
	for(Accommodation accommodation : accommodations)
		cboAccommodations.addItem(accommodation);
	
	this.setSize(644, 408);
	

	this.setLocationRelativeTo(null);

	this.setVisible(true);
}



//to be used to update the contents of the table when selecting hotels or changing starting date.
	private void updateTblBookings(Date startDate){
				DateTime dt = new DateTime(startDate);
				ld = dt.toLocalDate();
				// set columns to the dates referenced
				String column0 = "Room no";
				if(viewFacilities)
					column0 = "Facility";
				String[] columnNames = {column0, ld.toString(), ld.plusDays(1).toString(), ld.plusDays(2).toString(), ld.plusDays(3).toString(), ld.plusDays(4).toString(), ld.plusDays(5).toString(), ld.plusDays(6).toString()};
				
				//erase current table
				AccommodationInfoGui.this.tblBookings = null;
				AccommodationInfoGui.this.srlBookingsPane.setViewportView(null);
				//create a new object array for populating the table and for referencing bookings
				if(viewFacilities)
					bookings = AccommodationInfoGui.this.context.getBookings((Accommodation) cboAccommodations.getSelectedItem(), startDate, true);
				else
					bookings = AccommodationInfoGui.this.context.getBookings((Accommodation) cboAccommodations.getSelectedItem(), startDate);
				//create and add a new table
				AccommodationInfoGui.this.tblBookings = new JTable(bookings, columnNames);
				AccommodationInfoGui.this.srlBookingsPane.setViewportView(AccommodationInfoGui.this.tblBookings);		
				AccommodationInfoGui.this.tblBookings.setEnabled(false);
	
	tblBookings.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = tblBookings.rowAtPoint(arg0.getPoint());
			int col = tblBookings.columnAtPoint(arg0.getPoint());
			//call booking information gui from context with booking from object array
			System.out.println(bookings[row][col]);
			Booking booking = (Booking)bookings[row][col];
			if (booking != null)
				context.editBooking(AccommodationInfoGui.this, booking);
			else{
				if(viewFacilities)
					context.startCreateBooking((Accommodation) cboAccommodations.getSelectedItem(), AccommodationInfoGui.this.ld.plusDays(col - 1), row, AccommodationInfoGui.this, true);
				else
					context.startCreateBooking((Accommodation) cboAccommodations.getSelectedItem(), AccommodationInfoGui.this.ld.plusDays(col - 1), row, AccommodationInfoGui.this, false);
			}
		}
	});
	}
	
	private void dateShift(int direction){
		DateTime dt = new DateTime(startDate);
		ld = dt.toLocalDate();		
		if (direction == 0)
			datePicker.setDate(ld.plusDays(1).toDate());
		else
			datePicker.setDate(ld.minusDays(1).toDate());
		startDate = datePicker.getDate();
		refresh();
	}
	
	

	@Override
	public void refresh() {
		// to allow refresh from outside class
		
		AccommodationInfoGui.this.txtFeatures.setText("");
		AccommodationInfoGui.this.updateTblBookings(startDate);
		for(Feature feature : ((Accommodation)cboAccommodations.getSelectedItem()).getFeatures()){
			AccommodationInfoGui.this.txtFeatures.setText(AccommodationInfoGui.this.txtFeatures.getText() + feature + " \n");
		}
				
		updateTblBookings(startDate);
		//add review to text area
		txtReviews.setText("");
		for (Booking booking : ((Accommodation)cboAccommodations.getSelectedItem()).getBookings())
			if(booking.getReview() != null)
				txtReviews.setText(txtReviews.getText()  + booking.getRoom() +": "+ booking.getReview() + " - by: " + booking.getCustomer().getSurname() + " on " +  LocalDate.fromDateFields(booking.getDate()).toString("dd/MM/yyyy") + "\n");
		
		if(viewFacilities)
			lblView.setText("Facilities");
		else
			lblView.setText("Rooms");
	}
}
