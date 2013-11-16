package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.DAO.PedidoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.Status;

@ManagedBean
@ViewScoped
public class OcProdutoControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Ocproduto ocproduto;
	
	private List<Ocproduto> listaOcproduto;
	
	private OcProdutoDAO ocProdutoDAO;
	
	private LazyDataModel<Ocproduto> listaOcProdutoGeral;
	
	private Fornecedor fornecedor;
	
	private List<Ocproduto> listaOutrosProdOc;
	
	private List<Ocproduto> listaOcProdutoAcao = new ArrayList<Ocproduto>();
	
	
	/*
	 * CONSTRUTORES
	 * */
	public OcProdutoControl(String messagem, Ocproduto ocproduto,
			List<Ocproduto> listaOcproduto, OcProdutoDAO ocProdutoDAO) {
		super(messagem);
		this.ocproduto = ocproduto;
		this.listaOcproduto = listaOcproduto;
		this.ocProdutoDAO = ocProdutoDAO;
	}

	public OcProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public OcProdutoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * M�TODOS
	 * */	
	@Override
	public void gravar() {
		
	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Ocproduto> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ocproduto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void limparProduto(){
		ocproduto.setProduto(new Produto());
	}
	
	public void limparOcProduto(){
		ocproduto = new Ocproduto();
		ocproduto.setProduto(new Produto());
	}
	
	public LazyDataModel<Ocproduto> getListaProdutosEncomendaAll(){
		if(listaOcProdutoGeral == null){
			listaOcProdutoGeral = new LazyDataModel<Ocproduto>() {
									 private List<Ocproduto> listaLazyOcProduto;
									 
									 public Ocproduto getRowData(String idOcProduto) {
									    	Integer id = Integer.valueOf(idOcProduto);
									    	
									        for(Ocproduto ocproduto : listaLazyOcProduto) {
									            if(ocproduto.getId().equals(id))
									                return ocproduto;
									        }
									        
									        return null;
									    }
					
									    @Override
									    public Object getRowKey(Ocproduto ocproduto) {
									        return ocproduto.getId();
									    }
					
									    @Override
									    public List<Ocproduto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
									    	OcProdutoDAO ocProdutoDAO = new OcProdutoDAO();
									    	if(getFornecedor().getId() != null && getFornecedor().getId() != 0){
									    		listaLazyOcProduto = ocProdutoDAO.listaProdutosAEncomendarFornecedor(first, pageSize,fornecedor);
									    		
									    		if (getRowCount() <= 0) {  
										            setRowCount(ocProdutoDAO.totalProdutosAEncomendarFornecedor(fornecedor));  
										        }
									    	}else{
									    		listaLazyOcProduto = ocProdutoDAO.listaProdutosAEncomendarTodos(first, pageSize);
									    		
									    		if (getRowCount() <= 0) {  
										            setRowCount(ocProdutoDAO.totalProdutosAEncomendarTodos());  
										        }
									    	}
									    	
									    	  
									        // set the page dize  
									        setPageSize(pageSize);  
									        return listaLazyOcProduto;  
									    }
								};
		}
		return listaOcProdutoGeral;
	}
	
	public void gravarPedido(){
		PedidoDAO pedidoDAO = new PedidoDAO();
		OcDAO ocDAO = new OcDAO();
		List<Pedidoproduto> pedidoprodutos = new ArrayList<Pedidoproduto>();
		Pedido pedido = new Pedido();		
		ocProdutoDAO = new OcProdutoDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		try {			
			pedido.setDatasolicitacao(new Date());	
			pedido.getFornecedor().setId(fornecedor.getId());
			for(Ocproduto ocproduto : listaOcproduto){				
				Pedidoproduto pedidoproduto = new Pedidoproduto();
				pedidoproduto.setProduto(ocproduto.getProduto());
				pedidoproduto.setQuantidade(ocproduto.getQuantidade());
				pedidoproduto.setPedido(pedido);
				pedidoproduto.setOcproduto(ocproduto);
				pedidoprodutos.add(pedidoproduto);
				
			}			
			pedido.setPedidoprodutos(pedidoprodutos);
			pedidoDAO.insert(pedido);
			fornecedor = new FornecedoresDAO().buscaObjetoId(fornecedor.getId());
			for(Ocproduto ocproduto : listaOcproduto){
				ocproduto.getStatus().setId(4);
				ocProdutoDAO.update(ocproduto);
				ocproduto.getOc().setStatus(retornaMenorStatusOcProduto(ocproduto.getOc(), true));
				ocDAO.update(ocproduto.getOc());
			}
			for(Ocproduto ocproduto : listaOcproduto){
				Produto produto = ocproduto.getProduto();
				produto.setEncomenda(produto.getEncomenda() - ocproduto.getQuantidade());
				produtoDAO.update(produto);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido para o fornecedor " + fornecedor.getNome() + " foi gravado!"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alterado o status do produto para 'Pendente de Chegada'"));
			logger.info("Pedido: " + pedido.getId() + " foi gravado");
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "n�o foi gravado!");
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[gravar_pedido] Erro Hibernate: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "n�o foi gravado!");
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
			logger.error("[gravar_pedido] Erro Gen�rico: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "n�o foi gravado!");
		}
		
		
	}
	
	public void addProdutoPedido(SelectEvent selectEvent){
		listaOcproduto.add((Ocproduto) selectEvent.getObject());
	}
	
	public void subProdutoPedido(UnselectEvent unselectEvent){
		listaOcproduto.remove((Ocproduto) unselectEvent.getObject());
	}
	
	public void verOutrosProdutosOc(Ocproduto ocproduto){
		ocProdutoDAO = new OcProdutoDAO();
		listaOutrosProdOc = ocProdutoDAO.buscaOcProdutoOc(ocproduto);
	}
	
	public Status retornaMenorStatusOcProduto(Oc oc, Boolean buscaBanco){
		Status status = new Status();
		listaOcproduto = new ArrayList<Ocproduto>();
		if(buscaBanco){
			ocProdutoDAO = new OcProdutoDAO();
			listaOcproduto = ocProdutoDAO.buscaOcProdutoPorOc(oc);
		}else{
			listaOcproduto = oc.getOcprodutos();
		}
		status = listaOcproduto.get(0).getStatus();
		for(Ocproduto ocproduto : listaOcproduto){
			if(status.getId() > ocproduto.getStatus().getId()){
				status = ocproduto.getStatus();
			}
		}
		return status;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Ocproduto getOcproduto() {
		if(ocproduto == null){
			ocproduto = new Ocproduto();
		}
		return ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}

	public List<Ocproduto> getListaOcproduto() {
		return listaOcproduto;
	}

	public void setListaOcproduto(List<Ocproduto> listaOcproduto) {
		this.listaOcproduto = listaOcproduto;
	}

	public OcProdutoDAO getOcProdutoDAO() {
		return ocProdutoDAO;
	}

	public void setOcProdutoDAO(OcProdutoDAO ocProdutoDAO) {
		this.ocProdutoDAO = ocProdutoDAO;
	}

	public LazyDataModel<Ocproduto> getListaOcProdutoGeral() {
		return listaOcProdutoGeral;
	}

	public void setListaOcProdutoGeral(LazyDataModel<Ocproduto> listaOcProdutoGeral) {
		this.listaOcProdutoGeral = listaOcProdutoGeral;
	}

	public Fornecedor getFornecedor() {
		if(fornecedor == null){
			fornecedor = new Fornecedor();
		}
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Ocproduto> getListaOutrosProdOc() {
		return listaOutrosProdOc;
	}

	public void setListaOutrosProdOc(List<Ocproduto> listaOutrosProdOc) {
		this.listaOutrosProdOc = listaOutrosProdOc;
	}

	public List<Ocproduto> getListaOcProdutoAcao() {
		return listaOcProdutoAcao;
	}

	public void setListaOcProdutoAcao(List<Ocproduto> listaOcProdutoAcao) {
		this.listaOcProdutoAcao = listaOcProdutoAcao;
	}
	
}