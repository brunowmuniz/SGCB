package br.com.casabemestilo.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.AssistenciaTecnicaDAO;
import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.TipoMovimentacao;
import br.com.casabemestilo.model.Usuario;

@ViewScoped
@ManagedBean
public class ClienteControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Cliente cliente = new Cliente();
	
	private ClienteDAO clienteDAO;
	
	private List<Cliente> listaCliente;
	
	private LazyDataModel<Cliente> listaLazyCliente;
	
	private Oc oc;
	
	
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
        	defineCamposTipoPessoa();
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
    		defineCamposTipoPessoa();
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
	
	public List<Cliente> listaComplete(String nomeCliente){
		listaCliente = new ArrayList<Cliente>();		
		cliente.setNome(nomeCliente);
		cliente.setDeleted(false);
		try {
			listaCliente = new ClienteDAO().listaSelecao(cliente);
			if(listaCliente.isEmpty()){
				cliente.setNome(nomeCliente);
				listaCliente.add(cliente);
			}
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
		return listaCliente;
	}
	
	public LazyDataModel<Cliente> listaLazyClienteGeral(){
		if(listaLazyCliente == null){
			listaLazyCliente = new LazyDataModel<Cliente>() {									
									private List<Cliente> listaLazy;
									
									@Override
								    public Cliente getRowData(String idCliente) {
								    	Integer id = Integer.valueOf(idCliente);
								    	
								        for(Cliente cliente : listaLazy) {
								            if(cliente.getId().equals(id))
								                return cliente;
								        }
								        
								        return null;
								    }

								    @Override
								    public Object getRowKey(Cliente cliente) {
								        return cliente.getId();
								    }

								    @Override
								    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
								    	clienteDAO = new ClienteDAO();  
								    	
								    	listaLazy = clienteDAO.listaLazy(first, pageSize, filters);
								    	
								    	if (getRowCount() <= 0) {  
								            setRowCount(clienteDAO.totalUsuario(filters));  
								        } 			       
								        setPageSize(pageSize);  
								        return listaLazy;  
								    }
			};
		}
		return listaLazyCliente;
	}
	
	public List listaClienteCombo(){
		listarAtivos();
		List listaClienteCombo = new ArrayList();
		
		for(Cliente cliente : listaCliente){
			SelectItem si = new SelectItem();
			si.setValue(cliente.getId());
			si.setLabel(cliente.getNome());
			listaClienteCombo.add(si);
		}
		return listaClienteCombo;
	}
	
	public void buscarComprasCliente(){
		Collection<Oc> listaOcsCliente = cliente.getOcs();
		Collections.reverse(cliente.getOcs());
	}
	
	public String detalharOcCliente(){
		ELFlash.getFlash().put("oc", getOc());
		return "cadastraoc?faces-redirect=true";
	}
	
	public void carregaClienteArquivo() throws ConstraintViolationException, HibernateException, Exception{
		clienteDAO = new ClienteDAO();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("c:\\temp\\casabem\\cliente.csv")));
			String linha = null;
			int i = 0;
			listaCliente = new ArrayList<Cliente>();
			while((linha = reader.readLine()) != null){
				String[] linhaCliente = linha.split(";");
				cliente = new Cliente();				
				//cliente.setId(Integer.parseInt(linhaCliente[0]));
				cliente.setNome(linhaCliente[1]);
				cliente.setIe(linhaCliente[3]);				
				cliente.setCidade(linhaCliente[7] + "/"+ linhaCliente[6]);
				cliente.setBairro(linhaCliente[8]);
				cliente.setEndereco(linhaCliente[9]);
				cliente.setCep(linhaCliente[10]);
				if(linhaCliente[2].length() > 12){
					cliente.setTipoPessoa("pj");
					cliente.setCnpj(linhaCliente[2]);
					cliente.setCpf(null);
				}else{
					cliente.setTipoPessoa("pf");
					cliente.setCnpj(null);
					cliente.setCpf(linhaCliente[2]);
				}
				if(linhaCliente[4].indexOf("/") > -1){
					cliente.setTelefone(linhaCliente[4].split("/")[0]);
					cliente.setTelefoneadicional(linhaCliente[4].split("/")[1]);					
				}else if (linhaCliente[4] == "") {
					cliente.setTelefone("");					
				}else if(linhaCliente[4] == null){
					cliente.setTelefone("");
				}else{
					cliente.setTelefone(linhaCliente[4]);
				}
				cliente.setDeleted(false);
				listaCliente.add(cliente);
				System.out.println("Adiciona lista " + cliente);				
			}
			clienteDAO.insertCliente(listaCliente);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(cliente);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(cliente);
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			System.out.println(cliente);
		}
	}
	
	private void defineCamposTipoPessoa(){
		if(cliente.getTipoPessoa().equals("pj")){
			cliente.setCpf(null);
			cliente.setRg(null);
			cliente.setDatadenascimento(null);
		}else{
			cliente.setCnpj(null);
		}
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

	public LazyDataModel<Cliente> getListaLazyCliente() {
		return listaLazyCliente;
	}

	public void setListaLazyCliente(LazyDataModel<Cliente> listaLazyCliente) {
		this.listaLazyCliente = listaLazyCliente;
	}

	public Oc getOc() {
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}
	
	

}
