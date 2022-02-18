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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;
import backend.DbConnection;
import backend.DoctorOperations;

public class KeyDateDoctor extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField treatmentField;
	private DefaultTableModel model;
	private JButton btnAdd;
	private JTable table;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JButton btnCancleTratement;
	private static int patient;
	private static int id;
	private static String patientName;
	private ResultSet res;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeyDateDoctor frame = new KeyDateDoctor(patient, patientName);
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
	public KeyDateDoctor(int patient, String patientName) {
		setTitle("Key Date Management");
		this.patient = patient;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 802, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel kelLbl = new JLabel("Add Treatment For Patient");
		kelLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		kelLbl.setHorizontalAlignment(SwingConstants.CENTER);
		kelLbl.setBounds(55, 10, 298, 35);
		contentPane.add(kelLbl);
		
		JLabel lblPatient = new JLabel("Patient");
		lblPatient.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatient.setBounds(0, 68, 154, 35);
		contentPane.add(lblPatient);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(164, 64, 154, 39);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(patientName);
		
		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setHorizontalAlignment(SwingConstants.CENTER);
		lblTreatment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTreatment.setBounds(0, 146, 154, 35);
		contentPane.add(lblTreatment);
		
		treatmentField = new JTextField();
		treatmentField.setColumns(10);
		treatmentField.setBounds(164, 142, 154, 39);
		contentPane.add(treatmentField);
		
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(139, 211, 177, 35);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(this);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(390, 42, 390, 163);
		contentPane.add(scrollPane);
		
		model = new DefaultTableModel();
		table = new JTable();
		table.addMouseListener(this);
		scrollPane.setViewportView(table);
		table.setModel(model);
		scrollPane.setViewportView(table);
		Object[] column = {
				"Date", "Time", "Treatment", "Lab", "id"};
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);

		
		lblNewLabel = new JLabel("Current Treatments");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(434, 0, 276, 35);
		contentPane.add(lblNewLabel);
		
		btnCancleTratement = new JButton("Cancle Tratement");
		btnCancleTratement.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancleTratement.setBounds(509, 211, 177, 35);
		contentPane.add(btnCancleTratement);
		btnCancleTratement.addActionListener(this);
		populateDates(patient);
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
                  columnData.add(res.getString("Date"));
                  columnData.add(res.getString("Time"));
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
		


	

	
	public void refreshTable() {
		for(int i=table.getRowCount()-1;i>=0;i--){
			model.removeRow(i);
		    }
		populateDates(patient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd) {
			String data = treatmentField.getText();
			if(DoctorOperations.setTreatment(data, patient)) { JOptionPane.showMessageDialog(null, "Treatment Added successsfully");refreshTable();}
			else {
				JOptionPane.showMessageDialog(null, "Failed to Add Treatment");
			}
		}
		
		if(e.getSource()==btnCancleTratement) {
			if(id!=0) {
				if(DoctorOperations.removeTreatment(id)) { JOptionPane.showMessageDialog(null, "Treatment Removed successsfully");refreshTable();}
				else {
					JOptionPane.showMessageDialog(null, "Failed to Remove Treatment");
				}
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int[] selRows = table.getSelectedRows();
		TableModel recordtable= table.getModel();
		id = Integer.parseInt(recordtable.getValueAt(selRows[0],4).toString());
		
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
