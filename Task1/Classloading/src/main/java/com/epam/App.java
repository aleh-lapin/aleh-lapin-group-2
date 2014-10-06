package com.epam;

import com.epam.menu.MainMenu;
import org.apache.log4j.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class);

    public static void main(String[] args) {
    	LOG.info("App started");
    	MainMenu menu = new MainMenu();
    	menu.start();
    	LOG.info("App closed");
    }
    
}
