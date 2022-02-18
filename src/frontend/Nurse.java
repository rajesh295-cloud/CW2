package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.DoctorOperations;
import backend.NurseOperations;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Nurse extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTable table;
	private JLabel lblfname;
	private JTextField fnameField;
	private JLabel lblLastName;
	private JTextField lnameField;
	private JLabel lblDob;
	private JTextField dobField;
	private JLabel lblDateAdmitted;
	private JTextField admitField;
	private JLabel lblMedicalHistory;
	private JTextField historyField;
	private JLabel lblWard;
	private JTextField wardField;
	private JLabel lblBed;
	private DefaultTableModel model;
	private JTextField bedField;
	private JLabel lblDiagnosis;
	private JTextArea diagnosisArea;
	private ResultSet res;
	private static int staff_id;
	private static int id;
	private JButton btnDates;
	private JButton btnPres;
	private String name;
	private static String fullName;
	private JLabel lblFullName;
	private JLabel lblPost;
	private JButton btnLogout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nurse frame = new Nurse(staff_id, fullName);
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
	public Nurse(int nurseId, String fullName) {
		setTitle("Nurse Pannel");
		this.staff_id = nurseId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(387, 136, 689, 316);
		contentPane.add(scrollPane);

		model = new DefaultTableModel();
		table = new JTable();
		table.addMouseListener(this);
		scrollPane.setViewportView(table);
		table.setModel(model);
		scrollPane.setViewportView(table);
		Object[] column = {
				"First Name", "Last Name", "DOB", "Medical History", "Admit Date", "Status","diagnosis","id"};
		model.setColumnIdentifiers(column);
		
		
		lblfname = new JLabel("First Name");
		lblfname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblfname.setBounds(10, 121, 107, 26);
		contentPane.add(lblfname);
		
		fnameField = new JTextField();
		fnameField.setEditable(false);
		fnameField.setBounds(121, 121, 208, 26);
		contentPane.add(fnameField);
		fnameField.setColumns(10);
		
		lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLastName.setBounds(10, 167, 107, 26);
		contentPane.add(lblLastName);
		
		lnameField = new JTextField();
		lnameField.setEditable(false);
		lnameField.setColumns(10);
		lnameField.setBounds(121, 167, 208, 26);
		contentPane.add(lnameField);
		
		lblDob = new JLabel("DOB");
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDob.setBounds(10, 203, 107, 26);
		contentPane.add(lblDob);
		
		dobField = new JTextField();
		dobField.setEditable(false);
		dobField.setColumns(10);
		dobField.setBounds(121, 203, 208, 26);
		contentPane.add(dobField);
		
		lblDateAdmitted = new JLabel("Date Admitted");
		lblDateAdmitted.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDateAdmitted.setBounds(10, 239, 107, 26);
		contentPane.add(lblDateAdmitted);
		
		admitField = new JTextField();
		admitField.setEditable(false);
		admitField.setColumns(10);
		admitField.setBounds(121, 239, 208, 26);
		contentPane.add(admitField);
		
		lblMedicalHistory = new JLabel("Medical History");
		lblMedicalHistory.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedicalHistory.setBounds(10, 275, 107, 26);
		contentPane.add(lblMedicalHistory);
		
		historyField = new JTextField();
		historyField.setEditable(false);
		historyField.setColumns(10);
		historyField.setBounds(121, 275, 208, 26);
		contentPane.add(historyField);
		
		lblWard = new JLabel("Ward");
		lblWard.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWard.setBounds(10, 311, 107, 26);
		contentPane.add(lblWard);
		
		wardField = new JTextField();
		wardField.setEditable(false);
		wardField.setColumns(10);
		wardField.setBounds(121, 311, 208, 26);
		contentPane.add(wardField);
		
		lblBed = new JLabel("Bed");
		lblBed.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBed.setBounds(10, 347, 107, 26);
		contentPane.add(lblBed);
		
		bedField = new JTextField();
		bedField.setEditable(false);
		bedField.setColumns(10);
		bedField.setBounds(121, 347, 208, 26);
		contentPane.add(bedField);
		
		lblDiagnosis = new JLabel("Current Diagnosis");
		lblDiagnosis.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDiagnosis.setBounds(10, 405, 107, 26);
		contentPane.add(lblDiagnosis);
		
		diagnosisArea = new JTextArea();
		diagnosisArea.setBounds(118, 383, 225, 69);
		contentPane.add(diagnosisArea);
		diagnosisArea.setEditable(false);
		
		btnDates = new JButton("Show Key Dates");
		btnDates.setBounds(387, 27, 162, 41);
		contentPane.add(btnDates);
		btnDates.addActionListener(this);
		
		btnPres = new JButton("Show Prescriptions");
		btnPres.setBounds(634, 27, 162, 41);
		contentPane.add(btnPres);
		btnPres.addActionListener(this);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(874, 27, 162, 41);
		contentPane.add(btnLogout);
		btnLogout.addActionListener(this);
		populatePatients();
		table.removeColumn(table.getColumnModel().getColumn(7));
		table.removeColumn(table.getColumnModel().getColumn(6));
		
		lblFullName = new JLabel(fullName);
		lblFullName.setForeground(Color.BLUE);
		lblFullName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFullName.setBackground(Color.GREEN);
		lblFullName.setBounds(38, 27, 242, 33);
		contentPane.add(lblFullName);
		
		lblPost = new JLabel("Nurse");
		lblPost.setHorizontalAlignment(SwingConstants.CENTER);
		lblPost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPost.setBackground(Color.WHITE);
		lblPost.setBounds(38, 46, 242, 33);
		contentPane.add(lblPost);
	}
	


	private void populatePatients() {
		int colCount;
		Vector<String> columnData;
		try {
			res = NurseOperations.getPatients(staff_id);
	        ResultSetMetaData  patientData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	        DefaultTableModel recordtable=(DefaultTableModel)table.getModel();
	                
	        while (res.next()) {
	                    
                columnData= new Vector<String>();
                for(int i=0;i<=colCount;i++) {
                    columnData.add(res.getString("fname"));
                    columnData.add(res.getString("lname"));
                    columnData.add(res.getString("dob"));
                    columnData.add(res.getString("medical_history"));
                    columnData.add(res.getString("admit_date"));
                    columnData.add(res.getString("status"));
                    columnData.add(res.getString("diagnosis"));
                    columnData.add(res.getString("id"));
                    
                }
                recordtable.addRow(columnData);  
            }
	        
	                
		}
	    catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, ex);
	        
	        
	        
	        
	    }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int SelectedRow;
		int[] selRows = table.getSelectedRows();
		TableModel recordtable= table.getModel();
        SelectedRow=table.getSelectedRow();
        id = Integer.parseInt(recordtable.getValueAt(selRows[0],7).toString());		
        res = NurseOperations.getWardDetails(id);
        fnameField.setText(recordtable.getValueAt(selRows[0],0).toString());
        lnameField.setText(recordtable.getValueAt(selRows[0],1).toString());
        dobField.setText(recordtable.getValueAt(selRows[0],2).toString());
        admitField.setText(recordtable.getValueAt(selRows[0],4).toString());
        historyField.setText(recordtable.getValueAt(selRows[0],3).toString());
        diagnosisArea.setText(recordtable.getValueAt(selRows[0],6).toString());
        name = recordtable.getValueAt(selRows[0],0).toString() + " "+recordtable.getValueAt(selRows[0],1).toString();
        try {
        	wardField.setText("");
			bedField.setText("");
        	while(res.next()) {
			wardField.setText(res.getString("ward_name"));
			bedField.setText(res.getString("bed_name"));}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnDates) {
			new KeyDateNurse(id, name).setVisible(true);
		}
		else if(e.getSource()==btnPres) {
			new Prescription(id).setVisible(true);
		}
		else if(e.getSource()==btnLogout) {
			this.dispose();
		}
		
	}
}
