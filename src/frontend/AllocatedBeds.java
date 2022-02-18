package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;
import backend.DoctorOperations;

public class AllocatedBeds extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private ResultSet res;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllocatedBeds frame = new AllocatedBeds();
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
	public AllocatedBeds() {
		setTitle("Bed Allocation Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 562, 185);
		contentPane.add(scrollPane);
	
		
		table = new JTable();
		model = new DefaultTableModel();
		table.setModel(model);
		Object[] column ={ "First Name", "Last Name", "Ward", "Bed"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JLabel lblAllBeds = new JLabel("Allocated Beds");
		lblAllBeds.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAllBeds.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllBeds.setBounds(159, 10, 249, 26);
		contentPane.add(lblAllBeds);
		populateTable();
	}
	
	private void populateTable() {

		int colCount;

		try {
			res = AdminOperations.getAllocatedBeds();
	        ResultSetMetaData  patientData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	
	        DefaultTableModel recordtable=(DefaultTableModel)table.getModel();
	        recordtable.setRowCount(0);
	                
	        while (res.next()) {
	                    
                Vector<String> columnData= new Vector<String>();
                
                for(int i=0;i<=colCount;i++) {
                  columnData.add(res.getString("fname"));
                  columnData.add(res.getString("lname"));
                  columnData.add(res.getString("name"));
                  columnData.add(res.getString("bed"));

                }
                recordtable.addRow(columnData);  
            }
	        
	                
		}
	    catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, ex);
	        
	        
	        
	        
	    }
		
	}
}
