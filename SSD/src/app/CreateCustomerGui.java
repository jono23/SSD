package app;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateCustomerGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6058228792766735240L;
	private TA context;
	private JTextField txtFirstname;
	private JTextField txtSurname;
	private JTextField txtAddress;
	private JTextField txtPhoneNo;
	private JTextField txtSecretAnswer;
	private JLabel lblFirstname;
	private JLabel lblSurname;
	private JLabel lblAddress;
	private JLabel lblPhoneNo;
	private JLabel lblSecretAns;
	private JLabel lblMessage;

	public CreateCustomerGui(TA context) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CreateCustomerGui.this.context.startCreateProgramChoice();
				CreateCustomerGui.this.dispose();				
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.context = context;
		getContentPane().setLayout(null);
		
		lblMessage = new JLabel("");
		lblMessage.setBounds(42, 11, 334, 14);
		getContentPane().add(lblMessage);

		txtFirstname = new JTextField();
		txtFirstname.setBounds(160, 46, 118, 20);
		getContentPane().add(txtFirstname);
		txtFirstname.setColumns(10);
		
		txtSurname = new JTextField();
		txtSurname.setBounds(160, 77, 118, 20);
		getContentPane().add(txtSurname);
		txtSurname.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(160, 108, 118, 20);
		getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
		
		txtPhoneNo = new JTextField();
		txtPhoneNo.setBounds(160, 139, 118, 20);
		getContentPane().add(txtPhoneNo);
		txtPhoneNo.setColumns(10);
		
		txtSecretAnswer = new JTextField();
		txtSecretAnswer.setBounds(160, 170, 118, 20);
		getContentPane().add(txtSecretAnswer);
		txtSecretAnswer.setColumns(10);
		
		lblFirstname = new JLabel("Firstname");
		lblFirstname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstname.setBounds(76, 49, 74, 14);
		getContentPane().add(lblFirstname);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSurname.setBounds(76, 80, 74, 14);
		getContentPane().add(lblSurname);
		
		lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setBounds(76, 111, 74, 14);
		getContentPane().add(lblAddress);
		
		lblPhoneNo = new JLabel("Phone no");
		lblPhoneNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhoneNo.setBounds(76, 142, 74, 14);
		getContentPane().add(lblPhoneNo);
		
		lblSecretAns = new JLabel("Secret ans");
		lblSecretAns.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecretAns.setBounds(76, 173, 74, 14);
		getContentPane().add(lblSecretAns);
		
		Button btnCancel = new Button("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateCustomerGui.this.context.startCreateProgramChoice();
				CreateCustomerGui.this.dispose();
				
			}
		});
		btnCancel.setBounds(109, 213, 89, 23);
		getContentPane().add(btnCancel);
		
		Button btnCreate = new Button("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean[] errors = CreateCustomerGui.this.context.newCustomer(txtFirstname.getText(), txtSurname.getText(), 
						txtAddress.getText(), txtPhoneNo.getText(), txtSecretAnswer.getText());
				//first clear previous colouring
				clearErrors();
				
				boolean isErrors = false;
					for (int i = 0; i < 5; i++)
						if(errors[i] == true)
							isErrors = true;
				
				if (isErrors){		
					//display contains errors message
					CreateCustomerGui.this.lblMessage.setText("Errors exist, please correct the highlighted input fields");
				
					if(errors[0]){
						CreateCustomerGui.this.txtFirstname.setBackground(Color.RED);
						CreateCustomerGui.this.lblFirstname.setForeground(Color.RED);
					}
					if(errors[1]){
						CreateCustomerGui.this.txtSurname.setBackground(Color.RED);
						CreateCustomerGui.this.lblSurname.setForeground(Color.RED);
					}
					if(errors[2]){					
						CreateCustomerGui.this.txtAddress.setBackground(Color.RED);
						CreateCustomerGui.this.lblAddress.setForeground(Color.RED);
					}
					if(errors[3]){					
						CreateCustomerGui.this.txtPhoneNo.setBackground(Color.RED);
						CreateCustomerGui.this.lblPhoneNo.setForeground(Color.RED);
					}
					if(errors[4]){
						CreateCustomerGui.this.txtSecretAnswer.setBackground(Color.RED);
						CreateCustomerGui.this.lblSecretAns.setForeground(Color.RED);
					}						
					
				}else{
					//display confirmation message
					CreateCustomerGui.this.lblMessage.setText("Successfully added customer : " + CreateCustomerGui.this.txtSurname.getText());
				}
					
			}
		});
		btnCreate.setBounds(208, 213, 89, 23);
		getContentPane().add(btnCreate);
		
		this.setSize(400, 310);
		this.setLocationRelativeTo(ProgramChoiceGui.getFrames()[0]);
		//this.setLocationRelativeTo(null);
		//this.setLocationByPlatform(true);
		this.setVisible(true);
		
	}
	private void clearErrors(){
		CreateCustomerGui.this.txtFirstname.setBackground(Color.WHITE);
		CreateCustomerGui.this.lblFirstname.setForeground(Color.BLACK);
		CreateCustomerGui.this.txtSurname.setBackground(Color.WHITE);
		CreateCustomerGui.this.lblSurname.setForeground(Color.BLACK);
		CreateCustomerGui.this.txtAddress.setBackground(Color.WHITE);
		CreateCustomerGui.this.lblAddress.setForeground(Color.BLACK);
		CreateCustomerGui.this.txtPhoneNo.setBackground(Color.WHITE);
		CreateCustomerGui.this.lblPhoneNo.setForeground(Color.BLACK);
		CreateCustomerGui.this.txtSecretAnswer.setBackground(Color.WHITE);
		CreateCustomerGui.this.lblSecretAns.setForeground(Color.BLACK);
	}
}
