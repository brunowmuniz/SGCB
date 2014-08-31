package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
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
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.ExtendedExcelExporter;
import br.com.casabemestilo.util.ExtendedPDFExporter;

@ManagedBean
@ViewScoped
public class ProdutoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto();
	
	private Produto produtoFiltro;
	
	private List<Produto> listaProduto;
	
	private ProdutoDAO produtoDAO;
	
	private LazyDataModel<Produto> listaLazyProduto;

	private Double percentualReajuste;
	
	private Integer quantidade;
	
	private String filtroLocal = "";
	
	private String filtroTemMontagem = "";
	
	private String tipoArquivo = "xls";
	
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
        	logger.info("Salvo produto: " + produto.toString() + " - usuário: " + super.getUsuarioLogado().getNome());
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
        	logger.info("Deletado Produto: " + produto.toString() + " - usuário: " + super.getUsuarioLogado().getNome());
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
        	logger.info("Alterado Produto: " + produto.toString() + " - usuário: " + super.getUsuarioLogado().getNome());
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
		logger.info("Aberto Produto: " + produto.toString() + " - usuário: " + super.getUsuarioLogado().getNome());
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
							    	
							    	listaLazy = produtoDAO.listaLazy(first, pageSize, filters, getProdutoFiltro(), getFiltroLocal(),getFiltroTemMontagem());
							        setRowCount(produtoDAO.totalProduto(filters, getProdutoFiltro(), getFiltroLocal(),getFiltroTemMontagem()));
							       
							        setPageSize(pageSize);  
							        return listaLazy;  
							    }
			};
		}
		return listaLazyProduto;
	}
	
	public void reajustarProdutoFornecedor(ActionEvent actionEvent){		
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	        Usuario usuarioLogado = (Usuario) session.getAttribute("UsuarioLogado");
			produtoDAO = new ProdutoDAO();
			quantidade = produtoDAO.reajustarProdutoFornecedor(produto.getFornecedor().getId(), percentualReajuste, usuarioLogado.getNome());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado " + quantidade + " produto(s)!", ""));
			produto = new Produto();
			percentualReajuste = null;
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "- reajuste de produto!");
		}catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "- reajuste de produto!");
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("Erro Geral: " + super.mensagem + "- reajuste de produto!");
		} 
	}
	
	public void verificaQtdeProdutoAjuste(){		
		produtoDAO = new ProdutoDAO();
		quantidade = produtoDAO.buscaQtdeProdutoReajuste(produto.getFornecedor().getId());			
	}
	
	public void buscarFiltro(){
		listaLazyProdutoGeral();
	}
	
	public void exportarArquivo(DataTable tabela, String nomeArquivo, String tipoArquivo) throws IOException{
		this.tipoArquivo = tipoArquivo;
		Exporter exporter = null;
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
		nomeArquivo += df.format(new Date()) + "@";
		
		if(filtroLocal.equals("")) nomeArquivo += "TODOS";
		if(filtroLocal.equals("1")) nomeArquivo += "Em Estoque";
		if(filtroLocal.equals("2")) nomeArquivo += "Showroom";
		if(filtroLocal.equals("3")) nomeArquivo += "Encomenda";
		if(filtroLocal.equals("1,2")) nomeArquivo += "Em Estoque + Encomenda";
		if(filtroLocal.equals("2,3")) nomeArquivo += "Encomenda + Showroom";
		if(filtroLocal.equals("1,3")) nomeArquivo += "Em Estoque + Encomenda";
		
		if(!getProdutoFiltro().getCodigo().equals("")) nomeArquivo += "@" + getProdutoFiltro().getCodigo();
		
		if(!filtroTemMontagem.equals("")){
			if(filtroTemMontagem.equals("false")){
				nomeArquivo += "@" + "Sem Montagem";
			}else{
				nomeArquivo += "@" + "Com Montagem";
			}
		}else{
			nomeArquivo += "@Com e Sem Montagem";
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(tipoArquivo.equals("xls")){
			exporter = new ExtendedExcelExporter();
		}else{
			exporter = new ExtendedPDFExporter();
		}	    
	    exporter.export(context,tabela, nomeArquivo, false, false, "ISO-8859-1", null, null);
	    context.responseComplete();
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
	
	public Double getPercentualReajuste() {
		if(percentualReajuste == null){
			percentualReajuste = new Double("0");
		}
		return percentualReajuste;
	}

	public void setPercentualReajuste(Double percentualReajuste) {
		this.percentualReajuste = percentualReajuste;
	}

	public Integer getQuantidade() {
		if(quantidade == null){
			quantidade = 0;
		}
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getFiltroLocal() {
		return filtroLocal;
	}

	public void setFiltroLocal(String filtroLocal) {
		this.filtroLocal = filtroLocal;
	}

	public Produto getProdutoFiltro() {
		if(produtoFiltro == null){
			produtoFiltro = new Produto();
			produtoFiltro.setCodigo("");
		}
		return produtoFiltro;
	}

	public void setProdutoFiltro(Produto produtoFiltro) {
		this.produtoFiltro = produtoFiltro;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getFiltroTemMontagem() {
		return filtroTemMontagem;
	}

	public void setFiltroTemMontagem(String filtroTemMontagem) {
		this.filtroTemMontagem = filtroTemMontagem;
	}
	
}
