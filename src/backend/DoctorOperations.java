package backend;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorOperations {
	static PreparedStatement st;
	static String query;
	static ResultSet res;
	DbConnection connection = new DbConnection();
	/*
	 * This class contains all database operations required from admin pannel
	 * Firstly all required variables are declared.
	 * Then they are initialized as required
	 */
	public static boolean setPrescription(String data, String time, int id) {
		/*
		 * This function takes all required details as parameters and insert it into prescription table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query= "INSERT INTO `prescription`(medicine, time, patient) VALUES(?,?,?)";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, data);
			st.setString(2, time);
	        st.setInt(3, id);
	        if(st.executeUpdate()>0) {
	        	return true;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean setDiagnosis(String data, int id) {
		/*
		 * This function takes diagnosis detail and id as parameters and insert it into patient table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query= "UPDATE `patient` SET diagnosis=? WHERE id=?";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, data);
			st.setInt(2, id);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ResultSet getPatients(int docId) {
		/*
		 * This method fetches all patients from patients table whose doctor matches with doc id and returns it as ResultSet Object
		 */
		query = "select * from patient where doctor=?" ;
		
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, docId);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static ResultSet getDates(int patId) {
		/*
		 * This method fetches all dates from key_treatment_date table whose id matches with patient id and returns it as ResultSet Object
		 */
		query = "select * from key_treatment_date where patient=?" ;
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, patId);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static boolean setTreatment(String data, int id) {
		/*
		 * This function takes diagnosis name and id as parameters and insert it into key_treatment_date table
		 * returns true if succcessfully inserted
		 * Else returns false
		 */
		query= "Insert into `key_treatment_date`(`Treatment`,`patient`) VALUES(?, ?)";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, data);
	        st.setInt(2, id);
	        if(st.executeUpdate()>0) {
	        	return true;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public static boolean removeTreatment(int id) {
		/*
		 * This function takes diagnosis id as parameter and removes it from key_treatment_date table
		 * returns true if succcessfully removed
		 * Else returns false
		 */
		query= "delete from key_treatment_date where idkey_treatment_date = ? ";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, id);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateTreatment(String data, int treatId, String date, String time, String lab) {
		/*
		 * This function takes required arguments  and update key_treatment_date table
		 * returns true if succcessfully updated
		 * Else returns false
		 */
		query= "UPDATE `key_treatment_date` SET Date=?, Treatment=?, lab=?, Time=? WHERE idkey_treatment_date=?";
		
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, time);
			st.setString(2, data);
			st.setString(3, lab);
			st.setString(4, date);
	        st.setInt(5, treatId);
	        if(st.executeUpdate()>0) {
	        	return true;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ResultSet getMedicines(int patId) {
		/*
		 * This function takes patient id as argument and update prescription table
		 * returns resultset 
		 * 
		 */
		query = "select * from prescription where patient=?" ;
		
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, patId);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static boolean updatePresctiption(String data,String time, int id) {
		/*
		 * This function takes required arguments  and update prescription table
		 * returns true if succcessfully updated
		 * Else returns false
		 */
		query= "UPDATE `prescription` SET medicine=?, time=? WHERE id=?";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setString(1, data);
			st.setString(2, time);
			st.setInt(3, id);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean removePresctiption(int id) {
		/*
		 * This function takes patient id as argument and remove it from prescription table
		 * returns true if succcessfully removed
		 * Else returns false
		 */
		query= "delete from `prescription` WHERE id=?";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, id);
			if(st.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ResultSet getReport(int id) {
		/*
		 * This function takes id as argument and update prescription table
		 * returns Resultset if found
		 * Else returns Null
		 */
		query = "select report from lab where patient=?";
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, id);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	}
	

