package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;

import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.ExtendedPDFExporter;

public class Control implements Serializable{

	protected String mensagem;
	
	protected Logger logger = Logger.getLogger(Control.class);
	
	protected Usuario usuarioLogado;
	
	

	public Control(String messagem) {
		super();
		this.mensagem = messagem;
	}

	public Control() {
		super();
	}
	

	public String getmensagem() {
		return mensagem;
	}

	public void setmensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuarioLogado() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("UsuarioLogado");
        return usuarioLogado;
	}
	
}
