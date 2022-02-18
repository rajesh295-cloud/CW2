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

public class KeyDateNurse extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField treatmentField;
	private DefaultTableModel model;
	private JButton btnUpdate;
	private JTable table;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JButton btnExit;
	private static int patient;
	private static int id;
	private static String patientName;
	private ResultSet res;
	private JTextField comboVal;
	private JTextField timePicker;
	private JTextField datePicker;

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
	public KeyDateNurse(int patient, String patientName) {
		setTitle("Key Date Page");
		this.patient = patient;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 802, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel kelLbl = new JLabel("Treatment List For This Patient");
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
		treatmentField.setEditable(false);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(390, 42, 390, 226);
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
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(512, 278, 177, 35);
		contentPane.add(btnExit);
		
		JLabel lblLab = new JLabel("Lab");
		lblLab.setHorizontalAlignment(SwingConstants.CENTER);
		lblLab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLab.setBounds(10, 224, 154, 35);
		contentPane.add(lblLab);
		
		datePicker = new JTextField();
		datePicker.setBounds(138, 66, 180, 39);
		contentPane.add(datePicker);
		datePicker.setEditable(false);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(10, 108, 144, 35);
		contentPane.add(lblTime);
		
		timePicker = new JTextField();
		timePicker.setBounds(138, 111, 180, 35);
		contentPane.add(timePicker);
		timePicker.setEditable(false);;
		
		 String[] items = new String[] {"Hematology", "Immunology", "Cytology", "Transfusion Services"};
		comboVal = new JTextField();
		comboVal.setBounds(138, 224, 180, 35);
		contentPane.add(comboVal);
		comboVal.setEditable(false);
		populateDates(patient);
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.addMouseListener(this);
		
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
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnExit) {
			this.dispose();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int[] selRows = table.getSelectedRows();
		TableModel recordtable= table.getModel();
		id = Integer.parseInt(recordtable.getValueAt(selRows[0],4).toString());
		datePicker.setText(recordtable.getValueAt(selRows[0],0).toString());
		timePicker.setText(recordtable.getValueAt(selRows[0],1).toString());
		treatmentField.setText(recordtable.getValueAt(selRows[0],2).toString());
		comboVal.setText(recordtable.getValueAt(selRows[0],3).toString());
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
