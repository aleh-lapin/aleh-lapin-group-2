package by.pochem.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.pochem.common.web.client.json.ClientSearchResponse;
import by.pochem.server.services.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ClientSearchResponse search(@RequestParam String searchQuery) {
		ClientSearchResponse response = null;
		if (searchQuery != null) {
			try {
				response = searchService.search(searchQuery);
			} catch (IOException e) {
			}
		}
		return response;
	}
	
}
