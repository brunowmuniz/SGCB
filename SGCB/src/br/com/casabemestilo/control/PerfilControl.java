package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Perfil;

@ViewScoped
@ManagedBean
public class PerfilControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Perfil perfil;
	
	private List<Perfil> listarPerfil;
	
	private PerfilDAO perfilDAO;
	
	
	/*
     * CONSTRUTORES
     */
    public PerfilControl(String messagem, Perfil perfil,
			List<Perfil> listarPerfil, PerfilDAO perfilDAO) {
		super(messagem);
		this.perfil = perfil;
		this.listarPerfil = listarPerfil;
		this.perfilDAO = perfilDAO;
	}

	public PerfilControl() {
		super();
	}
	
	public PerfilControl(String messagem) {
		super(messagem);
	}



	/*
     * MÉTODOS
     */
    @PostConstruct
    public void init(){}
	
    
    @PreDestroy
    public void destroy() {}
    
    
    public void gravar(){
    	try{
    		perfilDAO = new PerfilDAO();
        	perfil.setDeleted(false);        	
        	perfilDAO.insert(perfil);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil: " + perfil.getDescricao() + " gravado!"));
        	logger.info("Salvo perfil: " + perfil.toString());
        	perfil = new Perfil();        	
    	}catch(TransactionException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Conexão: " + super.mensagem, ""));
    	}
    	catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));		
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}    	
    }
    
    public void deletar(){
    	try{
    		perfil = this.buscaObjetoId(perfil.getId());
    		perfil.setDeleted(true);
        	perfilDAO.delete(perfil);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil:" + perfil.getDescricao() + " deletado!"));
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
    	
    }
    
    public void alterar(){
    	try{
    		perfilDAO = new PerfilDAO();        	
        	perfilDAO.update(perfil);        	
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil: " + perfil.getDescricao() + " alterado!"));
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
    }
    

	public List<Perfil> listarAtivos(){
		try{
			perfilDAO = new PerfilDAO();
	    	listarPerfil = perfilDAO.listaAtivos();
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
		return listarPerfil;		
    }

	@Override
	public List<Perfil> listarTodos() {
		try{
			perfilDAO = new PerfilDAO();
	    	listarPerfil = perfilDAO.listaTodos();
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
    	return listarPerfil;
	}


	@Override
	public List<Perfil> listaSelecao(Object obj) {
		try{
			perfil = (Perfil) obj;
			perfilDAO = new PerfilDAO();
			listarPerfil = perfilDAO.listaSelecao(perfil);
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
		return listarPerfil;
	}


	@Override
	public Perfil buscaObjetoId(Integer id) {
		try{
			perfil.setId(id);
			perfilDAO = new PerfilDAO();
			perfil = perfilDAO.buscaObjetoId(perfil.getId());
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
		return perfil;
	}
	
	

	/*
	 * GETTERS & SETTERS
	 */	
	public Perfil getPerfil() {
		if(perfil == null){
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	
	public List<Perfil> getListarPerfil() {
		return listarPerfil;
	}
	
	public void setListarPerfil(List<Perfil> listarPerfil) {
		this.listarPerfil = listarPerfil;
	}
	
}
