package cs5500.plagiarism.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author team-211
 * @since 02.10.2018
 * This class is a controller class for the login module 
 * as shown in the UML class diagrams. 
 * With @RequestMapping we can specify end points to login page.
 */

@Controller
public class Register {
	private String fName;
	private String lName;
	private String emailId;
	
	/**
	 * Parsing emailId of user to decide the Role. 
	 * @northeastern.edu will be for Faculty.
	 * @husky will be given Student Role.
	 */
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.POST)
	public void decideRole() {
		
	}
	

}
