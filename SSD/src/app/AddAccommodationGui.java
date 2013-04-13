package app;
import java.awt.Button;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;


public class AddAccommodationGui extends JFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 2127953546073440570L;
		private TA context;
		private JTextField txtName;
		private JTextField txtRating;
		private JTextField txtNoOfRooms;
		private JTextField txtFeature;
		private JLabel lblname;
		private JLabel lblRating;
		private JLabel lblNoOfRooms;
		private JLabel lblFeature;
		private JLabel lblMessage;
		private JTextArea txtFeatures;
		private ArrayList<String> features;

		public AddAccommodationGui(TA context) {
			features = new ArrayList<String>();
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					AddAccommodationGui.this.context.startCreateProgramChoice();
					AddAccommodationGui.this.dispose();				
				}
			});
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.context = context;
			getContentPane().setLayout(null);
			
			lblMessage = new JLabel("");
			lblMessage.setBounds(42, 11, 334, 14);
			getContentPane().add(lblMessage);

			txtName = new JTextField();
			txtName.setBounds(160, 30, 118, 20);
			getContentPane().add(txtName);
			txtName.setColumns(10);
			
			txtRating = new JTextField();
			txtRating.setBounds(160, 61, 118, 20);
			getContentPane().add(txtRating);
			txtRating.setColumns(10);
			
			txtNoOfRooms = new JTextField();
			txtNoOfRooms.setBounds(160, 92, 118, 20);
			getContentPane().add(txtNoOfRooms);
			txtNoOfRooms.setColumns(10);
			
			txtFeature = new JTextField();
			txtFeature.setBounds(160, 123, 118, 20);
			getContentPane().add(txtFeature);
			txtFeature.setColumns(10);
			
			lblname = new JLabel("Name");
			lblname.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblname.setHorizontalAlignment(SwingConstants.RIGHT);
			lblname.setBounds(76, 33, 74, 14);
			getContentPane().add(lblname);
			
			lblRating = new JLabel("Rating");
			lblRating.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblRating.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRating.setBounds(76, 64, 74, 14);
			getContentPane().add(lblRating);
			
			lblNoOfRooms = new JLabel("Number of rooms");
			lblNoOfRooms.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNoOfRooms.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfRooms.setBounds(10, 95, 140, 14);
			getContentPane().add(lblNoOfRooms);
			
			lblFeature = new JLabel("Features");
			lblFeature.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblFeature.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFeature.setBounds(76, 126, 74, 14);
			getContentPane().add(lblFeature);
						
			txtFeatures = new JTextArea();
			txtFeatures.setBackground(SystemColor.control);
			txtFeatures.setBounds(160, 154, 118, 72);
			getContentPane().add(txtFeatures);


			
			
			this.setSize(400, 310);
			this.setLocationRelativeTo(ProgramChoiceGui.getFrames()[0]);
			//this.setLocationRelativeTo(null);
			//this.setLocationByPlatform(true);
			this.setVisible(true);

			Button btnCancel = new Button("Close");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddAccommodationGui.this.context.startCreateProgramChoice();
					AddAccommodationGui.this.dispose();
					
				}
			});
			btnCancel.setBounds(106, 239, 89, 23);
			getContentPane().add(btnCancel);
			
			Button btnCreate = new Button("Create");
			btnCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean[] errors = AddAccommodationGui.this.context.addAccommodation(txtName.getText(), txtRating.getText(), 
							txtNoOfRooms.getText(), features);
					//first clear previous colouring
					clearErrors();
					
					boolean isErrors = false;
						for (int i = 0; i < 3; i++)
							if(errors[i] == true)
								isErrors = true;
					
					if (isErrors){		
						//display contains errors message
						AddAccommodationGui.this.lblMessage.setText("Errors exist, please correct the highlighted input fields");
					
						if(errors[0]){
							AddAccommodationGui.this.txtName.setBackground(Color.RED);
							AddAccommodationGui.this.lblname.setForeground(Color.RED);
						}
						if(errors[1]){
							AddAccommodationGui.this.txtRating.setBackground(Color.RED);
							AddAccommodationGui.this.lblRating.setForeground(Color.RED);
						}
						if(errors[2]){					
							AddAccommodationGui.this.txtNoOfRooms.setBackground(Color.RED);
							AddAccommodationGui.this.lblNoOfRooms.setForeground(Color.RED);
						}
					
						
					}else{
						//display confirmation message
						AddAccommodationGui.this.lblMessage.setText("Successfully added accommodation : " + AddAccommodationGui.this.txtName.getText());
					}
						
				}
			});
			btnCreate.setBounds(205, 239, 89, 23);
			getContentPane().add(btnCreate);
			
			Button btnAddFeature = new Button("Add feature");
			btnAddFeature.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AddAccommodationGui.this.features.add(AddAccommodationGui.this.txtFeature.getText());
					String featureText = "";
					for (String f : features){
						featureText += f + "\n";
					}
					AddAccommodationGui.this.txtFeatures.setText(featureText);
				}
			});
			btnAddFeature.setBounds(293, 122, 70, 22);
			getContentPane().add(btnAddFeature);
			
			
		}
		private void clearErrors(){
			AddAccommodationGui.this.txtName.setBackground(Color.WHITE);
			AddAccommodationGui.this.lblname.setForeground(Color.BLACK);
			AddAccommodationGui.this.txtRating.setBackground(Color.WHITE);
			AddAccommodationGui.this.lblRating.setForeground(Color.BLACK);
			AddAccommodationGui.this.txtNoOfRooms.setBackground(Color.WHITE);
			AddAccommodationGui.this.lblNoOfRooms.setForeground(Color.BLACK);
		}
}