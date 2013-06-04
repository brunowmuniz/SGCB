package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;

public class FornecedoresControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private FornecedoresControl fornecedoresControl;
	
	private List<FornecedoresControl> listaFornecedores;
	
	private FornecedoresDAO fornecedoresDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */	
	public FornecedoresControl(String messagem, FornecedoresControl fornecedoresControl,
			List<FornecedoresControl> listaFornecedores,
			FornecedoresDAO fornecedoresDAO) {
		super(messagem);
		this.fornecedoresControl = fornecedoresControl;
		this.listaFornecedores = listaFornecedores;
		this.fornecedoresDAO = fornecedoresDAO;
	}

	public FornecedoresControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public FornecedoresControl() {
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
	public List<FornecedoresControl> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FornecedoresControl buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	 * GETTERS & SETTERS
	 * */
	public FornecedoresControl getFornecedores() {
		return fornecedoresControl;
	}

	public void setFornecedores(FornecedoresControl fornecedoresControl) {
		this.fornecedoresControl = fornecedoresControl;
	}

	public List<FornecedoresControl> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<FornecedoresControl> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

	public FornecedoresDAO getFornecedoresDAO() {
		return fornecedoresDAO;
	}

	public void setFornecedoresDAO(FornecedoresDAO fornecedoresDAO) {
		this.fornecedoresDAO = fornecedoresDAO;
	}

}
