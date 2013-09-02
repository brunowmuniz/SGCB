package br.com.casabemestilo.control;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class Control implements Serializable{

	protected String mensagem;
	
	protected Logger logger = Logger.getLogger(Control.class);
	
	private String texto;

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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
}
