package cs5500.plagiarism.schema;

import cs5500.plagiarism.model.Role;
/**
 * @since 02.10.2018
 * @author team211
 * Operations that can be performed on Role table. 
 */
public interface RoleSchema {
	Role findByRole(String role);

}
