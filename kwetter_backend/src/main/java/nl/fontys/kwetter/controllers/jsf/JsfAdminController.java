package nl.fontys.kwetter.controllers.jsf;

import nl.fontys.kwetter.exceptions.CouldNotDeleteModelException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.service.IAdminService;
import nl.fontys.kwetter.service.IKwetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class JsfAdminController {

    private final IAdminService adminService;
    private final IKwetterService kwetterService;
    private Logger logger = LoggerFactory.getLogger(JsfAdminController.class);

    @Autowired
    public JsfAdminController(IAdminService adminService, IKwetterService kwetterService) {
        this.adminService = adminService;
        this.kwetterService = kwetterService;
    }

    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    public List<Role> getAllRoles() {
        return Arrays.asList(Role.values());
    }

    public List<Kwetter> getAllKwetters() {
        return adminService.getAllKwetters();
    }

    public void changeUserRole(User user) throws ModelNotFoundException, IOException {
        logger.info(String.format("Change role user: %s", user.toString()));
        adminService.changeRole(user.getCredentials().getEmail(), user.getCredentials().getRole());
        reload();
    }

    public void deleteKwetter(Kwetter kwetter) throws CouldNotDeleteModelException, ModelNotFoundException, IOException {
        logger.info(String.format("Deleted kwetter: %s", kwetter.toString()));
        kwetterService.removeKwetter(kwetter.getOwner().getId(), kwetter.getId());
        reload();
    }

    private void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}