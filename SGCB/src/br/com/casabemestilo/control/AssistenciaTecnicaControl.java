package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.AssistenciaTecnicaDAO;
import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Perfil;

@ManagedBean
@ViewScoped
public class AssistenciaTecnicaControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Assistenciatecnica assistenciaTecnica;
	
	private List<Assistenciatecnica> listaAssitenciaTecnica;
	
	private AssistenciaTecnicaDAO assistenciaTecnicaDAO;
	
	private List<Integer> listaMontadores = new ArrayList<Integer>();
	
	/*
	 * CONSTRUTORES
	 */
	public AssistenciaTecnicaControl(String messagem,
			Assistenciatecnica assistenciaTecnica,
			List<Assistenciatecnica> listaAssitenciaTecnica,
			AssistenciaTecnicaDAO assistenciaTecnicaDAO) {
		super(messagem);
		this.assistenciaTecnica = assistenciaTecnica;
		this.listaAssitenciaTecnica = listaAssitenciaTecnica;
		this.assistenciaTecnicaDAO = assistenciaTecnicaDAO;
	}

	public AssistenciaTecnicaControl() {
		super();
	}

	public AssistenciaTecnicaControl(String messagem) {
		super(messagem);
	}

	
	/*
	 * MÉTODOS
	 */
	 @PostConstruct
	 public void init(){}
		
	    
	 @PreDestroy
	 public void destroy() {}
	
	
	@Override
	public void gravar() {
		try{
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
        	//assistenciaTecnica.setDeleted(false);        	
        	assistenciaTecnicaDAO.insert(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica do produto: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " - da OC:" + assistenciaTecnica.getOcproduto().getOc().getId() +  " foi gravada!"));
        	assistenciaTecnica = new Assistenciatecnica();
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

	@Override
	public void deletar() {
		try{
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
    		assistenciaTecnica = this.buscaObjetoId(assistenciaTecnica.getId());
    		//assistenciaTecnica.setDeleted(true);
        	assistenciaTecnicaDAO.delete(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica da OC: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " deletado!"));
        	assistenciaTecnica = new Assistenciatecnica();
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
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
        	assistenciaTecnicaDAO.update(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica da OC: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " alterado!"));
        	assistenciaTecnica = new Assistenciatecnica();
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
	public List<Assistenciatecnica> listarAtivos() {
		try{
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaAtivos();
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
		return listaAssitenciaTecnica;
	}

	@Override
	public List<Assistenciatecnica> listarTodos() {
		try{
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaTodos();
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
		return listaAssitenciaTecnica;
	}

	@Override
	public List<Assistenciatecnica> listaSelecao(Object obj) {
		try{
			assistenciaTecnica = (Assistenciatecnica) obj;
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaSelecao(assistenciaTecnica);
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
		return listaAssitenciaTecnica;
	}

	@Override
	public Assistenciatecnica buscaObjetoId(Integer id) {
		try{
			assistenciaTecnica.setId(id);
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
			assistenciaTecnica = assistenciaTecnicaDAO.buscaObjetoId(assistenciaTecnica.getId());
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
		return assistenciaTecnica;
	}

	public Assistenciatecnica gravarAssistTecnicaProduto(List<Ocproduto> listaOcProdutos, List<Integer> listaMontadores) {
		
		try {
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
			assistenciaTecnica = new Assistenciatecnica();
			assistenciaTecnica.setDatainicio(new Date());
			assistenciaTecnica.setDatafim(new Date());
			assistenciaTecnica.setOcprodutos(listaOcProdutos);
			assistenciaTecnica.setObservacoes("");			
			for (int i = 0; i < listaMontadores.size(); i++) {				
				assistenciaTecnica.setMontador(assistenciaTecnica.getMontador().concat((String) (i==0 ? listaMontadores.get(i) : "," + listaMontadores.get(i))));
			}
			assistenciaTecnica = assistenciaTecnicaDAO.insertAssistenciaTecnica(assistenciaTecnica);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica gerada para os produtos!"));
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return assistenciaTecnica;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Assistenciatecnica getAssistenciaTecnica() {
		return assistenciaTecnica;
	}

	public void setAssistenciaTecnica(Assistenciatecnica assistenciaTecnica) {
		this.assistenciaTecnica = assistenciaTecnica;
	}

	
	public List<Assistenciatecnica> getListaAssitenciaTecnica() {
		return listaAssitenciaTecnica;
	}

	public void setListaAssitenciaTecnica(
			List<Assistenciatecnica> listaAssitenciaTecnica) {
		this.listaAssitenciaTecnica = listaAssitenciaTecnica;
	}

	
	public AssistenciaTecnicaDAO getAssistenciaTecnicaDAO() {
		return assistenciaTecnicaDAO;
	}

	public void setAssistenciaTecnicaDAO(AssistenciaTecnicaDAO assistenciaTecnicaDAO) {
		this.assistenciaTecnicaDAO = assistenciaTecnicaDAO;
	}

	public List<Integer> getListaMontadores() {
		return listaMontadores;
	}

	public void setListaMontadores(List<Integer> listaMontadores) {
		this.listaMontadores = listaMontadores;
	}
	
	
}
