package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Comissao;

public class ComissaoControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Comissao comissao;
	
	private ComissaoDAO comissaoDAO;
	
	private List<Comissao> listaComissao;
	
	
	/*
	 * CONSTRUTORES
	 * */	
	public ComissaoControl(String messagem, Comissao comissao,
			ComissaoDAO comissaoDAO, List<Comissao> listaComissao) {
		super(messagem);
		this.comissao = comissao;
		this.comissaoDAO = comissaoDAO;
		this.listaComissao = listaComissao;
	}	
	
	public ComissaoControl() {
		super();		
	}

	public ComissaoControl(String messagem) {
		super(messagem);
	}
	

	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comissao> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comissao buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Comissao getComissao() {
		return comissao;
	}

	public void setComissao(Comissao comissao) {
		this.comissao = comissao;
	}

	public ComissaoDAO getComissaoDAO() {
		return comissaoDAO;
	}

	public void setComissaoDAO(ComissaoDAO comissaoDAO) {
		this.comissaoDAO = comissaoDAO;
	}

	public List<Comissao> getListaComissao() {
		return listaComissao;
	}

	public void setListaComissao(List<Comissao> listaComissao) {
		this.listaComissao = listaComissao;
	}
	
}
