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

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Formapagamento;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.CondicoesPagamento;

@ManagedBean
@ViewScoped
public class CondicoesPagamentoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
	
	private List<CondicoesPagamento> listaCondicoesPagamento;
	
	private CondicoesPagamentoDAO condicoesPagamentoDAO;
	
	private List listaCondicoesPagamentoCombo;  

	
	/*
	 * CONSTRUTORES
	 * */
	public CondicoesPagamentoControl(String messagem, CondicoesPagamento condicoesPagamento,
			List<CondicoesPagamento> listaCondicoesPagamento, CondicoesPagamentoDAO condicoesPagamentoDAO) {
		super(messagem);
		this.condicoesPagamento = condicoesPagamento;
		this.listaCondicoesPagamento = listaCondicoesPagamento;
		this.condicoesPagamentoDAO = condicoesPagamentoDAO;
	}

	public CondicoesPagamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public CondicoesPagamentoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("condicoesPagamento") != null){
			condicoesPagamento = (CondicoesPagamento) ELFlash.getFlash().get("condicoesPagamento");
		}		
	}
		
	    
	 @PreDestroy
	public void destroy() {}
	 
	@Override
	public void gravar() {
		try{
    		condicoesPagamentoDAO = new CondicoesPagamentoDAO();
    		condicoesPagamento.setDeleted(false);
        	condicoesPagamentoDAO.insert(condicoesPagamento);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condições de Pagamento:" + condicoesPagamento.getNome() + " foi gravado!"));
        	logger.info("Salvo retenção: " + condicoesPagamento.toString());
        	condicoesPagamento = new CondicoesPagamento();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + condicoesPagamento.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + condicoesPagamento.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + condicoesPagamento.getNome());
		}
	}

	@Override
	public void deletar() {
		try{
    		condicoesPagamentoDAO = new CondicoesPagamentoDAO();
    		condicoesPagamento.setDeleted(true);
        	condicoesPagamentoDAO.delete(condicoesPagamento);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condições de Pagamento:" + condicoesPagamento.getNome() + " foi deletado!"));        	
        	logger.info("Deletado retenção: " + condicoesPagamento.toString());
        	condicoesPagamento = new CondicoesPagamento();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + condicoesPagamento.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + condicoesPagamento.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + condicoesPagamento.getNome());
		}
	}

	@Override
	public void alterar() {
		try{
    		condicoesPagamentoDAO = new CondicoesPagamentoDAO();
    		condicoesPagamento.setDeleted(false);
        	condicoesPagamentoDAO.update(condicoesPagamento);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condições de Pagamento:" + condicoesPagamento.getNome() + " foi alterado!"));        	
        	logger.info("Alterado retenção: " + condicoesPagamento.toString());
        	condicoesPagamento = new CondicoesPagamento();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + condicoesPagamento.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + condicoesPagamento.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + condicoesPagamento.getNome());
		}
	}
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("condicoesPagamento", condicoesPagamento);
		return "cadastracondicoespagamento";
	}
	
	public String sairAlteracao(){
		return "manutencaocondicoespagamento?faces-redirect=true";
	}

	@Override
	public List<CondicoesPagamento> listarAtivos() {
		try{
			condicoesPagamentoDAO = new CondicoesPagamentoDAO();
			listaCondicoesPagamento = condicoesPagamentoDAO.listaAtivos();
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
		return listaCondicoesPagamento;
	}
	
	public List carregaCondicoesPagamentoPorFormaPagamento(Integer idFormaPagamento){
		listaCondicoesPagamentoCombo = new ArrayList();
		listarAtivos();
		for(CondicoesPagamento condicoesPagamento : listaCondicoesPagamento){
			if(condicoesPagamento.getFormapagamento().getId() == idFormaPagamento){
				SelectItem si = new SelectItem();
				si.setValue(condicoesPagamento.getId());
				si.setLabel(condicoesPagamento.getNome());
				listaCondicoesPagamentoCombo.add(si);
			}			
		}
		
		return listaCondicoesPagamentoCombo;
	}

	@Override
	public List<CondicoesPagamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CondicoesPagamento> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CondicoesPagamento buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public CondicoesPagamento getCondicoesPagamento() {
		if(condicoesPagamento == null){
			condicoesPagamento = new CondicoesPagamento();
		}
		return condicoesPagamento;
	}

	public void setCondicoesPagamento(CondicoesPagamento condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}

	public List<CondicoesPagamento> getlistaCondicoesPagamento() {
		return listaCondicoesPagamento;
	}

	public void setlistaCondicoesPagamento(List<CondicoesPagamento> listaCondicoesPagamento) {
		this.listaCondicoesPagamento = listaCondicoesPagamento;
	}

	public CondicoesPagamentoDAO getRetencaoDAO() {
		return condicoesPagamentoDAO;
	}

	public void setRetencaoDAO(CondicoesPagamentoDAO condicoesPagamentoDAO) {
		this.condicoesPagamentoDAO = condicoesPagamentoDAO;
	}

	public List getListaCondicoesPagamentoCombo() {		
		return listaCondicoesPagamentoCombo;
	}

	public void setListaCondicoesPagamentoCombo(List listaCondicoesPagamentoCombo) {
		this.listaCondicoesPagamentoCombo = listaCondicoesPagamentoCombo;
	}
	
	

}
