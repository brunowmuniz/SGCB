package br.com.casabemestilo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.casabemestilo.control.PermissaoControl;
import br.com.casabemestilo.model.Pagina;
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
        List<String> nomePaginasPermissao = (ArrayList<String>) session.getAttribute("nomePaginasPermissao");
        String pagina = facesContext.getViewRoot().getViewId().replace("/content/", "");
        NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
        String[] paginasSemLogin;
        Properties properties = new Properties();
        InputStream in = this.getClass().getResourceAsStream("sgcb.properties");
        boolean ehPaginasSemLogin = false;
        int i = 1;
        
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Faz um split com as páginas sem login
        paginasSemLogin = properties.getProperty("pagina").split(",");

        // Loop para identificar se a página é uma das sem login
        while (paginasSemLogin.length >= i && !ehPaginasSemLogin) {
            try {
                ehPaginasSemLogin = facesContext.getViewRoot().getViewId().lastIndexOf(paginasSemLogin[i - 1].toString()) > -1 ? true : false;
            } catch (Exception e) {
            	e.printStackTrace();
                break;
            } finally {
                i++;
            }
        }
        
        
        if (!ehPaginasSemLogin && (user == null || user.getNome().length() == 0)) {            
            nh.handleNavigation(facesContext, null, "index");        	
    	}else{
    		if(!ehPaginasSemLogin && !nomePaginasPermissao.contains(pagina)){
           		nh.handleNavigation(facesContext, null, "sempermissao");
    		}
    		
    	}
               
    }
}