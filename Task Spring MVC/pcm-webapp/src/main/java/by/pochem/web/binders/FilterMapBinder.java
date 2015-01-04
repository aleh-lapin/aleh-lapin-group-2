package by.pochem.web.binders;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import by.pochem.persistence.model.Filter;

@Component
public class FilterMapBinder extends PropertyEditorSupport{

	@Override
	public String getAsText() {
		return Long.toString(((Filter) getValue()).getId());
	}

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		Filter filter = null;
		if (StringUtils.hasText(arg0)) {
			filter = new Filter();
			filter.setId(Long.parseLong(arg0));
		}
		setValue(filter);
	}

	
	
}
