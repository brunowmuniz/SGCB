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
import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Produto;

@ManagedBean
@ViewScoped
public class ProdutoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto();
	
	private List<Produto> listaProduto;
	
	private ProdutoDAO produtoDAO;
	
	private LazyDataModel<Produto> listaLazyProduto;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ProdutoControl(String messagem, Produto produto,
			List<Produto> listaProduto, ProdutoDAO produtoDAO) {
		super(messagem);
		this.produto = produto;
		this.listaProduto = listaProduto;
		this.produtoDAO = produtoDAO;
	}

	public ProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public ProdutoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){
		if(ELFlash.getFlash().get("produto") != null){
			produto = (Produto) ELFlash.getFlash().get("produto");
		}		
	}		
	    
	 @PreDestroy
	public void destroy() {}
	
	@Override
	public void gravar() {
		try{
    		produtoDAO = new ProdutoDAO();
    		produto.setDeleted(false);
    		if(produto.getEhPlanejado()){
    			produto.setTemMontagem(true);
    		}
    		produtoDAO.insert(produto);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto: " + produto.getDescricao() + " foi gravado!"));
        	produto = new Produto();
        	logger.info("Salvo produto: " + produto.toString());
    	}catch(ConstraintViolationException e){    		
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + produto.getDescricao());
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + produto.getDescricao());
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + produto.getDescricao());
		}
		
	}

	@Override
	public void deletar() {
		try{
			produtoDAO = new ProdutoDAO();
    		produto.setDeleted(true);
    		produtoDAO.delete(produto);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto: " + produto.getDescricao() + " deletado!"));
        	produto = new Produto();
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
			produtoDAO = new ProdutoDAO();
			if(produto.getEhPlanejado()){
    			produto.setTemMontagem(true);
    		}
    		produtoDAO.update(produto);    		
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto: " + produto.getDescricao() + " alterado!"));
        	produto = new Produto();
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
		ELFlash.getFlash().put("produto", produto);
		return "cadastraproduto?faces-redirect=true";
	}

	@Override
	public List<Produto> listarAtivos() {
		try{
			produtoDAO = new ProdutoDAO();
			listaProduto = produtoDAO.listaAtivos();
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
		return listaProduto;
	}

	@Override
	public List<Produto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String sairAlteracao(){
		return "manutencaoproduto?faces-redirect=true";
	}
	
	public List<Produto> buscaProdutoCodigoNome(String busca){
		listaProduto = new ArrayList<Produto>();
		Produto produtoBusca = new Produto();
		produtoBusca.setDescricao(busca);
		produtoBusca.setCodigo(busca);
		listaProduto = new ProdutoDAO().listaProdutoCodigoNome(produtoBusca);
		if(listaProduto.isEmpty()){
			produtoBusca.getFornecedor().setNome("Cadastrar ");
			listaProduto.add(produtoBusca);
		}
		return listaProduto;
	}
	
	public void planejadoMontagem(ValueChangeEvent event){
		if((Boolean) event.getNewValue()){
			produto.setTemMontagem((Boolean) event.getNewValue());
		}
	}
	
	public LazyDataModel<Produto> listaLazyProdutoGeral(){
		if(listaLazyProduto == null){
			listaLazyProduto = new LazyDataModel<Produto>() {
								private List<Produto> listaLazy;
								
								public Produto getRowData(String idProduto) {
							    	Integer id = Integer.valueOf(idProduto);
							    	
							        for(Produto produto : listaLazy) {
							            if(produto.getId().equals(id))
							                return produto;
							        }
							        
							        return null;
							    }

							    @Override
							    public Object getRowKey(Produto produto) {
							        return produto.getId();
							    }

							    @Override
							    public List<Produto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
							    	produtoDAO = new ProdutoDAO();  
							    	
							    	listaLazy = produtoDAO.listaLazy(first, pageSize, filters);
							    	
							    	if (getRowCount() <= 0) {  
							            setRowCount(produtoDAO.totalProduto(filters));  
							        }  
							       
							        setPageSize(pageSize);  
							        return listaLazy;  
							    }
			};
		}
		return listaLazyProduto;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public LazyDataModel<Produto> getListaLazyProduto() {
		return listaLazyProduto;
	}

	public void setListaLazyProduto(LazyDataModel<Produto> listaLazyProduto) {
		this.listaLazyProduto = listaLazyProduto;
	}
	
}
