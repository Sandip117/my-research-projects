package cs5500.plagiarism.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * All security related tasks will be done here. 
 * Tasks include
 * 	1. Encoding the user password. 
 * 	2. Safe killing of sessions using HttpSecurity and WebSecurity modules
 * 	   in Spring. 
 */

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {


}