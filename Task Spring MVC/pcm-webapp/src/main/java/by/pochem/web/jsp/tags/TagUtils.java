package by.pochem.web.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public final class TagUtils {

	public static int getScope(String scope, int defaultScope)
			throws JspException {
		int pcscope;
		if (scope == null) {
			switch (defaultScope) {
			case 1:
			case 2:
			case 3:
			case 4:
				pcscope = defaultScope;
				break;
			default:
				throw new JspTagException("Invalid default scope: "  + defaultScope);
			}
		} else {
			if ("page".equals(scope)) {
				pcscope = 1;
			} else {
				if ("request".equals(scope)) {
					pcscope = 2;
				} else {
					if ("session".equals(scope))
						pcscope = 3;
					else if ("application".equals(scope))
						pcscope = 4;
					else
						throw new JspTagException("Invalid scope name: " + scope);
				}
			}
		}
		return pcscope;
	}
}
