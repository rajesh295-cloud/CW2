package frontend;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.DoctorOperations;
import backend.NurseOperations;

public class Prescription extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private ResultSet res;
	private JButton btnExit;
	private static int id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prescription frame = new Prescription(id);
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
	public Prescription(int id) {
		setTitle("Prescription Page");
		this.id=id;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Patient's Medicines");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitle.setBounds(112, 10, 214, 37);
		contentPane.add(lblTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 416, 142);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Time", "Medicine"
			}
		));
		scrollPane.setViewportView(table);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(127, 220, 181, 43);
		contentPane.add(btnExit);
		btnExit.addActionListener(this);
		populateMeds();
	}
	
	
	
	
	public void populateMeds() {
		int colCount;
		Vector<String> columnData;
		try {
			res = NurseOperations.getMedicines(id);
	        ResultSetMetaData  medData= (ResultSetMetaData) res.getMetaData();
	        
	        colCount = medData.getColumnCount();
	        DefaultTableModel recordtable=(DefaultTableModel)table.getModel();
	        while (res.next()) {
                columnData= new Vector<String>();

                for(int i=0;i<=colCount;i++) {
                	columnData.add(res.getString("time"));
                    columnData.add(res.getString("medicine"));
            
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
}
