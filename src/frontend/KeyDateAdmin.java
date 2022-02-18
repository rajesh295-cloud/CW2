package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;
import backend.DbConnection;
import backend.DoctorOperations;

public class KeyDateAdmin extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField treatmentField;
	private DefaultTableModel model;
	private JButton btnUpdate;
	private JTable table;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JButton btnCancleTratement;
	private static int patient;
	private static int id;
	private static String patientName;
	private ResultSet res;
	private JComboBox comboBox;
	private TimePicker timePicker;
	private DatePicker datePicker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeyDateAdmin frame = new KeyDateAdmin(patient, patientName);
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
	public KeyDateAdmin(int patient, String patientName) {
		setTitle("Key Date Management");
		this.patient = patient;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 802, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel kelLbl = new JLabel("Add Treatment For Patient");
		kelLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		kelLbl.setHorizontalAlignment(SwingConstants.CENTER);
		kelLbl.setBounds(55, 10, 298, 35);
		contentPane.add(kelLbl);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(10, 65, 144, 35);
		contentPane.add(lblDate);
		
		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setHorizontalAlignment(SwingConstants.CENTER);
		lblTreatment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTreatment.setBounds(10, 166, 144, 35);
		contentPane.add(lblTreatment);
		
		treatmentField = new JTextField();
		treatmentField.setEditable(false);
		treatmentField.setColumns(10);
		treatmentField.setBounds(138, 166, 180, 39);
		contentPane.add(treatmentField);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(138, 278, 177, 35);
		contentPane.add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(390, 42, 390, 226);
		contentPane.add(scrollPane);
		
		model = new DefaultTableModel();
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(model);
		Object[] column = {
				"Date", "Time", "Treatment", "Lab", "id"};
		model.setColumnIdentifiers(column);
		
		lblNewLabel = new JLabel("Current Treatments");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(434, 0, 276, 35);
		contentPane.add(lblNewLabel);
		
		btnCancleTratement = new JButton("Cancle Tratement");
		btnCancleTratement.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancleTratement.setBounds(512, 278, 177, 35);
		contentPane.add(btnCancleTratement);
		
		JLabel lblLab = new JLabel("Lab");
		lblLab.setHorizontalAlignment(SwingConstants.CENTER);
		lblLab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLab.setBounds(10, 224, 154, 35);
		contentPane.add(lblLab);
		
		datePicker = new DatePicker();
		datePicker.setBounds(138, 66, 180, 39);
		contentPane.add(datePicker);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(10, 108, 144, 35);
		contentPane.add(lblTime);
		
		timePicker = new TimePicker();
		timePicker.setBounds(138, 111, 180, 35);
		contentPane.add(timePicker);
		
		 String[] items = new String[] {"Hematology", "Immunology", "Cytology", "Transfusion Services"};
		comboBox = new JComboBox(items);
		comboBox.setBounds(138, 224, 180, 35);
		contentPane.add(comboBox);
		populateDates(patient);
		table.addMouseListener(this);
		table.removeColumn(table.getColumnModel().getColumn(4));
		
	}
	
	private void populateDates(int patient) {

		int colCount;

		try {
			res = DoctorOperations.getDates(patient);
	        ResultSetMetaData  patientData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	        
	        DefaultTableModel recordtable=(DefaultTableModel)table.getModel();
	        recordtable.setRowCount(0);
	                
	        while (res.next()) {
	                    
                Vector<String> columnData= new Vector<String>();
                
                for(int i=0;i<=colCount;i++) {
                  columnData.add(res.getString("Time"));
                  columnData.add(res.getString("Date"));
                  columnData.add(res.getString("treatment"));
                  columnData.add(res.getString("lab"));
                  columnData.add(res.getString("idkey_treatment_date"));
                   

                    
                    
                }
                recordtable.addRow(columnData);  
            }
	        
	                
		}
	    catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, ex);
	        
	        
	        
	        
	    }	
	}	
		


	private void updateTreatment() {
		String data = treatmentField.getText();
		String time = timePicker.getText();
		String date = datePicker.getText();
		String lab = (String) comboBox.getSelectedItem();

		if(DoctorOperations.updateTreatment(data, id, time, date, lab)) { JOptionPane.showMessageDialog(null, "Treatment Details Updated successsfully");refreshTable();}
		else {
			JOptionPane.showMessageDialog(null, "Failed to Update Treatment");
		}
		
		
	}
	
	private void removeTreatment() {

		if(DoctorOperations.removeTreatment(id)) { JOptionPane.showMessageDialog(null, "Treatment Details Removed successsfully");refreshTable();}
		else {
			JOptionPane.showMessageDialog(null, "Failed to Remove Treatment");
		}
		
		
	}
	
	public void refreshTable() {
		for(int i=table.getRowCount()-1;i>=0;i--){
			model.removeRow(i);
		    }
		populateDates(patient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnUpdate) {
			
			updateTreatment();
		}
		
		if(e.getSource()==btnCancleTratement) {
			if(id!=0) {
				removeTreatment();
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==table) {
			int[] selRows = table.getSelectedRows();
			TableModel recordtable= table.getModel();

			timePicker.setText(recordtable.getValueAt(selRows[0],0).toString());
			datePicker.setText(recordtable.getValueAt(selRows[0],1).toString());
			treatmentField.setText(recordtable.getValueAt(selRows[0],2).toString());
			comboBox.setSelectedItem(recordtable.getValueAt(selRows[0],3).toString());
			id = Integer.parseInt(recordtable.getValueAt(selRows[0],4).toString());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
