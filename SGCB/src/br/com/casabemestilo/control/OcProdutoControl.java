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

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.DAO.PedidoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;
import br.com.casabemestilo.model.Produto;

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
	 * MÉTODOS
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
		List<Pedidoproduto> pedidoprodutos = new ArrayList<Pedidoproduto>();
		Pedido pedido = new Pedido();		
		ocProdutoDAO = new OcProdutoDAO();
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
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido para o fornecedor " + fornecedor.getNome() + " foi gravado!"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alterado o status do produto para 'Pendente de Chegada'"));
			logger.info("Pedido: " + pedido.getId() + " foi gravado");
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "não foi gravado!");
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			e.printStackTrace();
			logger.error("[gravar_pedido] Erro Hibernate: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "não foi gravado!");
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			e.printStackTrace();
			logger.error("[gravar_pedido] Erro Genérico: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + "para o fornecedor " + fornecedor.getNome() + "não foi gravado!");
		}
		
		
	}
	
	public void addProdutoPedido(SelectEvent selectEvent){
		listaOcproduto.add((Ocproduto) selectEvent.getObject());
	}
	
	public void subProdutoPedido(UnselectEvent unselectEvent){
		listaOcproduto.remove((Ocproduto) unselectEvent.getObject());
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
	
}
