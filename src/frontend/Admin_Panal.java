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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.naming.directory.SearchControls;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;
import backend.DbConnection;
import backend.SearchingSorting;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class Admin_Panal extends JFrame implements ActionListener, MouseListener, ItemListener{

	private JPanel contentPane;
	private JTable table;
	private JTextField fname;
	private JTextField lname;
	private JTextField dob;
	private JTextField searchField;
	private JTextField dateAdmitted;
	private JTextField med_history;
	private JComboBox<String> doctorCombo;
	private JComboBox<String> nurseCombo;
	private JComboBox<String> searchBox;
	private JComboBox<String> sortCombo;
	private JTextArea desc;
	private JButton updateDetails;
	private JButton btnAdd;
	private JButton btnAddReport;
	private JButton btnShowAdmitted;
	private JButton btnRev;
	private JButton btnBeds;
	private JButton btnAdmit;
	private JButton btnDate;
	private JButton searchBtn;
	private JButton btnReset;
	private DefaultTableModel model;
	private DefaultTableModel recordtable;
	ArrayList<Object> doctorList = new ArrayList<>();
	ArrayList <Object>nurseList = new ArrayList<>();
	ArrayList <Integer>patientList = new ArrayList<>();
	private ResultSet res;
	private HashMap<Integer, String> mapNurse = new HashMap<>(),  mapDoctor= new HashMap<>();
	private static int adminId;
	String name;
	int patId;
	private ArrayList<Vector<String> > patient_details = new ArrayList<Vector<String> >();
	private SearchingSorting manage = new SearchingSorting();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_Panal frame = new Admin_Panal(adminId);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Admin_Panal(int adminId) {
		setTitle("Admin Panel");
		this.adminId = adminId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1106, 703);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Hospital Administration");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		titleLabel.setBounds(367, 20, 350, 33);
		getContentPane().add(titleLabel);
		
		btnAdd = new JButton("Add Patient");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(11, 111, 146, 41);
		getContentPane().add(btnAdd);
		btnAdd.addActionListener(this);
		
		btnAdmit = new JButton("Show Admit Requests");
		btnAdmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdmit.setBounds(167, 111, 181, 41);
		getContentPane().add(btnAdmit);
		btnAdmit.addActionListener(this);
		
		btnAddReport = new JButton("Add Lab Report");
		btnAddReport.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddReport.setBounds(367, 111, 159, 41);
		getContentPane().add(btnAddReport);
		btnAddReport.addActionListener(this);
		
		btnDate = new JButton("Manage Dates");
		btnDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDate.setBounds(536, 111, 165, 41);
		getContentPane().add(btnDate);
		btnDate.addActionListener(this);
		
		btnShowAdmitted = new JButton("Allocated Beds");
		btnShowAdmitted.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowAdmitted.setBounds(723, 111, 165, 41);
		getContentPane().add(btnShowAdmitted);
		btnShowAdmitted.addActionListener(this);
		
		btnBeds = new JButton("Add Beds");
		btnBeds.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBeds.setBounds(910, 111, 172, 41);
		getContentPane().add(btnBeds);
		btnBeds.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 239, 715, 319);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(this);
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = {
				"First Name", "Last Name", "DOB", "Admit Date", "Med_History", "Doctor", "Nurse", "Description", "id"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		
		JLabel lblFname = new JLabel("First Name");
		lblFname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFname.setBounds(27, 192, 75, 25);
		getContentPane().add(lblFname);
		
		fname = new JTextField();
		fname.setBounds(112, 193, 198, 25);
		getContentPane().add(fname);
		fname.setColumns(10);
		
		JLabel lblLname = new JLabel("Last Name");
		lblLname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLname.setBounds(27, 227, 75, 25);
		getContentPane().add(lblLname);
		
		lname = new JTextField();
		lname.setColumns(10);
		lname.setBounds(112, 228, 198, 25);
		getContentPane().add(lname);
		
		JLabel lblDob = new JLabel(" DOB");
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDob.setBounds(27, 272, 75, 25);
		getContentPane().add(lblDob);
		
		dob = new JTextField();
		dob.setColumns(10);
		dob.setBounds(112, 273, 198, 25);
		getContentPane().add(dob);
		
		JLabel lblDate = new JLabel("Date Admitted");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDate.setBounds(11, 307, 91, 25);
		getContentPane().add(lblDate);
		
		dateAdmitted = new JTextField();
		dateAdmitted.setColumns(10);
		dateAdmitted.setBounds(112, 308, 198, 25);
		getContentPane().add(dateAdmitted);
		
		JLabel lblMed = new JLabel("Medical History");
		lblMed.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMed.setBounds(11, 352, 91, 25);
		getContentPane().add(lblMed);
		
		med_history = new JTextField();
		med_history.setColumns(10);
		med_history.setBounds(112, 353, 198, 25);
		getContentPane().add(med_history);
		
		JLabel lblDoc = new JLabel("Doctor");
		lblDoc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDoc.setBounds(27, 401, 75, 25);
		getContentPane().add(lblDoc);
		
		JLabel lblDesc = new JLabel("Description");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDesc.setBounds(11, 511, 75, 25);
		getContentPane().add(lblDesc);
		
		desc = new JTextArea();
		desc.setBounds(112, 489, 245, 69);
		getContentPane().add(desc);
		
		updateDetails = new JButton("Update Details");
		updateDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		updateDetails.setBounds(132, 568, 181, 54);
		getContentPane().add(updateDetails);
		updateDetails.addActionListener(this);
		
		JLabel lblNurse = new JLabel("Nurse");
		lblNurse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNurse.setBounds(27, 449, 75, 25);
		getContentPane().add(lblNurse);
		
		doctorCombo = new JComboBox<String>();
		doctorCombo.setBounds(112, 404, 198, 22);
		getContentPane().add(doctorCombo);
		
		nurseCombo = new JComboBox<String>();
		nurseCombo.setBounds(112, 452, 198, 22);
		getContentPane().add(nurseCombo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setColumns(10);
		searchField.setBounds(680, 204, 126, 25);
		getContentPane().add(searchField);
		
		searchBtn = new JButton("Search");
		searchBtn.setBounds(816, 203, 126, 25);
		getContentPane().add(searchBtn);
		
		String[] choices = {"First Name", "Last Name", "Doctor", "Nurse", "Ward"};
		searchBox = new JComboBox<String>(choices);
		searchBox.setBounds(531, 204, 126, 25);
		getContentPane().add(searchBox);
		searchBtn.addActionListener(this);
		
		String[] sortChoices = {"Id","First Name", "Last Name","Sort By"};
		sortCombo = new JComboBox<String>(sortChoices);
		sortCombo.setBounds(400, 204, 126, 25);
		getContentPane().add(sortCombo);
		
		btnRev = new JButton("R");
		btnRev.setBounds(351, 204, 50, 25);
		getContentPane().add(btnRev);
		
		sortCombo.setSelectedIndex(-1);
		sortCombo.setSelectedItem("Sort By");
		sortCombo.addItemListener(this);
		
		btnReset = new JButton("Reset");
		btnReset.setBounds(952, 204, 126, 25);
		getContentPane().add(btnReset);
		
		populateComboBox();
		populatePatientSection();
		btnReset.addActionListener(this);
		btnRev.addActionListener(this);
//		table.removeColumn(table.getColumnModel().getColumn(8));
	}

	
	private void populatePatientSection() {
		int colCount;
		try {
	        
	       
	        res = AdminOperations.getPatients();
	        ResultSetMetaData  patientData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	        
	        recordtable=(DefaultTableModel)table.getModel();
	        recordtable.setRowCount(0);
	                
	        while (res.next()) {
	                    
                Vector<String> columnData= new Vector<String>();
                patientList.add(res.getInt("id"));
                
                for(int i=0;i<=colCount;i++) {
                    columnData.add(res.getString("fname"));
                    columnData.add(res.getString("lname"));
                    columnData.add(res.getString("dob"));
                    columnData.add(res.getString("admit_date"));
                    columnData.add(res.getString("medical_history"));
                    columnData.add(mapDoctor.get(res.getInt("doctor")));
                    columnData.add(mapNurse.get(res.getInt("nurse")));
                    columnData.add(res.getString("description"));
                    columnData.add(res.getString("id"));
                    
                    
                    
                }
                patient_details.add(columnData);
                recordtable.addRow(columnData);  
            }
	        
	                
		}
	    catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, ex);
	        
	        
	        
	        
	    }
	}
	public void populateComboBox() {


		try {
			res = AdminOperations.getDoctors();
			while(res.next()) {
				doctorList.add(res.getString(1));
				doctorCombo.addItem(res.getString(2));
				mapDoctor.put(res.getInt(1), res.getString(2));
				
				
			}
			res = AdminOperations.getNurses();
			while(res.next()) {
				nurseList.add(res.getString(1));
				nurseCombo.addItem(res.getString(2));
				mapNurse.put(res.getInt(1), res.getString(2));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		 DefaultTableModel recordtable= (DefaultTableModel)table.getModel();
	 	int SelectedRow=table.getSelectedRow();
        fname.setText(recordtable.getValueAt(SelectedRow,0).toString());
        lname.setText(recordtable.getValueAt(SelectedRow,1).toString());
        dob.setText(recordtable.getValueAt(SelectedRow,2).toString());
        dateAdmitted.setText(recordtable.getValueAt(SelectedRow,3).toString());
        med_history.setText(recordtable.getValueAt(SelectedRow,4).toString());
        desc.setText(recordtable.getValueAt(SelectedRow,7).toString());
        doctorCombo.setSelectedItem(recordtable.getValueAt(SelectedRow,5).toString());
        nurseCombo.setSelectedItem(recordtable.getValueAt(SelectedRow,6).toString());
        patId = Integer.parseInt(recordtable.getValueAt(SelectedRow,8).toString());
        name = recordtable.getValueAt(SelectedRow,0).toString() + " " + recordtable.getValueAt(SelectedRow,1).toString();

		
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
		if (e.getSource()==updateDetails){
	        String up_fname, up_lname, up_Dob, up_admit_date, up_med_history,up_desp;
	        Integer  up_doc, up_nurse, id;
	        up_fname = fname.getText();
	        up_lname = lname.getText();
	        up_Dob = dob.getText();
	        up_admit_date = dateAdmitted.getText();
	        up_med_history = med_history.getText();
	        int indexDoctor =  doctorCombo.getSelectedIndex();			
			int indexNurse = nurseCombo.getSelectedIndex();	
	        up_doc = Integer.parseInt(doctorList.get(indexDoctor).toString());
			up_nurse = Integer.parseInt(nurseList.get(indexNurse).toString());
	        up_desp = desc.getText();
	        id=  patientList.get(table.getSelectedRow());
	        boolean success = AdminOperations.updatePatient(up_fname, up_lname, up_Dob, up_admit_date, up_med_history, up_doc, up_nurse, up_desp, id);
	        if(success) {
	        	JOptionPane.showMessageDialog(null, "data updated successsfully");
		        refreshTable();
	        }
	        else {
	        	JOptionPane.showMessageDialog(null, "data updated Failed");
	        }
	        
	        }
		
		else if(e.getSource()==btnAdd) {
			new AddPatient().setVisible(true);
			
		}
		
		else if(e.getSource()==btnAdmit) {
			new AdmitPatient().setVisible(true);
			
		}
		else if(e.getSource()==btnDate) {
			if(!(patId==0)) {
			new KeyDateAdmin(patId, name).setVisible(true);}
			else {
				JOptionPane.showMessageDialog(null, "Please select a patient!");
			}
		}
		
		else if(e.getSource()==btnAddReport) {
			new ReportUploadFrame(patId, name).setVisible(true);
		}
		
		else if(e.getSource()==searchBtn) {
			String query = searchField.getText();
			String choice = (String) searchBox.getSelectedItem();
			patient_details = manage.search(patient_details, query,choice);
			addSearchedData(patient_details);
		}
		else if(e.getSource()==btnReset) {
			clearTable();
			populatePatientSection();
		}
		
		else if(e.getSource()==btnRev) {
			clearTable();
			manage.reverse(patient_details);
			addSearchedData(patient_details);
		} 
		else if(e.getSource()==btnShowAdmitted) {
			new AllocatedBeds().setVisible(true);
		}
		
		else if(e.getSource()==btnBeds) {
			new AddBed().setVisible(true);
		}
			
		}
	
	public void refreshTable() {
		clearTable();
		populatePatientSection();
		}
	
	private void clearTable() {
		for(int i=model.getRowCount()-1;i>=0;i--){
			model.removeRow(i);
		   }
	}
	
	public void addSearchedData(ArrayList<Vector<String>> data) {
		clearTable();
		for(Vector<String> i: data) {
			recordtable.addRow(i);
		}
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==sortCombo) {
			String selected = (String) sortCombo.getSelectedItem();
			if(!selected.equals("Sort By")) {
				addSearchedData(manage.sort(patient_details, selected));
				
			}
		}
		
	}
	
		
	}
	
	

