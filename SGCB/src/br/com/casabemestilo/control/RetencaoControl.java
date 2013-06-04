package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.RetencaoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Retencao;

public class RetencaoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Retencao retencao;
	
	private List<Retencao> listaRetencao;
	
	private RetencaoDAO retencaoDAO;

	
	/*
	 * CONSTRUTORES
	 * */
	public RetencaoControl(String messagem, Retencao retencao,
			List<Retencao> listaRetencao, RetencaoDAO retencaoDAO) {
		super(messagem);
		this.retencao = retencao;
		this.listaRetencao = listaRetencao;
		this.retencaoDAO = retencaoDAO;
	}

	public RetencaoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public RetencaoControl() {
		// TODO Auto-generated constructor stub
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
	public List<Retencao> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Retencao buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Retencao getRetencao() {
		return retencao;
	}

	public void setRetencao(Retencao retencao) {
		this.retencao = retencao;
	}

	public List<Retencao> getListaRetencao() {
		return listaRetencao;
	}

	public void setListaRetencao(List<Retencao> listaRetencao) {
		this.listaRetencao = listaRetencao;
	}

	public RetencaoDAO getRetencaoDAO() {
		return retencaoDAO;
	}

	public void setRetencaoDAO(RetencaoDAO retencaoDAO) {
		this.retencaoDAO = retencaoDAO;
	}	

}
