package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NurseOperations {
	/*
	 * This class contains all database operations required from Nurse pannel
	 * Firstly all required variables are declared.
	 * Then they are initialized as required
	 */
	static PreparedStatement st;
	static String query;
	static ResultSet res;
	DbConnection connection = new DbConnection();
	
	public static ResultSet getPatients(int staff_id) {
		/*
		 * This function takes staff id as argument and return all patients assigned to that staff(nurse)
		 */
		query = "select * from patient where nurse=?" ;
	
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, staff_id);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static ResultSet getWardDetails(int pat_id) {
		/*
		 * This function takes patient id as argument and return ward detail fro that patient
		 */
		query = "select distinct wd.name as ward_name, bd.bed as bed_name from admit_request ad, ward wd, bed as bd, patient as pt where bd.patient=? and wd.idward = ad.ward;" ;
		
		try {
			st = DbConnection.conn.prepareStatement(query);
			st.setInt(1, pat_id);
			res = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static ResultSet getMedicines(int patId) {
		/*
		 * This function takes patient id as argument and return medication details for that patient
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

}
