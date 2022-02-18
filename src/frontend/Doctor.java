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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.DbConnection;
import backend.DoctorOperations;

import javax.swing.JTextArea;
import javax.swing.JButton;


public class Doctor extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTable table_pat;
	private JTable med_table;
	private JScrollPane scrollPane;
	private DefaultTableModel model_pat;
	private DefaultTableModel model_med;
	private ArrayList<Integer> patientList = new ArrayList<>();
	private static int docId;
	private JTextArea presArea;
	private JTextArea diagArea;
	private int SelectedRow;
	private JButton presBtn;
	private JButton diagBtn;
	private JButton wardBtn;
	private JButton btnDates;
	private JButton btnUpdate;
	private JButton btnRemove;
	private JButton labBtn;
	private int id;
	private int med_id;
	private ResultSet res;
	private String name;
	private String pres;
	private String med_time;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctor frame = new Doctor(docId);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Doctor(int id) {
		setTitle("DOctor's Pannel");
		Doctor.docId = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1195, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Doctor Panal");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(492, 23, 505, 43);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(325, 245, 846, 287);
		contentPane.add(scrollPane_1);
		
		scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		model_pat = new DefaultTableModel();
		table_pat = new JTable();
		table_pat.addMouseListener(this);
		scrollPane.setViewportView(table_pat);
		table_pat.setModel(model_pat);
		scrollPane.setViewportView(table_pat);
		Object[] column = {
				"First Name", "Last Name", "DOB", "Admit Date", "Med_History",  "Description","Ward Status","Prescription","Diagnosis"};
		Object[] row = new Object[0];
		model_pat.setColumnIdentifiers(column);

		diagArea = new JTextArea();
		diagArea.setBounds(21, 414, 284, 95);
		contentPane.add(diagArea);
		
		JLabel lblDiag = new JLabel("Current Diagnosis");
		lblDiag.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiag.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiag.setBounds(49, 377, 210, 27);
		contentPane.add(lblDiag);
		
		diagBtn = new JButton("Add Diagnosis");
		diagBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		diagBtn.setBounds(72, 514, 165, 34);
		contentPane.add(diagBtn);
		diagBtn.addActionListener(this);
		
		JLabel lblPatient = new JLabel("My Patients");
		lblPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPatient.setBounds(636, 208, 217, 27);
		contentPane.add(lblPatient);
		
		
		presBtn = new JButton("Add");
		presBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		presBtn.setBounds(21, 333, 78, 34);
		contentPane.add(presBtn);;
		presBtn.addActionListener(this);

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUpdate.setBounds(109, 333, 78, 34);
		contentPane.add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRemove.setBounds(197, 333, 78, 34);
		contentPane.add(btnRemove);
		btnRemove.addActionListener(this);
		
		JLabel lblPresc = new JLabel("Current Prescriptions");
		lblPresc.setHorizontalAlignment(SwingConstants.CENTER);
		lblPresc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPresc.setBounds(49, 174, 210, 27);
		contentPane.add(lblPresc);
		
		wardBtn = new JButton("Request ward");
		wardBtn.setBounds(380, 91, 198, 49);
		contentPane.add(wardBtn);
		wardBtn.addActionListener(this);
		wardBtn.setEnabled(false);
		
		labBtn = new JButton("Show Lab Report");
		labBtn.setBounds(655, 91, 198, 49);
		contentPane.add(labBtn);
		labBtn.addActionListener(this);
		
		btnDates = new JButton("Add Treatments");
		btnDates.setBounds(957, 91, 198, 49);
		contentPane.add(btnDates);
		btnDates.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("Chirag Simkhada");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(21, 52, 232, 34);
		contentPane.add(lblNewLabel_1);
		populatePatientSection();
		table_pat.removeColumn(table_pat.getColumnModel().getColumn(8));
		table_pat.removeColumn(table_pat.getColumnModel().getColumn(7));
		
		JScrollPane med_scrollPane = new JScrollPane();
		med_scrollPane.setBounds(21, 208, 284, 119);
		contentPane.add(med_scrollPane);
		
		model_med = new DefaultTableModel();
		med_table = new JTable();
		med_table.addMouseListener(this);
		med_scrollPane.setViewportView(med_table);
		med_table.setModel(model_med);
		med_scrollPane.setViewportView(med_table);
		Object[] column_med = {
				"Time", "Medicine", "id"};
		Object[] row_med = new Object[0];
		model_med.setColumnIdentifiers(column_med);
		med_table.removeColumn(med_table.getColumnModel().getColumn(2));
		med_table.addMouseListener(this);
		
		

		


	}
	
	
	private void populatePatientSection() {
		int colCount;
		try {
			res = DoctorOperations.getPatients(docId);
	        ResultSetMetaData  patientData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	        
	        DefaultTableModel recordtable=(DefaultTableModel)table_pat.getModel();
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
                    columnData.add(res.getString("description"));
                    columnData.add(res.getString("status"));
                    columnData.add(res.getString("diagnosis"));
                    columnData.add(res.getString("prescription"));
                   

                    
                    
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
		if(e.getSource() == presBtn) {
			new AddPrescription(id).setVisible(true);
			refreshMed();
		}
		else if(e.getSource() == btnUpdate) {
			new UpdatePrescription(med_id, pres, med_time).setVisible(true);
			refreshMed();
		}
		
		else if(e.getSource()==btnRemove) {
			if(DoctorOperations.removePresctiption(med_id)) {
				JOptionPane.showMessageDialog(null, "Prescription removed Successfully");
			}
			else {
				JOptionPane.showMessageDialog(null, "Failed to remove prescription");
			}
			refreshMed();
		}
			
		else if(e.getSource() == diagBtn){
			String data = diagArea.getText();
			if(DoctorOperations.setDiagnosis(data, id)) { JOptionPane.showMessageDialog(null, "Diagnosis updated successsfully");
			refreshTable();	}
			else {
				 JOptionPane.showMessageDialog(null, "Failed to update Diagnosis");
			}
		}

		else if(e.getSource() == wardBtn) {
			new RequestAdmit(id, docId).setVisible(true);;
			
		}
		
		else if(e.getSource() == btnDates) {
			new KeyDateDoctor(id, name).setVisible(true);
			
		}
		
		else if(e.getSource()==labBtn) {
			Blob in = null;
			res = DoctorOperations.getReport(id);
			try {
				while(res.next()) {
					in = res.getBlob("report");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				OutputStream out = new FileOutputStream("out.pdf");
				out.write(in.getBytes(1l, (int) in.length()));
				out.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(); 
			}
			new ReportViewingForm().setVisible(true);
		}
	}
	
	public void populateMeds() {
		int colCount;
		try {
			res = DoctorOperations.getMedicines(id);
	        ResultSetMetaData  medData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = medData.getColumnCount();
	        
	        DefaultTableModel recordtable=(DefaultTableModel)med_table.getModel();
	        recordtable.setRowCount(0);
	                
	        while (res.next()) {
	                    
                Vector<String> columnData= new Vector<String>();

                for(int i=0;i<=colCount;i++) {
                	columnData.add(res.getString("time"));
                    columnData.add(res.getString("medicine"));
                    columnData.add(res.getString("id"));
            
                }
                recordtable.addRow(columnData);  
            }
	        
	                
		}
	    catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, ex);

	        
	        
	    }
		
	}
		
		

	public void refreshTable() {
		for(int i=model_pat.getRowCount()-1;i>=0;i--){
			model_pat.removeRow(i);
		    }
		populatePatientSection();
		}
	public void refreshMed() {
		for(int i=model_med.getRowCount()-1;i>=0;i--){
			model_med.removeRow(i);
		}
		populateMeds();
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table_pat) {
			id=  patientList.get(table_pat.getSelectedRow());
			int[] selRows = table_pat.getSelectedRows();
			TableModel recordtable= table_pat.getModel();
	        SelectedRow=table_pat.getSelectedRow();
	        diagArea.setText(recordtable.getValueAt(selRows[0],7).toString());
	        name = recordtable.getValueAt(selRows[0],0).toString() + " "+ recordtable.getValueAt(selRows[0],1).toString();
	        refreshMed();
	        populateMeds();
	        if(recordtable.getValueAt(selRows[0],6).toString().equals("Admitted")) {
	        	wardBtn.setEnabled(false);
	        }
	        else {
	        	wardBtn.setEnabled(true);
	        }
		}
		
		else if(e.getSource()==med_table) {
			int[] selRows = med_table.getSelectedRows();
			TableModel recordtable= med_table.getModel();
	        SelectedRow=med_table.getSelectedRow();
	        med_id = Integer.parseInt(recordtable.getValueAt(selRows[0],2).toString());
	        pres = recordtable.getValueAt(selRows[0],1).toString();
	        med_time = recordtable.getValueAt(selRows[0],0).toString();
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
