package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.Fornecedor;

@ManagedBean
@ViewScoped
public class FornecedoresControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Fornecedor fornecedor;
	
	private List<Fornecedor> listaFornecedores;
	
	private FornecedoresDAO fornecedoresDAO;
	
	private List listaFornecedorCombo;
	
	
	/*
	 * CONSTRUTORES
	 * */	
	public FornecedoresControl(String messagem, Fornecedor fornecedor,
			List<Fornecedor> listaFornecedores,
			FornecedoresDAO fornecedoresDAO) {
		super(messagem);
		this.fornecedor = fornecedor;
		this.listaFornecedores = listaFornecedores;
		this.fornecedoresDAO = fornecedoresDAO;
	}

	public FornecedoresControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public FornecedoresControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("fornecedor") != null){
			fornecedor = (Fornecedor) ELFlash.getFlash().get("fornecedor");
		}		
	}
		
	    
	 @PreDestroy
	public void destroy() {}
	 
	@Override
	public void gravar() {
		try{
    		fornecedoresDAO = new FornecedoresDAO();
    		fornecedor.setDeleted(false);
        	fornecedoresDAO.insert(fornecedor);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor:" + fornecedor.getNome() + " foi gravado!"));
        	fornecedor = new Fornecedor();
        	logger.info("Salvo fornecedor: " + fornecedor.toString());
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + fornecedor.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + fornecedor.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + fornecedor.getNome());
		}

	}

	@Override
	public void deletar() {
		try{
			fornecedoresDAO = new FornecedoresDAO();
    		fornecedor.setDeleted(true);
    		fornecedoresDAO.delete(fornecedor);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor: " + fornecedor.getNome() + " deletado!"));
        	fornecedor = new Fornecedor();
    	}catch(ConstraintViolationException e){
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

	@Override
	public void alterar() {
		try{
			fornecedoresDAO = new FornecedoresDAO();
    		fornecedoresDAO.update(fornecedor);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor: " + fornecedor.getNome() + " alterado!"));
        	fornecedor = new Fornecedor();
    	}catch(ConstraintViolationException e){
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

	public String alterarCadastro(){
		ELFlash.getFlash().put("fornecedor", fornecedor);
		return "cadastrafornecedor";
	}
	
	@Override
	public List<Fornecedor> listarAtivos() {
		try{
			fornecedoresDAO = new FornecedoresDAO();
			listaFornecedores = fornecedoresDAO.listaAtivos();
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
		return listaFornecedores;
	}

	@Override
	public List<FornecedoresControl> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FornecedoresControl buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String sairAlteracao(){
		return "manutencaofornecedor?faces-redirect=true";
	}
	
	/*
	 * GETTERS & SETTERS
	 * */	
	public Fornecedor getFornecedor() {
		if(this.fornecedor == null){
			this.fornecedor = new Fornecedor();
		}
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

	public FornecedoresDAO getFornecedoresDAO() {
		return fornecedoresDAO;
	}

	public void setFornecedoresDAO(FornecedoresDAO fornecedoresDAO) {
		this.fornecedoresDAO = fornecedoresDAO;
	}

	public List getListaFornecedorCombo() {
		listaFornecedorCombo = new ArrayList();
		listarAtivos();
		for (Fornecedor fornecedores : listaFornecedores) {
            SelectItem si = new SelectItem();
            si.setValue(fornecedores.getId());
            si.setLabel(fornecedores.getNome());             
            listaFornecedorCombo.add(si);
        }
		return listaFornecedorCombo;
	}

	public void setListaFornecedorCombo(List listaFornecedorCombo) {
		this.listaFornecedorCombo = listaFornecedorCombo;
	}
	
}
