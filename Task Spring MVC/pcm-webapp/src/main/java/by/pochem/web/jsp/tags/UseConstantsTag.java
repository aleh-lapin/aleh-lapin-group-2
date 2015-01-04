package by.pochem.web.jsp.tags;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class UseConstantsTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String className;
	private String scope;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int doStartTag() throws JspException {
		int scope = TagUtils.getScope(this.scope, 4);
		if ((this.className != null)) {
			Map<String, Object> constants = null;
			try {
				constants = ClassUtils.getClassConstants(this.className);
			} catch (ClassNotFoundException e) {
				throw new JspTagException("Class not found: " + this.className);
			} catch (IllegalArgumentException e) {
				throw new JspTagException("Illegal argument: " + this.className);
			} catch (IllegalAccessException e) {
				throw new JspTagException("Illegal access: " + this.className);
			}
			if ((constants != null) && (!constants.isEmpty())) {
				for (Entry<String, Object> constant : constants.entrySet()) {
					pageContext.setAttribute(constant.getKey(), constant.getValue(), scope);
				}
			}
		}
		return 0;
	}

	public void release() {
		super.release();
		className = null;
		scope = null;
	}
}
