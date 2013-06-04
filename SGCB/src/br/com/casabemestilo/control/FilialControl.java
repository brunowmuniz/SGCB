package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.FilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Filial;

public class FilialControl extends Control implements InterfaceControl, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Filial filial;
	
	private List<Filial> listaFilial;
	
	private FilialDAO filialDAO;

	
	/*
	 * CONSTRUTORES
	 * */
	public FilialControl(String messagem, Filial filial,
			List<Filial> listaFilial, FilialDAO filialDAO) {
		super(messagem);
		this.filial = filial;
		this.listaFilial = listaFilial;
		this.filialDAO = filialDAO;
	}

	public FilialControl(String messagem) {
		super(messagem);
	
	}

	public FilialControl() {
	
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
	public List<Filial> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Filial> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Filial> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filial buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	public FilialDAO getFilialDAO() {
		return filialDAO;
	}

	public void setFilialDAO(FilialDAO filialDAO) {
		this.filialDAO = filialDAO;
	}
	
}
