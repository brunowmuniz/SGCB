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
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ContaContabilDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Contacontabil;
import br.com.casabemestilo.model.Pedido;

@ManagedBean
@ViewScoped
public class ContaContabilControl extends Control implements InterfaceControl, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Contacontabil> listaContaContabil;
	
	private Contacontabil contacontabil;
	
	private ContaContabilDAO contaContabilDAO;
	
	/*
	 * CONSTRUTORES
	 * */
	public ContaContabilControl(List<Contacontabil> listaContaContabil,
			Contacontabil contacontabil, ContaContabilDAO contaContabilDAO) {
		super();
		this.listaContaContabil = listaContaContabil;
		this.contacontabil = contacontabil;
		this.contaContabilDAO = contaContabilDAO;
	}
		
	public ContaContabilControl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContaContabilControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}


	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("cc") != null){
			contacontabil = (Contacontabil) ELFlash.getFlash().get("cc");			
			ELFlash.getFlash().clear();
		}else{
			contacontabil = new Contacontabil();
		}
	}
	
	@PreDestroy
	public void destroy(){
		
	}
	
	
	@Override
	public void gravar() {		
		try {
			contaContabilDAO = new ContaContabilDAO();
			contaContabilDAO.insert(contacontabil);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Conta Contábil " + contacontabil.getNome() + " foi gravada!"));
			logger.info("Conta Contábil: " + contacontabil.getNome() + " foi gravada");
			contacontabil = new Contacontabil();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar_conta_contabil] Erro Constraint: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");			
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[gravar_conta_contabil] Erro Hibernate: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");	
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("[gravar_conta_contabil] Erro Genérico: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");	
		}

	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar() {
		try {
			contaContabilDAO = new ContaContabilDAO();
			contaContabilDAO.update(contacontabil);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Conta Contábil " + contacontabil.getId() + "-" + contacontabil.getNome() + " foi alterada!"));
			logger.info("Conta Contábil: " + contacontabil.getNome() + " foi alterada");
			contacontabil = new Contacontabil();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar_conta_contabil] Erro Constraint: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");			
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar_conta_contabil] Erro Hibernate: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");	
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("[alterar_conta_contabil] Erro Genérico: " + super.mensagem + "-" + "Conta Contábil não foi gravada!");	
		}
	}

	@Override
	public <T> List<T> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacontabil> listarTodos() {
		contaContabilDAO = new ContaContabilDAO();
		try {
			listaContaContabil = contaContabilDAO.listaTodos();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaContaContabil;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("cc", contacontabil);
		return "cadastracontacontabil?faces-redirect=true";
	}
	
	public List listaContasCombo(){
		listarTodos();
		List listaContaContabilCombo = new ArrayList();
		for(Contacontabil contacontabil : listaContaContabil){			
			listaContaContabilCombo.add(new SelectItem(contacontabil.getId(), contacontabil.getNome()));
		}
		return listaContaContabilCombo;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Contacontabil> getListaContaContabil() {
		return listaContaContabil;
	}

	public void setListaContaContabil(List<Contacontabil> listaContaContabil) {
		this.listaContaContabil = listaContaContabil;
	}

	public Contacontabil getContacontabil() {
		if(contacontabil == null){
			contacontabil = new Contacontabil();
		}
		return contacontabil;
	}

	public void setContacontabil(Contacontabil contacontabil) {
		this.contacontabil = contacontabil;
	}

	public ContaContabilDAO getContaContabilDAO() {
		return contaContabilDAO;
	}

	public void setContaContabilDAO(ContaContabilDAO contaContabilDAO) {
		this.contaContabilDAO = contaContabilDAO;
	}
	
	

}
