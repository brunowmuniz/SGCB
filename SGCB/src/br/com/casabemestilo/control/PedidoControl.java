package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.DAO.PedidoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Pedido;

@ManagedBean
@ViewScoped
public class PedidoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	
	private List<Pedido> listaPedido;
	
	private PedidoDAO pedidoDAO;
	
	private LazyDataModel<Pedido> listaPedidoGeral;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoControl(String messagem, Pedido pedido,
			List<Pedido> listaPedido, PedidoDAO pedidoDAO) {
		super(messagem);
		this.pedido = pedido;
		this.listaPedido = listaPedido;
		this.pedidoDAO = pedidoDAO;
	}

	public PedidoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PedidoControl() {
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
	public List<Pedido> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pedido buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyDataModel<Pedido> getListaPedidoGeralAll(){
		if(listaPedidoGeral == null){
			listaPedidoGeral = new LazyDataModel<Pedido>() {
				 private List<Pedido> listaLazyPedido;
				 
				 public Pedido getRowData(String idPedido) {
				    	Integer id = Integer.valueOf(idPedido);
				    	
				        for(Pedido pedido : listaLazyPedido) {
				            if(pedido.getId().equals(id))
				                return pedido;
				        }
				        
				        return null;
				    }

				    @Override
				    public Object getRowKey(Pedido pedido) {
				        return pedido.getId();
				    }

				    @Override
				    public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
				    	PedidoDAO pedidoDAO = new PedidoDAO();
				    	listaLazyPedido = pedidoDAO.listaPedidos(first, pageSize);
				    	if (getRowCount() <= 0) {  
				            setRowCount(pedidoDAO.totalPedidos());  
				        }  
				        // set the page dize  
				        setPageSize(pageSize);  
				        return listaLazyPedido;  
				    }
			};
		}
		return listaPedidoGeral;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public PedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(PedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

	public LazyDataModel<Pedido> getListaPedidoGeral() {
		return listaPedidoGeral;
	}

	public void setListaPedidoGeral(LazyDataModel<Pedido> listaPedidoGeral) {
		this.listaPedidoGeral = listaPedidoGeral;
	}
	
	

}
