package de.htwBerlin.ai.kbe.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // brauchen wir nicht
    	System.out.println("Server Started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // ManagerFactory schliessen um max_connection error zu vermeiden
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("songsDB-PU");
    	factory.close();
    }
}
