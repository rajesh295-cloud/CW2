package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import frontend.Admin_Panal;
import frontend.Doctor;
import frontend.Login;
import frontend.Nurse;

public class Authentication {
	
	
	  public static void login(String user, String psw) {
		  /*
		   * This function is used to allow users to login based on their user role
		   * Their role is automatially determined if their credintials matches the system
		   */
	    	PreparedStatement st;
			String query = "SELECT * FROM staff WHERE `uname` = ? AND `password` = ?";
			DbConnection connection = new DbConnection();
			try {
				st = DbConnection.conn.prepareStatement(query);
				st.setString(1, user);
				st.setString(2, psw);
				ResultSet rs = st.executeQuery();
				if(rs.next()) {
					String post = rs.getString(5);
					if(post.equals("admin")) {
						System.out.println("Admin");
						new Admin_Panal(rs.getInt(1)).setVisible(true);
					}
					else if(post.equals("doctor")) {
						String name = rs.getString(2)+" "+rs.getString(3);
						new Doctor(rs.getInt(1)).setVisible(true);
					}
					else if(post.equals("nurse")) {
						String name = rs.getString(2)+" "+rs.getString(3);
						new Nurse(rs.getInt(1),name).setVisible(true);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid Credentials!");
					new Login().setVisible(true);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return;
		}

}
