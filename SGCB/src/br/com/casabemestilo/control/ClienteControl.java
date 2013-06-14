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
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.AssistenciaTecnicaDAO;
import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Cliente;

@ViewScoped
@ManagedBean
public class ClienteControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Cliente cliente = new Cliente();
	
	private ClienteDAO clienteDAO;
	
	private List<Cliente> listaCliente;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ClienteControl(String messagem, Cliente cliente,
			ClienteDAO clienteDAO, List<Cliente> listaCliente) {
		super(messagem);
		this.cliente = cliente;
		this.clienteDAO = clienteDAO;
		this.listaCliente = listaCliente;
	}
	
	public ClienteControl() {
		super();
	}

	public ClienteControl(String messagem) {
		super(messagem);
	}



	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("cliente") != null){
			cliente = (Cliente) ELFlash.getFlash().get("cliente");
		}		
	}
		
	    
	 @PreDestroy
	public void destroy() {}
	 
	@Override
	public void gravar() {
		try{
    		clienteDAO = new ClienteDAO();
        	cliente.setDeleted(false);        	
        	clienteDAO.insert(cliente);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente:" + cliente.getNome() + " foi gravado!"));
        	logger.info("Salvo cliente: " + cliente.getNome());
        	cliente = new Cliente();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + cliente.getNome());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "-" + cliente.getNome());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro: " + super.mensagem + "-" + cliente.getNome());
		}
		
	}	

	@Override
	public void deletar() {
		try{
			clienteDAO = new ClienteDAO();
    		cliente.setDeleted(true);
    		clienteDAO.delete(cliente);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente:" + cliente.getNome() + " deletado!"));
        	cliente = new Cliente();
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
    		clienteDAO = new ClienteDAO();    		
    		clienteDAO.update(cliente);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente:" + cliente.getNome() + " alterado!"));
        	cliente = new Cliente();
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
		ELFlash.getFlash().put("cliente", cliente);
		return "cadastracliente";
	}

	@Override
	public List<Cliente> listarAtivos() {
		try{
			clienteDAO = new ClienteDAO();
	    	listaCliente = clienteDAO.listaAtivos();
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
		return listaCliente;
	}

	@Override
	public List<Cliente> listarTodos() {
		try{
			clienteDAO = new ClienteDAO();
	    	listaCliente = clienteDAO.listaTodos();
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
		return listaCliente;
	}

	@Override
	public List<Cliente> listaSelecao(Object obj) {
		try{
			clienteDAO = new ClienteDAO();
	    	listaCliente = clienteDAO.listaSelecao(cliente);
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
		return listaCliente;
	}

	@Override
	public Cliente buscaObjetoId(Integer id) {
		clienteDAO = new ClienteDAO();
		try {
			cliente = clienteDAO.buscaObjetoId(id);
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
		return cliente;
	}

	public String sairAlteracao(){
		return "manutencaocliente?faces-redirect=true";
	}
	
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Cliente getCliente() {
		
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}	
	
}
