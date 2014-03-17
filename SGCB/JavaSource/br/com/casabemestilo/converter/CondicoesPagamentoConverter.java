package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Filial;

public class CondicoesPagamentoConverter implements Converter {

	public CondicoesPagamentoConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
		
		try {
			if(value != "" && value != null){
				condicoesPagamento = new CondicoesPagamentoDAO().buscaObjetoId(Integer.parseInt(value));
			}			
		} catch (ConstraintViolationException e) {		
			e.printStackTrace();
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (HibernateException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return condicoesPagamento;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		try {
			CondicoesPagamento condicoesPagamento = (CondicoesPagamento) value;
			return String.valueOf(condicoesPagamento.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
