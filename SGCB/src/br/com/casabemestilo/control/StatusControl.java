package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Status;

public class StatusControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Status status;
	
	private List<Status> listaStatus;

	private StatusDAO statusDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public StatusControl(String messagem, Status status,
			List<Status> listaStatus, StatusDAO statusDAO) {
		super(messagem);
		this.status = status;
		this.listaStatus = listaStatus;
		this.statusDAO = statusDAO;
	}

	public StatusControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public StatusControl() {
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
	public List<Status> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Status> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<Status> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public StatusDAO getStatusDAO() {
		return statusDAO;
	}

	public void setStatusDAO(StatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}

	
}
