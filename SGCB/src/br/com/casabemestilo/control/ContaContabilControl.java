package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.ContaContabilDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Contacontabil;

public class ContaContabilControl extends Control implements InterfaceControl, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Contacontabil> listaContaContabil;
	
	private Contacontabil contacontabil;
	
	private ContaContabilDAO contaContabilDAO;
	
	/*
	 * CONSTRUTORES
	 * */
	public ContaContabilControl(List<Contacontabil> listaContaContabil,
			Contacontabil contacontabil, ContaContabilDAO contaContabilDAO) {
		super();
		this.listaContaContabil = listaContaContabil;
		this.contacontabil = contacontabil;
		this.contaContabilDAO = contaContabilDAO;
	}
		
	public ContaContabilControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContaContabilControl(String messagem) {
		super(messagem);
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
	public <T> List<T> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Contacontabil> getListaContaContabil() {
		return listaContaContabil;
	}

	public void setListaContaContabil(List<Contacontabil> listaContaContabil) {
		this.listaContaContabil = listaContaContabil;
	}

	public Contacontabil getContacontabil() {
		return contacontabil;
	}

	public void setContacontabil(Contacontabil contacontabil) {
		this.contacontabil = contacontabil;
	}

}
