package by.pochem.web.auth;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import by.pochem.persistence.model.User;
import by.pochem.server.services.auth.AuthenticationService;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
		User user = authenticationService.loadUserByPhoneOrEmail(phoneOrEmail);
		if (user != null) {
			//A hack to identify how user is loging
			boolean isEmail = EmailValidator.getInstance().isValid(phoneOrEmail);
			return new UserDetail(isEmail ? user.getEmail(): user.getPhone(), user);
		}
		throw new UsernameNotFoundException("User with phone/email: " + phoneOrEmail + " is not found");
	}

}
