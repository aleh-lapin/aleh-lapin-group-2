package by.pochem.web.auth;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import by.pochem.persistence.model.User;
import by.pochem.persistence.model.User.Role;

public class UserDetail extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserDetail(String name, User user) {
		super(name, user.getPassword(), user.isEnabled(), true, true, true, converRoles(user.getRole()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	private static List<GrantedAuthority> converRoles(Role role) {
		return Arrays.asList((GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.toString()));
	}
	
}
