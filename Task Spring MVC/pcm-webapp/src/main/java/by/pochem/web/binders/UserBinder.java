package by.pochem.web.binders;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import by.pochem.persistence.model.User;

@Component
public class UserBinder extends PropertyEditorSupport {

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		User user = null;
		if (StringUtils.hasText(arg0)) {
			user = new User();
			user.setId(Long.parseLong(arg0));
		}
		setValue(user);
	}
	
}
