package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Filial;

public class ClienteConverter implements Converter {

	public ClienteConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("Cliente.getAsObject(): " + value);
		
		Cliente cliente = new Cliente();
		
		try {
			cliente = new ClienteDAO().buscaObjetoId(Integer.parseInt(value));
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
				
		if(cliente == null){
			return null;
		}
		
		return cliente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Cliente cliente= (Cliente) value;
		
		System.out.println("ProdutoConverter.getAsString(): " + cliente);
		
		if(cliente == null || cliente.getId() == null){
			return null;
		}
		
		return String.valueOf(cliente.getId());
	}

}
