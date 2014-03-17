package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.MontagemDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Montagem;

public class MontagemControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Montagem> listaMontagem;
	
	private Montagem montagem;
	
	private MontagemDAO montagemDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public MontagemControl(String messagem, List<Montagem> listaMontagem,
			Montagem montagem, MontagemDAO montagemDAO) {
		super(messagem);
		this.listaMontagem = listaMontagem;
		this.montagem = montagem;
		this.montagemDAO = montagemDAO;
	}

	public MontagemControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public MontagemControl() {
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
	public List<Montagem> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Montagem> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Montagem> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Montagem buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Montagem> getListaMontagem() {
		return listaMontagem;
	}

	public void setListaMontagem(List<Montagem> listaMontagem) {
		this.listaMontagem = listaMontagem;
	}

	public Montagem getMontagem() {
		return montagem;
	}

	public void setMontagem(Montagem montagem) {
		this.montagem = montagem;
	}

	public MontagemDAO getMontagemDAO() {
		return montagemDAO;
	}

	public void setMontagemDAO(MontagemDAO montagemDAO) {
		this.montagemDAO = montagemDAO;
	}	

}
