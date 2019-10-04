package cs5500.plagiarism.model;
/**
 * @since 02.10.2018
 * @author team211
 * Admin class gets admin roles and privileges.
 * Certain privileges like add and delete user can be performed
 * only by the admin
 */
public class Admin extends Role {
	
	/**
	 * Function to add a new user. This will also trigger 
	 * functionality to add this user to database.
	 */
	public void addUser() {
		
	}
	/**
	 * Function to delete a  user. This will also trigger 
	 * functionality to delete this user to database.
	 */
	public void deleteUser() {
		
	}
	/**
	 * Authorize certain functionalities. 
	 */
	public void authorize() {
		
	}
}
