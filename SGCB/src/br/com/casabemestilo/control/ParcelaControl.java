package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Parcela;

public class ParcelaControl extends Control implements InterfaceControl,
		Serializable {


	private static final long serialVersionUID = 1L;
	
	private Parcela parcela;
	
	private List<Parcela> listaParcela;
	
	private ParcelaDAO parcelaDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ParcelaControl(String messagem, Parcela parcela,
			List<Parcela> listaParcela, ParcelaDAO parcelaDAO) {
		super(messagem);
		this.parcela = parcela;
		this.listaParcela = listaParcela;
		this.parcelaDAO = parcelaDAO;
	}

	public ParcelaControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public ParcelaControl() {
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
	public List<Parcela> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	@Override
	public Parcela buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

	public List<Parcela> getListaParcela() {
		return listaParcela;
	}

	public void setListaParcela(List<Parcela> listaParcela) {
		this.listaParcela = listaParcela;
	}

	public ParcelaDAO getParcelaDAO() {
		return parcelaDAO;
	}

	public void setParcelaDAO(ParcelaDAO parcelaDAO) {
		this.parcelaDAO = parcelaDAO;
	}	

}
