package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminOperations {
	/*
	 * This class contains all database operations required from admin pannel
	 * Firstly all required variables are declared.
	 * Then they are initialized as required
	 */
	static PreparedStatement st;
	static String query;
	static ResultSet res;
	DbConnection connection = new DbConnection(); //sets up connection to database

	public static boolean updatePatient(String up_fname,String up_lname,String up_Dob,String up_admit_date,String up_med_history,int up_doc,int up_nurse,String up_desp, int id) {
		/*
		 * This function takes all required details as parameters and update it inti database
		 * returns true if succcessfully updated
		 * Else returns false
		 */
		query= "UPDATE `patient` SET fname=?, lname=?, dob=?, admit_date=?, medical_history=?, doctor=?, nurse=?, description=? WHERE id=?";
	
		try {
			st = DbConnection.conn.prepareStatement(query);
	        st.setString(1, up_fname);
	        st.setString(2, up_lname);
	        st.setString(3, up_Dob);
	        st.setString(4, up_admit_date);
	        st.setString(5, up_med_history);
	        st.setInt(6, up_doc);
	        st.setInt(7, up_nurse);
	        st.setString(8, up_desp);
	        st.setInt(9, id);
	        if(st.executeUpdate()>0) {return true;}
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ResultSet getDoctors() {
		/*
		 * This method fetches all doctors from staff table and returns it as ResultSet Object
		 */
		query = "SELECT * FROM staff where `post` = 'doctor' ";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static ResultSet getNurses() {
		/*
		 * This method fetches all nurses from staff table and returns it as ResultSet Object
		 */
		query = "SELECT * FROM staff where `post` = 'nurse' ";
		try {
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static ResultSet getPatients() {
		/*
		 * This method fetches all patients from patient table and returns it as ResultSet Object
		 */
		query = "select * from patient" ;
		try {
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static boolean register(String fname, String lname, String uname, String post, String password) {
		/*
		 * This function takes all required details as parameters and registers it into database
		 * returns true if succcessfully registered
		 * Else returns false
		 */
		query = "INSERT INTO `staff`(`fname`,`lname`,`uname`,`post`,`password`) VALUES(?, ?, ?, ?, ?)"; 
		
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, fname);
			st.setString(2, lname);
			st.setString(3, uname);
			st.setString(4, post);
			st.setString(5, password);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public static boolean addPatient(String fname,String lname,String history,int assignedDoctor,int assignedNurse,String desc,String dob,String dateAdmitted) {
		/*
		 * This function takes all required details as parameters and insert it into patient table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query = "INSERT INTO `patient`(`fname`,`lname`,`medical_history`,`description`,`doctor`,`nurse`,`dob`,`admit_date`) VALUES(?, ?, ?, ?, ?,?,?,?);";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, fname);
			st.setString(2, lname);
			st.setString(3, history);
			st.setString(4, desc);
			st.setInt(5, assignedDoctor);
			st.setInt(6, assignedNurse);
			st.setString(7, dob);
			st.setString(8, dateAdmitted);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ResultSet getRequests() {
		/*
		 * This method fetches all requests from database along with other custom collumns from multiple
		 *  tables and returns it as ResultSet Object
		 */
		query = "select idadmit_request, p.fname as pname, wd.name as ward,wd.idward as wid, s.fname as doc_name, ad.bed as bed, p.id as pid  from admit_request ad, patient p, ward wd, staff s WHERE p.status = 'Pending' AND ad.patient = p.id AND ad.requested_by = s.idstaff AND ad.ward = wd.idward" ;
		try {
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static boolean uploadReport(int id, byte[] data) {
		/*
		 * This function takes patient id and byte array as parameters and insert it into lab table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query = "INSERT INTO `lab`(`patient`,`report`) VALUES(?, ?);";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, id);
			st.setBytes(2, data);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static ResultSet getWards() {
		/*
		 * This method fetches all wards from ward table and returns it as ResultSet Object
		 */
		query = "SELECT * FROM ward";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static boolean addBed(int ward, String name) {
		/*
		 * This function takesward id and bed name as parameters and insert it into ward table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query = "INSERT INTO `bed`(`ward`,`bed`) VALUES(?, ?)"; 
		
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, ward);
			st.setString(2, name);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public static ResultSet getAvaliableBeds(int id) {
		/*
		 * This method fetches all availabe beds from bed table which is in certain ward(id)
		 */
		query = "SELECT * FROM bed where ward=? and status=?";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, id);
			st.setString(2, "Avaliable");
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	public static ResultSet getAllocatedBeds() {
		/*
		 * This method custom fields from multiple tables
		 * Basically it returns all allocated beds
		 */
		query = "select pt.fname, pt.lname, wd.name, bd.bed from ward as wd, patient as pt, bed as bd where bd.patient is not null and pt.id = bd.patient and bd.ward = wd.idward;";
		try {
			DbConnection connection = new DbConnection();
			st = DbConnection.conn.prepareStatement(query);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static boolean admitPatient(int bed, int admit_id, int userID) {
		/*
		 * This method updates 3 tables after admitting a patient
		 * Firstly data in admit_request table is inserted
		 * Then status of patient in patient table is updated to admitted
		 * Then query for updating bed status for selected bed is ran
		 * 
		 */
		 String query = "UPDATE `admit_request` SET bed=? WHERE idadmit_request=?";
         String queryAdmit= "UPDATE `patient` SET status='Admitted' WHERE id=?";
         String queryDone= "UPDATE `bed` SET status='Allocated', patient=? WHERE idbed=?";
         
         try {
 			DbConnection connection = new DbConnection();
 			st = DbConnection.conn.prepareStatement(query);
 			st.setInt(1, bed);
	        st.setInt(2, admit_id);
	        st.executeUpdate();
	        
	        st = DbConnection.conn.prepareStatement(queryAdmit);
 			st.setInt(1, userID);
	        st.executeUpdate();
	        
	        st = DbConnection.conn.prepareStatement(queryDone);
	        st.setInt(1, userID);
 			st.setInt(2, bed);
 			if(st.executeUpdate()>0) {
 				return true;
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
        return false;

	}



}
