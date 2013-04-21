package app;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class SearchForCustomerGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6197654421377128016L;
	TA context;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnPhoneNo;
	private JRadioButton rdbtnSurname;
	private JTextField txtSearchTerm;
	private JComboBox<Customer> cboResults;
	private Label lblCustomerVerified;
	private Button btnSearch;
	private InsertCustomer insertCustomer;

	public SearchForCustomerGui(TA context, InsertCustomer insertCustomer) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SearchForCustomerGui.this.dispose();
			}
		});
		this.context = context;
		this.insertCustomer = insertCustomer;
		getContentPane().setLayout(null);

		txtSearchTerm = new JTextField();
		txtSearchTerm.setBounds(188, 41, 86, 20);
		getContentPane().add(txtSearchTerm);
		txtSearchTerm.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search criteria",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(45, 11, 121, 72);
		getContentPane().add(panel);
		panel.setLayout(null);

		rdbtnSurname = new JRadioButton("Surname");
		rdbtnSurname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnSurname.setSelected(true);
		rdbtnSurname.setBounds(6, 16, 109, 23);
		panel.add(rdbtnSurname);
		buttonGroup.add(rdbtnSurname);

		rdbtnPhoneNo = new JRadioButton("Phone no");
		rdbtnPhoneNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnPhoneNo.setBounds(6, 42, 109, 23);
		panel.add(rdbtnPhoneNo);
		buttonGroup.add(rdbtnPhoneNo);

		Label label = new Label("Search term");
		label.setBounds(196, 13, 78, 22);
		getContentPane().add(label);

		btnSearch = new Button("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performSearch();
			}
		});
		btnSearch.setBounds(311, 27, 70, 22);
		getContentPane().add(btnSearch);

		cboResults = new JComboBox<Customer>();
		cboResults.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboResults.setBounds(10, 112, 403, 27);
		getContentPane().add(cboResults);

		Button btnCancel = new Button("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchForCustomerGui.this.dispose();
			}
		});
		btnCancel.setBounds(311, 84, 70, 22);
		getContentPane().add(btnCancel);
		

		
		Button btnVerifyCustomer = new Button("Verify customer");
		btnVerifyCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboResults.getSelectedItem() != null)
				{
				Icon icon = UIManager.getIcon("OptionPane.questionIcon");
				//prompt user for secret answer with dialog box
				String ans = (String)JOptionPane.showInputDialog(
	                    SearchForCustomerGui.this,
	                    "Please enter the customers secret answer",
	                    "Customized Dialog",
	                    JOptionPane.PLAIN_MESSAGE,
	                    icon,
	                    null,
	                    "Answer");	
				//check answer with main controller class
				if(SearchForCustomerGui.this.context.verifyCustomer((Customer)cboResults.getSelectedItem(), ans)){
					//display accepted message 
					SearchForCustomerGui.this.lblCustomerVerified.setText("Customer verified");
					SearchForCustomerGui.this.lblCustomerVerified.setForeground(Color.green);
				}
				else{
					//display failed verification message
					SearchForCustomerGui.this.lblCustomerVerified.setText("Failed Verification");
					SearchForCustomerGui.this.lblCustomerVerified.setForeground(Color.red);
				}
			}}
		});
		btnVerifyCustomer.setBounds(170, 84, 115, 22);
		getContentPane().add(btnVerifyCustomer);
		
		lblCustomerVerified = new Label("");
		lblCustomerVerified.setBounds(10, 89, 173, 22);
		getContentPane().add(lblCustomerVerified);
		
		Button btnUse = new Button("Use");
		btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboResults.getSelectedItem() != null){
					SearchForCustomerGui.this.context.useCustomer((Customer)cboResults.getSelectedItem(), SearchForCustomerGui.this.insertCustomer);
					SearchForCustomerGui.this.dispose();
				}
			}
		});
		btnUse.setBounds(311, 55, 70, 22);
		getContentPane().add(btnUse);

		this.setSize(444, 193);
		this.setLocationRelativeTo(ProgramChoiceGui.getFrames()[0]);
		//this.setLocationRelativeTo(null);
		//this.setLocationByPlatform(true);
		this.setVisible(true);
	}
	
	public void performSearch(){
		// clear the window
		cboResults.removeAllItems();
		// perform search
		if (SearchForCustomerGui.this.rdbtnSurname.isSelected())
			for (Customer customer : SearchForCustomerGui.this.context
					.customerSearch(
							SearchForCustomerGui.this.txtSearchTerm
									.getText(), Customer.SURNAME))
				//add to ui
				cboResults.addItem(customer);
		else if (SearchForCustomerGui.this.rdbtnPhoneNo.isSelected())
			for (Customer customer : SearchForCustomerGui.this.context
					.customerSearch(
							SearchForCustomerGui.this.txtSearchTerm
									.getText(), Customer.PHONENO))
				cboResults.addItem(customer);
		//else
			// no search criteria selected
			//do nothing			
	}
}
