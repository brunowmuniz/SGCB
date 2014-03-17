package br.com.casabemestilo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.casabemestilo.model.UsuarioFilial;

@FacesConverter(forClass=UsuarioFilial.class)
public class UsuarioFilialConverter implements Converter {

	public UsuarioFilialConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("UsuarioFilialConverter.getAsObject(): " + value);
		
		if(value == null || value.isEmpty()){
			return null;
		}
		
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		UsuarioFilial usuarioFilial = (UsuarioFilial) value;
		
		System.out.println("ProdutoConverter.getAsString(): " + usuarioFilial);
		
		if(usuarioFilial == null || usuarioFilial.getId() == null){
			return null;
		}
		
		return String.valueOf(usuarioFilial.getFilial().getId());
	}

}
