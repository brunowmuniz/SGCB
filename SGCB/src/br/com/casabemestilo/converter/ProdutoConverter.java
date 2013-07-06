package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.model.Produto;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {

	public ProdutoConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		Produto produto = new Produto();
		
		try {
			produto = new ProdutoDAO().buscaObjetoId(Integer.parseInt(value));
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
		
		
		if(produto == null){
			return null;
		}
		
		return produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Produto produto = (Produto) value;
		
		if(produto == null || produto.getId() == null){
			return null;
		}
		
		return String.valueOf(produto.getId());
	}

}
