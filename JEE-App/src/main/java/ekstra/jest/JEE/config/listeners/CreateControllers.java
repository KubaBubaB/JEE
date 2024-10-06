package ekstra.jest.JEE.config.listeners;

import ekstra.jest.JEE.controller.PersonController;
import ekstra.jest.JEE.service.PersonService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PersonService personService = (PersonService) event.getServletContext().getAttribute("personService");
        event.getServletContext().setAttribute("personController", new PersonController(personService));
    }
}
