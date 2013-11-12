package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.PedidoProdutoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;

@ManagedBean
@ViewScoped
public class PedidoProdutoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedidoproduto pedidoProduto;
	
	private List<Pedidoproduto> listaPedidoProduto;
	
	private LazyDataModel<Pedidoproduto> listaPedidoProdutoRede;
	
	private PedidoProdutoDAO pedidoProdutoDAO;
	
	private Date dataInicial;
	
	private Date dataFinal;

	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoProdutoControl(String messagem, Pedidoproduto pedidoProduto,
			List<Pedidoproduto> listaPedidoProduto,
			PedidoProdutoDAO pedidoProdutoDAO) {
		super(messagem);
		this.pedidoProduto = pedidoProduto;
		this.listaPedidoProduto = listaPedidoProduto;
		this.pedidoProdutoDAO = pedidoProdutoDAO;
	}

	public PedidoProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PedidoProdutoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {
		// TODO Auto-generated method stub

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
	public List<Pedidoproduto> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyDataModel<Pedidoproduto> getListaPedidoProdutoRedeAll(){
		if(listaPedidoProdutoRede == null){
			listaPedidoProdutoRede = new LazyDataModel<Pedidoproduto>() {
						 private List<Pedidoproduto> listaLazyPedidoProdutoRede;
						 
						 public Pedidoproduto getRowData(String idPedidoproduto) {
						    	Integer id = Integer.valueOf(idPedidoproduto);
						    	
						        for(Pedidoproduto pedidoproduto : listaLazyPedidoProdutoRede) {
						            if(pedidoproduto.getId().equals(id))
						                return pedidoproduto;
						        }
						        
						        return null;
						    }
		
						    @Override
						    public Object getRowKey(Pedidoproduto pedidoproduto) {
						        return pedidoproduto.getId();
						    }
		
						    @Override
						    public List<Pedidoproduto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
						    	pedidoProdutoDAO = new PedidoProdutoDAO();
						    	listaLazyPedidoProdutoRede = pedidoProdutoDAO.listaPedidoProdutoRede(getDataFinal(),getDataInicial(),first,pageSize);
						    		
					    		if (getRowCount() <= 0) {  
						            setRowCount(pedidoProdutoDAO.totalPedidoProdutoRede(getDataInicial(),getDataFinal()));  
						        }
						    	  
						        // set the page dize  
						        setPageSize(pageSize);  
						        return listaLazyPedidoProdutoRede;  
						    }
					};
		}
		return listaPedidoProdutoRede;
	}
	
	public LazyDataModel<Pedidoproduto> getListaPedidoFornecedores(){
		if(listaPedidoProdutoRede == null){
			listaPedidoProdutoRede = new LazyDataModel<Pedidoproduto>() {
						 private List<Pedidoproduto> listaLazyPedidoProdutoRede;
						 
						 public Pedidoproduto getRowData(String idPedidoproduto) {
						    	Integer id = Integer.valueOf(idPedidoproduto);
						    	
						        for(Pedidoproduto pedidoproduto : listaLazyPedidoProdutoRede) {
						            if(pedidoproduto.getId().equals(id))
						                return pedidoproduto;
						        }
						        
						        return null;
						    }
		
						    @Override
						    public Object getRowKey(Pedidoproduto pedidoproduto) {
						        return pedidoproduto.getId();
						    }
		
						    @Override
						    public List<Pedidoproduto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
						    	pedidoProdutoDAO = new PedidoProdutoDAO();
						    	listaLazyPedidoProdutoRede = pedidoProdutoDAO.listaPedidoFornecedores(getDataFinal(),getDataInicial(),first,pageSize);
						    		
					    		if (getRowCount() <= 0) {  
						            setRowCount(pedidoProdutoDAO.totalPedidoFornecedores(getDataInicial(),getDataFinal()));  
						        }
						    	  
						        // set the page dize  
						        setPageSize(pageSize);  
						        return listaLazyPedidoProdutoRede;  
						    }
					};
		}
		return listaPedidoProdutoRede;
	}
	
	public void pesquisaParceirosRede(){
		getListaPedidoProdutoRedeAll();
	}
	
	public void pesquisaPedidosFornecedores(){
		getListaPedidoFornecedores();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	@Override
	public Pedidoproduto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pedidoproduto getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(Pedidoproduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public List<Pedidoproduto> getListaPedidoProduto() {
		return listaPedidoProduto;
	}

	public void setListaPedidoProduto(List<Pedidoproduto> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}

	public PedidoProdutoDAO getPedidoProdutoDAO() {
		return pedidoProdutoDAO;
	}

	public void setPedidoProdutoDAO(PedidoProdutoDAO pedidoProdutoDAO) {
		this.pedidoProdutoDAO = pedidoProdutoDAO;
	}

	public LazyDataModel<Pedidoproduto> getListaPedidoProdutoRede() {
		return listaPedidoProdutoRede;
	}

	public void setListaPedidoProdutoRede(
			LazyDataModel<Pedidoproduto> listaPedidoProdutoRede) {
		this.listaPedidoProdutoRede = listaPedidoProdutoRede;
	}

	public Date getDataInicial() {
		if(dataInicial == null){
			dataInicial = new Date();
		}
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		if(dataFinal == null){
			dataFinal = new Date();
		}
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}	
	
}

