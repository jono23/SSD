package app;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class BookingInfoGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8274142348448801121L;
	Booking booking;
	TA context;
	Refreshable refreshable;
	
	public BookingInfoGui(Booking booking, TA context, Refreshable refreshable) throws HeadlessException {
		super();
		this.booking = booking;
		this.context = context;
		this.refreshable = refreshable; 
		
		getContentPane().setLayout(null);
		
		JLabel lblHeading = new JLabel("Booked By:");
		lblHeading.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblHeading.setBounds(10, 0, 214, 41);
		getContentPane().add(lblHeading);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookingInfoGui.this.dispose();
			}
		});
		btnClose.setBounds(266, 114, 89, 23);
		getContentPane().add(btnClose);
		
		JButton btnCancelBooking = new JButton("Cancel booking");
		btnCancelBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(JOptionPane.showConfirmDialog(
					    BookingInfoGui.this,
					    "Are you sure you want to cancel this booking?",
					    "Confirm",
					    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					if (!BookingInfoGui.this.context.removeBooking(BookingInfoGui.this.booking))
						JOptionPane.showMessageDialog(BookingInfoGui.this, "Historic bookings cannot be removed");
				if(BookingInfoGui.this.refreshable != null)
					//uses interface
					BookingInfoGui.this.context.refresh(BookingInfoGui.this.refreshable);
				BookingInfoGui.this.dispose();
				}}
			
		});
		btnCancelBooking.setBounds(131, 114, 125, 23);
		getContentPane().add(btnCancelBooking);
		
		
		JButton btnAddReview = new JButton("Add review");
		btnAddReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//allow reviews by a dialog
				Icon icon = UIManager.getIcon("OptionPane.questionIcon");
				BookingInfoGui.this.context.addReview(BookingInfoGui.this.booking, 
						(String)JOptionPane.showInputDialog(
			                    BookingInfoGui.this,
			                    "Please add breif review:",
			                    "Make review dialog",
			                    JOptionPane.PLAIN_MESSAGE,
			                    icon,
			                    null,
			                    ""), BookingInfoGui.this.refreshable);
			}
		});
		btnAddReview.setBounds(10, 114, 111, 23);
		getContentPane().add(btnAddReview);
		
		JLabel lblBookedBy = new JLabel("Customer: " + booking.getCustomer().getFirstname() + " " + booking.getCustomer().getSurname());
		lblBookedBy.setBounds(43, 40, 181, 14);
		getContentPane().add(lblBookedBy);

		JLabel lblBookedOn = new JLabel(booking.getRoom().toString());
		lblBookedOn.setBounds(63, 89, 181, 14);
		getContentPane().add(lblBookedOn);
		
		JLabel label = new JLabel("Booked on:" + booking.getDateOfBooking());
		label.setBounds(53, 64, 181, 14);
		getContentPane().add(label);
		
		this.setSize(381, 186);
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);		
		
	}
}
