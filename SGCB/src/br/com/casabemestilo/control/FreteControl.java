package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Frete;

public class FreteControl extends Control implements Serializable,
		InterfaceControl {

	
	private static final long serialVersionUID = 1L;
	
	private Frete frete;
	
	private List<Frete> listaFrete;
	
	private FreteDAO freteDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public FreteControl(String messagem, Frete frete, List<Frete> listaFrete,
			FreteDAO freteDAO) {
		super(messagem);
		this.frete = frete;
		this.listaFrete = listaFrete;
		this.freteDAO = freteDAO;
	}

	public FreteControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public FreteControl() {
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
	public List<Frete> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Frete buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/*
	 * GETTERS & SETTERS
	 * */
	public Frete getFrete() {
		return frete;
	}

	public void setFrete(Frete frete) {
		this.frete = frete;
	}

	public List<Frete> getListaFrete() {
		return listaFrete;
	}

	public void setListaFrete(List<Frete> listaFrete) {
		this.listaFrete = listaFrete;
	}

	public FreteDAO getFreteDAO() {
		return freteDAO;
	}

	public void setFreteDAO(FreteDAO freteDAO) {
		this.freteDAO = freteDAO;
	}

}
