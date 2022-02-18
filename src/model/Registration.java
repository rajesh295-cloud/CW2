package model;

public class Registration {
	
	/*This class serves as an extra layer of protection for our data.
    All data which is required to be passed into database is firstly converted into private datatype and then
    it is passed as values. This ensures that middleman is not able to take a peak into the data when its travelling
    into our database.*/
	
	private String fname;
	private String lname;
	private String uname;
	private String post;
	private String password;
	public Registration(String fname, String lname, String uname, String post, String password){
		this.fname = fname;
		this.lname = lname;
		this.uname = uname;
		this.post = post;
		this.password = password;
	}

	
	// Getter
	  public String getFname() {
	    return fname;
	  }

	  // Setter
	  public void setFname(String fname) {
	    this.fname = fname;
	  }
	  
	  // Getter
	  public String getLname() {
		  return lname;
	  }
	  
	  // Setter
	  public void setLname(String lname) {
		  this.lname = lname;
	  }
	  
	  // Getter
	  public String getUname() {
		  return uname;
	  }
	  
	  // Setter
	  public void setUname(String uname) {
		  this.uname = uname;
	  }
	  
	  
	  // Getter
	  public String getPost() {
		  return post;
	  }
	  
	  // Setter
	  public void setPost(String post) {
		  this.post = post;
	  }
	  
	  // Getter
	  public String getPassword() {
		  return password;
	  }
	  
	  // Setter
	  public void setPassword(String password) {
		  this.password = password;
	  }

}
