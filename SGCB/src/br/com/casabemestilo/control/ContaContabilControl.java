package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Column;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ContaContabilDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Contacontabil;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pedido;

@ManagedBean
@ViewScoped
public class ContaContabilControl extends Control implements InterfaceControl, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Contacontabil> listaContaContabil;
	
	private Contacontabil contacontabil;
	
	private ContaContabilDAO contaContabilDAO;
	
	private List listaTiposConta;
	
	private LazyDataModel<Contacontabil> listaLazyContaContabil;
	
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ContaContabilControl(List<Contacontabil> listaContaContabil,
			Contacontabil contacontabil, ContaContabilDAO contaContabilDAO, Boolean contaBaixa) {
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
	
	public List listaContasCombo(String filter){
		listarTodos();
		List listaContaContabilCombo = new ArrayList();
		
		if(filter.equals("filter")){
			SelectItem si = new SelectItem();
			si.setLabel("Todos");
			si.setValue("");
			si.setNoSelectionOption(true);
			listaContaContabilCombo.add(si);
		}
		
		for(Contacontabil contacontabil : listaContaContabil){			
			listaContaContabilCombo.add(new SelectItem(contacontabil.getId(), contacontabil.getNome()));
		}
		return listaContaContabilCombo;
	}
	
	public List listaContasDebitoCombo(String filter){
		listarTodos();
		List listaContaContabilCombo = new ArrayList();
		
		if(filter.equals("filter")){
			SelectItem si = new SelectItem();
			si.setLabel("Todos");
			si.setValue("");
			si.setNoSelectionOption(true);
			listaContaContabilCombo.add(si);
		}
		
		for(Contacontabil contacontabil : listaContaContabil){
			if(contacontabil.getTipo().equals("D")){
				listaContaContabilCombo.add(new SelectItem(contacontabil.getId(), contacontabil.getNome()));
			}
		}
		return listaContaContabilCombo;
	}

	public LazyDataModel<Contacontabil> listaLazyContaContabilGeral(){
		if(listaLazyContaContabil == null){
			listaLazyContaContabil = new LazyDataModel<Contacontabil>() {
										private List<Contacontabil> listaLazy;
										
										@Override
									    public Contacontabil getRowData(String idContacontabil) {
									    	Integer id = Integer.valueOf(idContacontabil);
									    	
									        for(Contacontabil contacontabil : listaLazy) {
									            if(contacontabil.getId().equals(id))
									                return contacontabil;
									        }
									        
									        return null;
									    }
						
									    @Override
									    public Object getRowKey(Contacontabil contacontabil) {
									        return contacontabil.getId();
									    }
						
									    @Override
									    public List<Contacontabil> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
									    	contaContabilDAO = new ContaContabilDAO();  
									    	
									    	listaLazy = contaContabilDAO.listaLazy(first, pageSize, filters);
									    	
									    	if (getRowCount() <= 0) {  
									            setRowCount(contaContabilDAO.totalContacontabil(filters));  
									        }  
									       
									        setPageSize(pageSize);  
									        return listaLazy;  
									    }					
			};
		}
		return listaLazyContaContabil;
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

	public List getListaTiposConta() {
		if(listaTiposConta == null){
			listaTiposConta = new ArrayList();
			listaTiposConta.add(new SelectItem("","Todos"));
			listaTiposConta.add(new SelectItem("D","Débito"));
			listaTiposConta.add(new SelectItem("C","Crédito"));
		}
		return listaTiposConta;
	}

	public LazyDataModel<Contacontabil> getListaLazyContaContabil() {
		return listaLazyContaContabil;
	}

	public void setListaLazyContaContabil(
			LazyDataModel<Contacontabil> listaLazyContaContabil) {
		this.listaLazyContaContabil = listaLazyContaContabil;
	}

	
}
