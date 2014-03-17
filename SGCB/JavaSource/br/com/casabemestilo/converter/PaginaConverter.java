package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.PaginaDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.model.Pagina;
import br.com.casabemestilo.model.Usuario;

@FacesConverter(forClass=Pagina.class)
public class PaginaConverter implements Converter {

	public PaginaConverter() {
		
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Pagina pagina = new Pagina();
		
		try {
			pagina = new PaginaDAO().buscaObjetoId(Integer.parseInt(value));
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		if(pagina == null){
			return null;
		}
		
		return pagina;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Pagina pagina = (Pagina) value;		
		
		if(pagina == null || pagina.getId() == null){
			return null;
		}
		
		return String.valueOf(pagina.getId());
	}

}
