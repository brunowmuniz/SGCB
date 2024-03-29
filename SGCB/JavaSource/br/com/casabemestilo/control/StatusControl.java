package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Status;

@ManagedBean
@ViewScoped
public class StatusControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Status status;
	
	private List<Status> listaStatus;

	private StatusDAO statusDAO;
	
	private List listaStatusCombo = new ArrayList();
	
	
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
	 * M�TODOS
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
		try{
			statusDAO = new StatusDAO();
	    	listaStatus = statusDAO.listaAtivos();
		}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
		return listaStatus;
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

	public List getListaStatusCombo() {
		if(listaStatusCombo.isEmpty()){
			listarAtivos();
			listaStatusCombo.add(new SelectItem("", "Todos"));
			for(Status status : listaStatus){
				SelectItem si = new SelectItem();
				si.setValue(status.getId());
				si.setLabel(status.getDescricao());
				listaStatusCombo.add(si);
			}
		}
				
		
		return listaStatusCombo;
	}

	public void setListaStatusCombo(List listaStatusCombo) {
		this.listaStatusCombo = listaStatusCombo;
	}
	
}
