package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Usuario;

@FacesConverter(forClass=Usuario.class)
public class UsuarioConverter implements Converter{

	public UsuarioConverter() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		Usuario usuario = new Usuario();
		
		try {
			usuario = new UsuarioDAO().buscaObjetoId(Integer.parseInt(value));
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
				
		if(usuario == null){
			return null;
		}
		
		return usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		Usuario usuario = (Usuario) value;		
		
		if(usuario == null || usuario.getId() == null){
			return null;
		}
		
		return String.valueOf(usuario.getId());
	}
	
}
