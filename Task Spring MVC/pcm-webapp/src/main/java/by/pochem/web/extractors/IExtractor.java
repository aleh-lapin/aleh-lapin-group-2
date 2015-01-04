package by.pochem.web.extractors;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public abstract interface IExtractor<T> {

	abstract T extract(MultipartHttpServletRequest mpr);
	
}
