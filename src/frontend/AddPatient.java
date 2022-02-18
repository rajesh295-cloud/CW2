package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

import backend.AdminOperations;
import backend.DbConnection;

import javax.swing.JComboBox;

public class AddPatient extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField medHistory;
	private JTextField labReport;
	private JTextArea patientDesc; 
	private JDateChooser dobChooser; 
	private JComboBox doctorCombo; 
	private JComboBox nurseCombo; 
	private JDateChooser admitDate;
	private JButton btnAdmit;
	ArrayList doctorList = new ArrayList<>();
	ArrayList nurseList = new ArrayList<>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPatient frame = new AddPatient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddPatient() {
		setTitle("Add Patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 697, 727);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Patient Admit Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(204, 30, 339, 39);
		contentPane.add(lblNewLabel);
		
		JLabel fnameLabel = new JLabel("First Name");
		fnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fnameLabel.setBounds(89, 108, 183, 30);
		contentPane.add(fnameLabel);
		
		fnameField = new JTextField();
		fnameField.setBounds(273, 108, 270, 30);
		contentPane.add(fnameField);
		fnameField.setColumns(10);
		
		JLabel lnameLabel = new JLabel("Last Name");
		lnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lnameLabel.setBounds(89, 148, 183, 30);
		contentPane.add(lnameLabel);
		
		lnameField = new JTextField();
		lnameField.setColumns(10);
		lnameField.setBounds(273, 148, 270, 30);
		contentPane.add(lnameField);
		
		JLabel dobLabel = new JLabel("DOB");
		dobLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dobLabel.setBounds(89, 190, 183, 30);
		contentPane.add(dobLabel);
		
		JLabel medLabel = new JLabel("Medical History");
		medLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		medLabel.setBounds(89, 230, 183, 30);
		contentPane.add(medLabel);
		
		medHistory = new JTextField();
		medHistory.setColumns(10);
		medHistory.setBounds(273, 230, 270, 30);
		contentPane.add(medHistory);
		
		JLabel reportLabel = new JLabel("Lab Report");
		reportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		reportLabel.setBounds(89, 279, 183, 30);
		contentPane.add(reportLabel);
		
		labReport = new JTextField();
		labReport.setColumns(10);
		labReport.setBounds(273, 279, 270, 30);
		contentPane.add(labReport);
		
		JLabel docLabel = new JLabel("Nurse");
		docLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		docLabel.setBounds(89, 334, 183, 30);
		contentPane.add(docLabel);
		
		JLabel nurseLabel = new JLabel("Doctor");
		nurseLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nurseLabel.setBounds(89, 382, 183, 30);
		contentPane.add(nurseLabel);
		
		JLabel descLabel = new JLabel("Patient Description");
		descLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descLabel.setBounds(89, 541, 183, 30);
		contentPane.add(descLabel);
		
		patientDesc = new JTextArea();
		patientDesc.setBounds(273, 516, 270, 100);
		contentPane.add(patientDesc);
		
		btnAdmit = new JButton("Admit Patient");
		btnAdmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdmit.setBounds(303, 633, 162, 47);
		contentPane.add(btnAdmit);
		btnAdmit.addActionListener(this);
		
		dobChooser = new JDateChooser();
		dobChooser.setBounds(273, 188, 276, 30);
		contentPane.add(dobChooser);
		
		doctorCombo = new JComboBox();
		doctorCombo.setBounds(273, 388, 270, 30);
		contentPane.add(doctorCombo);
		
		nurseCombo = new JComboBox();
		nurseCombo.setBounds(273, 336, 270, 30);
		contentPane.add(nurseCombo);
		
		JLabel admitDateLbl = new JLabel("Admit Date");
		admitDateLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		admitDateLbl.setBounds(89, 460, 183, 30);
		contentPane.add(admitDateLbl);
		
		admitDate = new JDateChooser();
		admitDate.setBounds(267, 460, 276, 30);
		contentPane.add(admitDate);
		
		populateComboBox();
		
	}
	
	public void populateComboBox() {
		PreparedStatement st;
		PreparedStatement st2;
		DbConnection connection = new DbConnection();
		String query = "SELECT * FROM staff where `post` = 'doctor' ";
		String query2 = "SELECT * FROM staff where `post` = 'nurse' ";
		try {
			st = DbConnection.conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				doctorCombo.addItem(new ComboItems(res.getString(2), res.getInt(1)));

				
			}
			st2 = DbConnection.conn.prepareStatement(query2);
			ResultSet res2 = st2.executeQuery();
			while(res2.next()) {
				nurseCombo.addItem(new ComboItems(res2.getString(2), res2.getInt(1)));

				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean valid(String fname, String lname, String patientMedHistory, int assignedDoctor, int assignedNurse, String desc) {
    	if(fname.equals("")) {JOptionPane.showMessageDialog(this, "First Name can not be set Empty");return false;}
    	if(lname.equals("")) {JOptionPane.showMessageDialog(this, "Last Name can not be set Empty");return false;}
    	if(patientMedHistory.equals("")) {JOptionPane.showMessageDialog(this, "Patient Medical History can not be set Empty");return false;}
    	if(assignedDoctor == 0) {JOptionPane.showMessageDialog(this, "Doctor is required to be Assigned");return false;}
    	if(assignedNurse == 0) {JOptionPane.showMessageDialog(this, "Nurse is required to be Assigned");return false;}
    	if(desc.equals("")) {JOptionPane.showMessageDialog(this, "Patient description is required to be Assigned");return false;}
    	return true;
    }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdmit) {
			String f_name = fnameField.getText();
			String l_name = lnameField.getText();
			String dob = ""+dobChooser.getDate();
			String history= medHistory.getText();
			String desc= patientDesc.getText();
			String dateAdmitted = ""+admitDate.getDate();
			
			Object item1 = doctorCombo.getSelectedItem();
			int assignedDoctor = ((ComboItems) item1).getValue();
			Object item2 = nurseCombo.getSelectedItem();
			int assignedNurse = ((ComboItems) item2).getValue();
			

			
			
			if(valid(f_name,l_name,history, assignedDoctor, assignedNurse, desc)) {
				if(AdminOperations.addPatient(f_name, l_name, history, assignedDoctor, assignedNurse, desc, dob, dateAdmitted)) {
					JOptionPane.showMessageDialog(null, "Patient Added Successfully");
					this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Something Went Wrong");
				}
				
			}
		}
		
	}
}
