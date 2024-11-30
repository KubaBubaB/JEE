package ekstra.jest.JEE.view.person;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;


@RequestScoped
@Named
@Log
public class PersonLogin {


    private final HttpServletRequest request;


    private final SecurityContext securityContext;


    private final FacesContext facesContext;


    @Inject
    public PersonLogin(
            HttpServletRequest request,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
            FacesContext facesContext
    ) {
        this.request = request;
        this.securityContext = securityContext;
        this.facesContext = facesContext;
    }


    @Getter
    @Setter
    private String login;



    @Getter
    @Setter
    private String password;

    @SneakyThrows
    public void loginAction() {
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(request, extractResponseFromFacesContext(),
                withParams().credential(credential));
        switch (status) {
            case SUCCESS:
                facesContext.getExternalContext().redirect("/JEE-App/index.xhtml");
                break;
            case SEND_CONTINUE:
                // The authentication mechanism requires the response to be completed.
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                break;
            case NOT_DONE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication not completed", null));
                break;
            default:
                throw new IllegalStateException("Unexpected AuthenticationStatus: " + status);
        };
    }

    private HttpServletResponse extractResponseFromFacesContext() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }
}
