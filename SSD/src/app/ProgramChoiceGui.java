package app;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;

public class ProgramChoiceGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4268307842426734794L;
	private TA context;

	public ProgramChoiceGui(TA context) {
		this.context = context;

		getContentPane().setLayout(null);

		JButton btnAdd = new JButton("Add Customer");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgramChoiceGui.this.context.startCreateNewCustomer();
				ProgramChoiceGui.this.dispose();
			}
		});
		btnAdd.setBounds(22, 11, 146, 50);
		getContentPane().add(btnAdd);

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgramChoiceGui.this.context.startSearchForCustomer();
				ProgramChoiceGui.this.dispose();
			}
		});
		btnSearch.setBounds(178, 11, 109, 50);
		getContentPane().add(btnSearch);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgramChoiceGui.this.context.quit();
			}
		});
		btnExit.setBounds(198, 99, 89, 23);
		getContentPane().add(btnExit);
		
		JButton btnAddAccommodation = new JButton("Add Accommodation");
		btnAddAccommodation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddAccommodation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProgramChoiceGui.this.context.startAddAccommodation();
				ProgramChoiceGui.this.dispose();				
			}
		});
		btnAddAccommodation.setBounds(22, 72, 146, 50);
		getContentPane().add(btnAddAccommodation);

		this.setSize(325, 180);
		
		//this.setLocationRelativeTo(ProgramChoiceGui.getFrames()[0]);
		this.setLocationRelativeTo(null);
		//this.setLocationByPlatform(true);
		//this.setLocation(200, 200);
		this.setVisible(true);
	}
}
