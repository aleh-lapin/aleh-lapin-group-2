package by.pochem.web.binders;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import by.pochem.persistence.model.FilterGroup;

@Component
public class FilterGroupBinder extends PropertyEditorSupport {

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		FilterGroup group = null;
		if (StringUtils.hasText(arg0)) {
			group = new FilterGroup();
			group.setId(Long.parseLong(arg0));
		}
		setValue(group);
	}

	@Override
	public String getAsText() {
		FilterGroup group = (FilterGroup) getValue();
		if (group != null) {
			return Long.toString(group.getId());
		}
		return "";
	}
	
}
