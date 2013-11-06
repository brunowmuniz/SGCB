package br.com.casabemestilo.util;

import java.io.InputStream;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Relatorios {

	private FacesContext context;
	
	private HttpServletResponse response;
	
	private InputStream caminho;
	
	private HttpServletRequest request;
	
	private Map<String, Object> parametros;
	
	private static String caminhoRelatorio;
	
	private String nomeRelatorio;
	
	public Relatorios() {
		caminhoRelatorio = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/classes/br/com/casabemestilo/relatorio/");
		if(caminhoRelatorio.indexOf("home") > -1)
			caminhoRelatorio += "/";
		else
			caminhoRelatorio += "\\";
		
	}	
	
	
	public void imprimeRelatorioConexao(){
		
	}
	
	public void imprimeRelatorioDataSource(){
		
	}


	public FacesContext getContext() {
		context = FacesContext.getCurrentInstance();
		return context;
	}


	public void setContext(FacesContext context) {
		this.context = context;
	}


	public HttpServletResponse getResponse() {
		response = (HttpServletResponse) context.getExternalContext().getResponse();
		return response;
	}


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


	public InputStream getCaminho() {
		caminho = getClass().getResourceAsStream("../relatorio/oc.jrxml");
		return caminho;
	}


	public void setCaminho(InputStream caminho) {
		this.caminho = caminho;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public Map<String, Object> getParametros() {
		return parametros;
	}


	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}
	
	
}
