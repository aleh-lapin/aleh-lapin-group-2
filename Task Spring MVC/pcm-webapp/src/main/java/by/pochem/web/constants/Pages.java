package by.pochem.web.constants;

import java.util.ArrayList;
import java.util.List;

public class Pages {

	public static class Page {
		
		private String name;
		private String url;
		private String label;
		
		public Page(String name, String url, String label) {
			this.name = name;
			this.url = url;
			this.label = label;
		}
		
		public String getName() {
			return name;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getLabel() {
			return label;
		}

		@Override
		public String toString() {
			return name;
		}
		
	}
	
	public static class Admin {
	
		public static final Page HOME = new Page("HOME", "/home", "Главная");
		public static final Page CATEGORIES = new Page("CATEGORIES", "/categories", "Категории");
		public static final Page COMPANIES = new Page("COMPANIES", "/companies", "Компании");
		public static final Page FILTERS = new Page("FILTERS", "/filters/groups", "Фильтры");
		public static final Page CITIES = new Page("CITIES", "/cities", "Города");
		public static final Page USERS = new Page("USER", "/users?role=COMPANY", "Пользователи");
		public static final Page NEWS = new Page("NEWS", "/news", "Новости");
		public static final Page STPAGES = new Page("STPAGES", "/stpages", "Страницы");
		public static final Page ADVERTISING = new Page("ADVERTISING", "/advertising", "Реклама");
		public static final Page PDSERVICES = new Page("PDSERVICES", "/pdservices", "Платные услуги");
		
		public static final List<Page> PAGES = new ArrayList<>();
		
		static {
			PAGES.add(Pages.Admin.HOME);
			PAGES.add(Pages.Admin.CATEGORIES);
			PAGES.add(Pages.Admin.COMPANIES);
			PAGES.add(Pages.Admin.FILTERS);
			PAGES.add(Pages.Admin.CITIES);
			PAGES.add(Pages.Admin.USERS);
			PAGES.add(Pages.Admin.NEWS);
			PAGES.add(Pages.Admin.STPAGES);
			PAGES.add(Pages.Admin.ADVERTISING);
			PAGES.add(Pages.Admin.PDSERVICES);
		}
		
	}
	
}
