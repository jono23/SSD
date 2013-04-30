package app;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

public class CustomerInfoGui extends JFrame implements Refreshable, InsertCustomer {
	private JLabel lblCustomer;
	private TA context;
	private JTable tblBookings;
	private Customer customer;
	private JScrollPane srlBookingsPane;
	private Object[][] bookings;
	
	public CustomerInfoGui(TA context) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CustomerInfoGui.this.context.startCreateProgramChoice();
				CustomerInfoGui.this.dispose();
			}
		});		
		this.context = context;
		getContentPane().setLayout(null);
		
		JLabel lblCustomerHeading = new JLabel("Customer:");
		lblCustomerHeading.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCustomerHeading.setBounds(10, 11, 82, 14);
		getContentPane().add(lblCustomerHeading);
		
		lblCustomer = new JLabel("(Please search for customer)");
		lblCustomer.setBounds(119, 11, 262, 14);
		getContentPane().add(lblCustomer);
		
		JButton btnCustomerSearch = new JButton("Customer Search");
		btnCustomerSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerInfoGui.this.context.startSearchForCustomer(CustomerInfoGui.this);
			}
		});
		btnCustomerSearch.setBounds(91, 228, 163, 23);
		getContentPane().add(btnCustomerSearch);
		
		JButton btnAlterCustomerDetails = new JButton("Alter customer details");
		btnAlterCustomerDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CustomerInfoGui.this.customer != null)
					CustomerInfoGui.this.context.alterCustomerDetails(CustomerInfoGui.this.customer, CustomerInfoGui.this);
				else
					JOptionPane.showMessageDialog(CustomerInfoGui.this, "Please select a customer");
			}
		});
		btnAlterCustomerDetails.setBounds(20, 36, 175, 23);
		getContentPane().add(btnAlterCustomerDetails);
		
		JButton btnDeactivateCustomer = new JButton("De-activate customer");
		btnDeactivateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(customer == null) 
					JOptionPane.showMessageDialog(CustomerInfoGui.this, "Please select a customer");
				else if(JOptionPane.showConfirmDialog(
				    CustomerInfoGui.this,
				    "Are you sure you want to deactivate this customer?",
				    "Confirm",
				    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						CustomerInfoGui.this.context.deactivateCustomer(customer);
						refresh();
					}
					
			}
		});
		btnDeactivateCustomer.setBounds(206, 68, 175, 23);
		getContentPane().add(btnDeactivateCustomer);
		
		JLabel lblBookings = new JLabel("Bookings");
		lblBookings.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblBookings.setBounds(10, 54, 110, 44);
		getContentPane().add(lblBookings);
		
		JButton btnNewBooking = new JButton("New booking");
		btnNewBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CustomerInfoGui.this.customer != null)
					CustomerInfoGui.this.context.startCreateBooking(customer, CustomerInfoGui.this);
				else
					JOptionPane.showMessageDialog(CustomerInfoGui.this, "Please select a customer");
			}});
		btnNewBooking.setBounds(206, 36, 175, 23);
		getContentPane().add(btnNewBooking);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerInfoGui.this.dispose();
				CustomerInfoGui.this.context.startCreateProgramChoice();
			}
		});
		btnClose.setBounds(262, 228, 119, 23);
		getContentPane().add(btnClose);
		
		srlBookingsPane = new JScrollPane();
		srlBookingsPane.setBounds(10, 102, 371, 116);
		getContentPane().add(srlBookingsPane);
		
		tblBookings = new JTable();
		srlBookingsPane.setViewportView(tblBookings);
		
		this.setSize(407, 300);

		this.setLocationRelativeTo(null);		
		this.setVisible(true);
		
		//see if a customer is selected already
		customer = context.getCurrentCustomer();
		refresh();
	}
	

	private void updateTblBookings(){

		//create a new object array for populating the table and for referencing bookings
		bookings = CustomerInfoGui.this.context.getBookings(customer);
		
		if (bookings != null){
			// set columns date and booking
			String[] columnNames = {"Date", "Booking"};
			
			//erase current table
			CustomerInfoGui.this.tblBookings = null;
			CustomerInfoGui.this.srlBookingsPane.setViewportView(null);
			//create and add a new table
			CustomerInfoGui.this.tblBookings = new JTable(bookings, columnNames);
			CustomerInfoGui.this.srlBookingsPane.setViewportView(CustomerInfoGui.this.tblBookings);		
			CustomerInfoGui.this.tblBookings.setEnabled(false);
			TableColumnModel tcm = CustomerInfoGui.this.tblBookings.getColumnModel();
			tcm.getColumn(0).setPreferredWidth(70);     //Name
			tcm.getColumn(1).setPreferredWidth(300); 
	
			tblBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblBookings.rowAtPoint(arg0.getPoint());
				int col = tblBookings.columnAtPoint(arg0.getPoint());
				//call booking information gui from context with booking from object array
				System.out.println(bookings[row][col]);
				Booking booking = (Booking)bookings[row][col];
				if (booking != null)
					context.editBooking(CustomerInfoGui.this, booking);
			}
			});
		} // create a blank table
		else{
			CustomerInfoGui.this.tblBookings = null;
			CustomerInfoGui.this.tblBookings = new JTable();
			CustomerInfoGui.this.srlBookingsPane.setViewportView(tblBookings);
		}
}


	@Override
	public void setCustomerDetails(Customer customer) {
		this.customer = customer;
		refresh();
	}


	@Override
	public void refresh() {
		//see if a customer is selected already
		customer = context.getCurrentCustomer();		
		updateTblBookings();
		if(customer != null)
			lblCustomer.setText(customer.getFirstname() + " " + customer.getSurname());
		else
			lblCustomer.setText("(Please search for customer)");

		
	}
	
	
	
}
