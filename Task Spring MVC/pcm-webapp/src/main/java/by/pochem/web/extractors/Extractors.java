package by.pochem.web.extractors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import by.pochem.common.web.forms.CatalogItemForm;
import by.pochem.common.web.forms.UploadFileForm;

@SuppressWarnings("rawtypes")
public enum Extractors {

	FILE_EXT(new IExtractor<UploadFileForm> () {
		
		@Override
		public UploadFileForm extract(MultipartHttpServletRequest mpr) {
			String entityId = mpr.getParameter("entityId");
			String fileType = mpr.getParameter("fileType");
			MultipartFile file = mpr.getFile(mpr.getFileNames().next());
			return new UploadFileForm(entityId != null ? Long.parseLong(entityId) : null, fileType, file);
		}
	}),
	
	PRODUCT_EXT(new IExtractor<CatalogItemForm> () {

		@Override
		public CatalogItemForm extract(MultipartHttpServletRequest mpr) {
			long id = Long.parseLong(mpr.getParameter("product_id"));
			String name = mpr.getParameter("product_name");
			long price = Long.parseLong(mpr.getParameter("product_price").replaceAll(" ", ""));
			String description = mpr.getParameter("product_descr");
			long nodeId = Long.parseLong(mpr.getParameter("category_id"));
			String fileType = mpr.getParameter("fileType");
			
			CatalogItemForm catalgoItemForm = new CatalogItemForm(id, name, price, description, nodeId);
			List<UploadFileForm> images = new ArrayList<>();
			Iterator<String> fileNames = mpr.getFileNames();
			while (fileNames.hasNext()) {
				List<MultipartFile> files = mpr.getFiles(fileNames.next());
				for (MultipartFile file : files) {
					images.add(new UploadFileForm(id, fileType, file));
				}
			}
			catalgoItemForm.setImages(images);
			
			String mainImageId = mpr.getParameter("main_image_id");
			if (StringUtils.hasText(mainImageId)) {
				catalgoItemForm.setMainImageId(Long.parseLong(mainImageId));
			}
			
			return catalgoItemForm;
		}
		
	});
	
	private IExtractor iExtractor;
	
	private Extractors(IExtractor iExtractor) {
		this.iExtractor = iExtractor;
	}
	
	public Object extract(MultipartHttpServletRequest mpr) {
		return iExtractor.extract(mpr);
	}
	
}
