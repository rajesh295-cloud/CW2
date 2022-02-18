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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;
import backend.DbConnection;


public class AdmitPatient extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTextField patientName;
	private JTextField wardName;
	private JComboBox<ComboItems> bedBox;
	private JTable table;
	private int row;
	private int user;
	private int admit_id;
	private DefaultTableModel model;
	private JButton btnAllocate;
	private ArrayList<Integer> patientList = new ArrayList<>();
	private int wardId;
	private ResultSet res;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdmitPatient frame = new AdmitPatient();
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
	public AdmitPatient() {
		setTitle("Admit Patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1167, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Hospital Admit Requests");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(497, 20, 376, 41);
		contentPane.add(titleLabel);
		
		JLabel userLabel = new JLabel("Patient");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userLabel.setBounds(18, 104, 150, 30);
		contentPane.add(userLabel);
		
		patientName = new JTextField();
		patientName.setBounds(178, 107, 223, 30);
		contentPane.add(patientName);
		patientName.setColumns(10);
		patientName.setEnabled(false);
		
		JLabel wardLabel = new JLabel("Ward");
		wardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wardLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		wardLabel.setBounds(25, 168, 150, 30);
		contentPane.add(wardLabel);
		
		wardName = new JTextField();
		wardName.setColumns(10);
		wardName.setBounds(178, 171, 223, 30);
		contentPane.add(wardName);
		wardName.setEnabled(false);
		
		JLabel bedLabel = new JLabel("Bed");
		bedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bedLabel.setBounds(25, 235, 150, 30);
		contentPane.add(bedLabel);
		
		bedBox = new JComboBox<ComboItems>();
		bedBox.setBounds(178, 238, 223, 30);
		contentPane.add(bedBox);
		
		btnAllocate = new JButton("Allocate Bed");
		btnAllocate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAllocate.setBounds(179, 290, 207, 41);
		contentPane.add(btnAllocate);
		btnAllocate.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(500, 114, 624, 219);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		table.setModel(model);
		Object[] column ={ "Patient", "Ward",  "Requested By", "Bed", "Pid","wid","aid"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
	
		scrollPane.setViewportView(table);
		populateRequests();
		table.removeColumn(table.getColumnModel().getColumn(6));
		table.removeColumn(table.getColumnModel().getColumn(5));
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.addMouseListener(this);
	}
	
	
	private void populateRequests() {
		PreparedStatement st;
		int colCount;
		DbConnection connection = new DbConnection();
		try {
	         ResultSet rs = AdminOperations.getRequests();
	        ResultSetMetaData  patientData= (ResultSetMetaData) rs.getMetaData();
	        
	        colCount = patientData.getColumnCount();
	        
	        DefaultTableModel recordtable=(DefaultTableModel)table.getModel();
	        recordtable.setRowCount(0);
	                
	        while (rs.next()) {
	                    
                Vector<String> columnData= new Vector<String>();
                patientList.add(rs.getInt("idadmit_request"));
                
                for(int i=0;i<=colCount;i++) {
                    columnData.add(rs.getString("pname"));
                    columnData.add(rs.getString("ward"));
                    columnData.add(rs.getString("doc_name"));
                    columnData.add(rs.getString("bed"));
                    columnData.add(rs.getString("pid"));
                    columnData.add(rs.getString("wid"));
                    columnData.add(rs.getString("idadmit_request"));
                   
  
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
		DefaultTableModel recordtable= (DefaultTableModel)table.getModel();
        row=table.getSelectedRow();
        patientName.setText(recordtable.getValueAt(row,0).toString());
        wardName.setText(recordtable.getValueAt(row,1).toString());
        user = Integer.parseInt(recordtable.getValueAt(row,4).toString());
        wardId = Integer.parseInt(recordtable.getValueAt(row, 5).toString());
        admit_id = Integer.parseInt(recordtable.getValueAt(row, 6).toString());
        
        bedBox.removeAll();
        populateBeds();
		
	}
	
	private void populateBeds() {
		res = AdminOperations.getAvaliableBeds(wardId);
		try {
			while(res.next()) {
				bedBox.addItem(new ComboItems(res.getString(3), res.getInt(1)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if (e.getSource() == btnAllocate) {
			
			Object item = bedBox.getSelectedItem();
			int id = ((ComboItems) item).getValue();

			if (AdminOperations.admitPatient(id, admit_id, user)) {
				JOptionPane.showMessageDialog(null, "Patient Admitted Successfully! ");
			}
			else{
				JOptionPane.showMessageDialog(null, "Failed to Add message!");
			}


	    }
	}
		
	public void refreshTable() {
		for(int i=table.getRowCount()-1;i>=0;i--){
			model.removeRow(i);
		    }
		populateRequests();
		}
	
	
}
