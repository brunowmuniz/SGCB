package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.UsuarioFilial;

@FacesConverter(forClass=Filial.class)
public class FilialConverter implements Converter {

	public FilialConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("FilialConverter.getAsObject(): " + value);
		
		if(value == null || value.isEmpty()){
			return null;
		}
		
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Filial filial = (Filial) value;
		
		System.out.println("ProdutoConverter.getAsString(): " + filial);
		
		if(filial == null || filial.getId() == null){
			return null;
		}
		
		return String.valueOf(filial.getId());
	}

}
