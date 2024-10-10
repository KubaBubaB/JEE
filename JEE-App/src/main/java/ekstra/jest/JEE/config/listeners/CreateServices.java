package ekstra.jest.JEE.config.listeners;

import ekstra.jest.JEE.component.PersonInMemRepository;
import ekstra.jest.JEE.component.PseudoDatabase;
import ekstra.jest.JEE.interfaces.PersonRepository;
import ekstra.jest.JEE.service.PersonService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.nio.file.Path;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PseudoDatabase pseudoDatabase = (PseudoDatabase) event.getServletContext().getAttribute("pseudoDatabase");

        PersonRepository personRepository = new PersonInMemRepository(pseudoDatabase);
        Path photoDirectory = Path.of("C:\\StudiaHere\\TEMP");

        event.getServletContext().setAttribute("personService", new PersonService(personRepository, photoDirectory));

    }
}
