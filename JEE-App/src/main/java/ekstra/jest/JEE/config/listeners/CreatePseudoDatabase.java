package ekstra.jest.JEE.config.listeners;

import ekstra.jest.JEE.component.PseudoDatabase;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreatePseudoDatabase implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("pseudoDatabase", PseudoDatabase.getInstance());
    }
}
