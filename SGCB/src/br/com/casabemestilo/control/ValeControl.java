package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.ValeDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Vale;

public class ValeControl extends Control implements InterfaceControl,
		Serializable {


	private static final long serialVersionUID = 1L;
	
	private Vale vale;
	
	private List<Vale> listaVale;
	
	private ValeDAO valeDAO;
	
	
	/*CONSTRUTORES*/
	public ValeControl(String messagem, Vale vale, List<Vale> listaVale,
			ValeDAO valeDAO) {
		super(messagem);
		this.vale = vale;
		this.listaVale = listaVale;
		this.valeDAO = valeDAO;
	}

	public ValeControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public ValeControl() {
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
	public List<Vale> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vale> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vale> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vale buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Vale getVale() {
		return vale;
	}

	public void setVale(Vale vale) {
		this.vale = vale;
	}

	public List<Vale> getListaVale() {
		return listaVale;
	}

	public void setListaVale(List<Vale> listaVale) {
		this.listaVale = listaVale;
	}

	public ValeDAO getValeDAO() {
		return valeDAO;
	}

	public void setValeDAO(ValeDAO valeDAO) {
		this.valeDAO = valeDAO;
	}
	

}
