package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.AdminOperations;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class ReportUploadFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnAddReport;
	private JTextField fieldName;
	private JTextField textReport;
	private JFileChooser chooser = new JFileChooser();
	private String newPath;
	private File pdfFile;
	private JButton btnUploadReport;
	private static int id;
	private static String name;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportUploadFrame frame = new ReportUploadFrame(id,name);
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
	public ReportUploadFrame(int id, String name) {
		setTitle("Report Upload Page");
		this.id = id;
		this.name = name;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Upload Lab Report ");
		lblNewLabel.setBounds(124, 10, 171, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel lblPatient = new JLabel("Patient");
		lblPatient.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPatient.setBounds(10, 77, 144, 31);
		contentPane.add(lblPatient);
		
		fieldName = new JTextField();
		fieldName.setBounds(135, 79, 260, 31);
		contentPane.add(fieldName);
		fieldName.setColumns(10);
		fieldName.setText(name);
		fieldName.setEditable(false);
		
		textReport = new JTextField();
		textReport.setColumns(10);
		textReport.setBounds(135, 134, 134, 31);
		contentPane.add(textReport);
		textReport.setEditable(false);
		
		JLabel lblReport = new JLabel("Lab Report");
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReport.setBounds(10, 132, 144, 31);
		contentPane.add(lblReport);
		
		btnAddReport = new JButton("Add report");
		btnAddReport.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddReport.setBounds(279, 134, 119, 31);
		contentPane.add(btnAddReport);
		btnAddReport.addActionListener(this);
		
		btnUploadReport = new JButton("Upload Report");
		btnUploadReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUploadReport.setBounds(135, 200, 160, 41);
		contentPane.add(btnUploadReport);
		btnUploadReport.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String path;
		if (e.getSource()==btnAddReport) {
			chooser.showOpenDialog(null);
			pdfFile = chooser.getSelectedFile();
			String filename = pdfFile.getAbsolutePath();
			path = filename;
			newPath = path.replace('\\', '/');
			textReport.setText(newPath);
		}
		
		else if(e.getSource()==btnUploadReport) {
			byte[] pdfData = new byte[(int) pdfFile.length()];
			try {
				DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
				dis.readFully(pdfData);  // read from file into byte[] array
				dis.close();
				if(AdminOperations.uploadReport(id, pdfData)) {
					JOptionPane.showMessageDialog(this, "Report Uploaded Successfully");
				}
				else {
					JOptionPane.showMessageDialog(this, "Failed to upload report!");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
	}
}
