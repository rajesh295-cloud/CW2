package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import backend.AdminOperations;
import backend.AdminOperations;
import backend.DbConnection;
import model.Registration;

public class Register extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField fnameField;
	private JLabel lblLastName;
	private JTextField lnameField;
	private JLabel lblUserName;
	private JTextField unameField;
	private JLabel lblEmailAddress;
	private JTextField emailField;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JButton btnRegister;
	private JButton btnCancle;
	private JPasswordField passwordField_2;
	private Registration model;
	JRadioButton doctor;
	JRadioButton admin;
	JRadioButton labAdmin;
	JRadioButton nurse;
	String post;
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Register() {
		setTitle("Registration Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Registration Page");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(237, 10, 232, 36);
		contentPane.add(titleLabel);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		firstNameLabel.setBounds(105, 68, 174, 36);
		contentPane.add(firstNameLabel);
		
		fnameField = new JTextField();
		fnameField.setBounds(279, 75, 243, 26);
		contentPane.add(fnameField);
		fnameField.setColumns(10);
		
		lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLastName.setBounds(105, 114, 174, 36);
		contentPane.add(lblLastName);
		
		lnameField = new JTextField();
		lnameField.setColumns(10);
		lnameField.setBounds(279, 121, 243, 26);
		contentPane.add(lnameField);
		
		lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUserName.setBounds(105, 160, 174, 36);
		contentPane.add(lblUserName);
		
		unameField = new JTextField();
		unameField.setColumns(10);
		unameField.setBounds(279, 167, 243, 26);
		contentPane.add(unameField);
		
		lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmailAddress.setBounds(105, 217, 174, 36);
		contentPane.add(lblEmailAddress);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(279, 224, 243, 26);
		contentPane.add(emailField);
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(105, 263, 174, 36);
		contentPane.add(lblPassword);
		
		btnCancle= new JButton("Cancle");
		btnCancle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancle.setBounds(160, 433, 140, 44);
		contentPane.add(btnCancle);
		btnCancle.addActionListener(this);
		
		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegister.setBounds(379, 433, 140, 44);
		contentPane.add(btnRegister);
		btnRegister.addActionListener(this);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(279, 270, 250, 26);
		contentPane.add(passwordField);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblConfirmPassword.setBounds(98, 309, 174, 36);
		contentPane.add(lblConfirmPassword);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(279, 319, 250, 26);
		contentPane.add(passwordField_2);
		
		JLabel lblDesigination = new JLabel("Desigination");
		lblDesigination.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesigination.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDesigination.setBounds(119, 368, 174, 36);
		contentPane.add(lblDesigination);
		
		admin = new JRadioButton("Hospital Admin");
		admin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		admin.setBounds(279, 368, 130, 26);
		contentPane.add(admin);
		
		doctor = new JRadioButton("Doctor");
		doctor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		doctor.setBounds(279, 404, 130, 26);
		contentPane.add(doctor);
		

		
		labAdmin = new JRadioButton("Lab Admin");
		labAdmin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		labAdmin.setBounds(418, 368, 130, 26);
		contentPane.add(labAdmin);
		contentPane.add(labAdmin);
		
		nurse = new JRadioButton("Nurse");
		nurse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nurse.setBounds(418, 404, 130, 26);
		contentPane.add(nurse);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(nurse);
		bg.add(admin);
		bg.add(labAdmin);
		bg.add(doctor);
		
		emailField.setName("email");
		passwordField.setName("password");
        
	}
	

 
    public String validate(String id, String username, String password1, String password2) {
        String validation = "true";
        int id_len = id.length();
        int un_len = username.length();
        int psw_len = password1.length();
        if (id_len == 0 || un_len == 0 || psw_len == 0) {
            validation = "All Fields are required";
        } else if(un_len<=3) {
            validation = "Username should be atleast contain 4 characters";
        } else if (psw_len<=7) {
            validation = "Password too short. Must contain atleast 8 character";
        } else if (!password1.equals(password2)) {
            validation = "Password does not match";
        }
        return validation;
    }
    
    
    
    public boolean valid(String fname, String lname, String uname, String password) {
    	if(fname.equals("")) {JOptionPane.showMessageDialog(this, "First Name can not be set Empty");return false;}
    	if(lname.equals("")) {JOptionPane.showMessageDialog(this, "Last Name can not be set Empty");return false;}
    	if(uname.equals("")) {JOptionPane.showMessageDialog(this, "Last Name can not be set Empty");return false;}
    	if(password.equals("")) {JOptionPane.showMessageDialog(this, "Password can not be set Empty");return false;}
    	return true;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnRegister) {
			if(admin.isSelected()) {
				post = "admin";
			}
			else if(doctor.isSelected()) {
				post="doctor";
			}
			else if(nurse.isSelected()) {
				post="nurse";
			}
			else if(labAdmin.isSelected()) {
				post="labadmin";
			}
			else {
				JOptionPane.showMessageDialog(this, "Designation Field blank");
			}
			
			model = new Registration(fnameField.getText(), lnameField.getText(), unameField.getText(), post, String.valueOf(passwordField.getPassword()));
			String fname = model.getFname();
			String lname = model.getLname();
			String uname = model.getUname();
			String password= model.getPassword();
			
			
			if(valid(fname,lname,uname,password)) {
				if(AdminOperations.register(fname, lname, uname, uname, password)) {
					JOptionPane.showMessageDialog(null, "New Staff Added");
					this.dispose();
					new Login().setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Staff Registration Failed!");
				}
			}
            
        };
        
        if(e.getSource() == btnCancle) {
        	this.dispose();
        }
		
	}
	
	private void setEmail(String data) {
		this.emailField.setText(data);
	}
	private String getEmail() {
		return emailField.getText();
	}
	
	private void setPassword(String data) {
		this.passwordField.setText(data);
	}
	private String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
	
	
}
