package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.TimePicker;

import backend.DoctorOperations;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class AddPrescription extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textMed;
	private TimePicker timePicker;
	private JButton btnAdd;
	private static int patient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPrescription frame = new AddPrescription(patient);
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
	public AddPrescription(int patient) {
		setTitle("Prescriptions Page");
		this.patient = patient;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add Prescription");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(93, 10, 262, 31);
		contentPane.add(lblNewLabel);
		
		JLabel medLbl = new JLabel("Medicine");
		medLbl.setHorizontalAlignment(SwingConstants.CENTER);
		medLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		medLbl.setBounds(10, 68, 159, 31);
		contentPane.add(medLbl);
		
		textMed = new JTextField();
		textMed.setBounds(167, 68, 236, 31);
		contentPane.add(textMed);
		textMed.setColumns(10);
		
		JLabel timeLbl = new JLabel("Time");
		timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		timeLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeLbl.setBounds(10, 123, 159, 31);
		contentPane.add(timeLbl);
		
		timePicker = new TimePicker();
		timePicker.setBounds(167, 130, 236, 31);
		contentPane.add(timePicker);

		
		btnAdd = new JButton("Add Prescription");
		btnAdd.setBounds(182, 187, 151, 38);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdd) {
			String medicine = textMed.getText();
			String time = timePicker.getText();
			if(DoctorOperations.setPrescription(medicine, time, patient)) {
				JOptionPane.showMessageDialog(null, "Prescription Added Successfully");
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Failed to Add Prescription");
			}
		}
		
	}
}
