package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import backend.AdminOperations;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddBed extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textBed;
	private JComboBox<ComboItems> comboWard;
	private JButton btnAdd;
	private ResultSet res;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBed frame = new AddBed();
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
	public AddBed() {
		setTitle("Bed Addition Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Add New Bed");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitle.setBounds(116, 10, 215, 44);
		contentPane.add(lblTitle);
		
		JLabel lblWname = new JLabel("Ward Name");
		lblWname.setHorizontalAlignment(SwingConstants.CENTER);
		lblWname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWname.setBounds(0, 86, 137, 26);
		contentPane.add(lblWname);
		
		comboWard = new JComboBox();
		comboWard.setBounds(179, 87, 202, 26);
		contentPane.add(comboWard);
		
		JLabel lblBedName = new JLabel("Bed Name");
		lblBedName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBedName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBedName.setBounds(0, 142, 137, 26);
		contentPane.add(lblBedName);
		
		textBed = new JTextField();
		textBed.setBounds(179, 147, 202, 32);
		contentPane.add(textBed);
		textBed.setColumns(10);
		
		btnAdd = new JButton("Add Bed");
		btnAdd.setBounds(193, 189, 143, 31);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(this);
		populateCombo();
	}
	
	private void populateCombo() {
		res = AdminOperations.getWards();
		try {
			while(res.next()) {
				comboWard.addItem(new ComboItems(res.getString(2), res.getInt(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd) {
			String bed = textBed.getText();
			Object item = comboWard.getSelectedItem();
			int id = ((ComboItems) item).getValue();
			
			if(bed.equals("")) {
				JOptionPane.showMessageDialog(null, "Bed name can;t be set empty!");
			}
			else {
				if(AdminOperations.addBed(id, bed)) {
					JOptionPane.showMessageDialog(null, "Bed Added successfully");
					this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Failed to add bed");
				}
			}
		}
		
	}
	
}
