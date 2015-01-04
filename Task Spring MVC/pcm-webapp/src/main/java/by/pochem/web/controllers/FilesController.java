package by.pochem.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import by.pochem.common.web.forms.UploadFileForm;
import by.pochem.persistence.model.Image;
import by.pochem.server.services.web.FilesService;
import by.pochem.web.auth.authorize.AuthorizeRequest;
import by.pochem.web.extractors.Extractors;

@Controller
@RequestMapping(value = "/files")
public class FilesController {

	@Autowired
	private FilesService filesService;

	@AuthorizeRequest("hasRole('ROLE_ADMIN') or aclService.canModifyFiles(#request, principal)")
	@RequestMapping(value = "/images/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Image uploadImage(MultipartHttpServletRequest request) {
		UploadFileForm uploadFileForm = (UploadFileForm) Extractors.FILE_EXT.extract(request);
		Image image = filesService.uploadFile(uploadFileForm);
		return image;
	}

	@AuthorizeRequest("hasRole('ROLE_ADMIN') or aclService.canModifyFiles(#id, principal)")
	@RequestMapping(value = "/images/delete", method = RequestMethod.POST)
	public @ResponseBody
	boolean deleteImage(@RequestParam("id") Long id) {
		filesService.deleteFile(id);
		return true;
	}
	
	
}
