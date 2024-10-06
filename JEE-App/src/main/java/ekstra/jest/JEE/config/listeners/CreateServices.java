package ekstra.jest.JEE.config.listeners;

import ekstra.jest.JEE.component.PersonInMemRepository;
import ekstra.jest.JEE.component.PseudoDatabase;
import ekstra.jest.JEE.interfaces.PersonRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PseudoDatabase pseudoDatabase = (PseudoDatabase) event.getServletContext().getAttribute("pseudoDatabase");

        PersonRepository personRepository = new PersonInMemRepository();

        event.getServletContext().setAttribute("personRepository", personRepository);
    }
}
