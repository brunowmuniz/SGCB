package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.RetencaoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Retencao;

@ManagedBean
@ViewScoped
public class RetencaoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Retencao retencao = new Retencao();
	
	private List<Retencao> listaRetencao;
	
	private RetencaoDAO retencaoDAO;

	
	/*
	 * CONSTRUTORES
	 * */
	public RetencaoControl(String messagem, Retencao retencao,
			List<Retencao> listaRetencao, RetencaoDAO retencaoDAO) {
		super(messagem);
		this.retencao = retencao;
		this.listaRetencao = listaRetencao;
		this.retencaoDAO = retencaoDAO;
	}

	public RetencaoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public RetencaoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("retencao") != null){
			retencao = (Retencao) ELFlash.getFlash().get("retencao");
		}		
	}
		
	    
	 @PreDestroy
	public void destroy() {}
	 
	@Override
	public void gravar() {
		try{
    		retencaoDAO = new RetencaoDAO();
    		retencao.setDeleted(false);
        	retencaoDAO.insert(retencao);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Retenção:" + retencao.getNome() + " foi gravado!"));
        	logger.info("Salvo retenção: " + retencao.toString());
        	retencao = new Retencao();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + retencao.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + retencao.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + retencao.getNome());
		}
	}

	@Override
	public void deletar() {
		try{
    		retencaoDAO = new RetencaoDAO();
    		retencao.setDeleted(true);
        	retencaoDAO.delete(retencao);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor:" + retencao.getNome() + " foi deletado!"));        	
        	logger.info("Deletado retenção: " + retencao.toString());
        	retencao = new Retencao();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + retencao.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + retencao.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + retencao.getNome());
		}
	}

	@Override
	public void alterar() {
		try{
    		retencaoDAO = new RetencaoDAO();
    		retencao.setDeleted(false);
        	retencaoDAO.update(retencao);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor:" + retencao.getNome() + " foi alterado!"));        	
        	logger.info("Alterado retenção: " + retencao.toString());
        	retencao = new Retencao();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + retencao.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + retencao.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + retencao.getNome());
		}
	}
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("retencao", retencao);
		return "cadastraretencao";
	}
	
	public String sairAlteracao(){
		return "manutencaoretencao?faces-redirect=true";
	}

	@Override
	public List<Retencao> listarAtivos() {
		try{
			retencaoDAO = new RetencaoDAO();
			listaRetencao = retencaoDAO.listaAtivos();
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
		return listaRetencao;
	}

	@Override
	public List<Retencao> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Retencao buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Retencao getRetencao() {
		if(retencao == null){
			retencao = new Retencao();
		}
		return retencao;
	}

	public void setRetencao(Retencao retencao) {
		this.retencao = retencao;
	}

	public List<Retencao> getListaRetencao() {
		return listaRetencao;
	}

	public void setListaRetencao(List<Retencao> listaRetencao) {
		this.listaRetencao = listaRetencao;
	}

	public RetencaoDAO getRetencaoDAO() {
		return retencaoDAO;
	}

	public void setRetencaoDAO(RetencaoDAO retencaoDAO) {
		this.retencaoDAO = retencaoDAO;
	}	

}
