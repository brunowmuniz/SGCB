package br.com.casabemestilo.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import br.com.casabemestilo.model.Usuario;


public class LoginPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event) {
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Usuario user = (Usuario) session.getAttribute("UsuarioLogado");
        String pagina = facesContext.getViewRoot().getViewId();
        
         // Verifica se a requisicao eh para a pagina de login
        boolean loginPage = pagina.lastIndexOf("index") > -1 ? true : false;
        if (!loginPage && (user == null || user.getNome().length() == 0)) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "index");
        }
    }
}