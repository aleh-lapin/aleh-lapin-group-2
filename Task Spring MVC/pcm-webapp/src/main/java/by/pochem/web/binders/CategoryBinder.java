package by.pochem.web.binders;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import by.pochem.persistence.model.Category;

@Component
public class CategoryBinder extends PropertyEditorSupport {

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		Category category = null;
		if (StringUtils.hasText(arg0)) {
			category = new Category();
			category.setId(Long.parseLong(arg0));
		}
		setValue(category);
	}

	@Override
	public String getAsText() {
		Category category = (Category) getValue();
		if (category != null) {
			return Long.toString(category.getId());
		}
		return "";
	}
	
}
