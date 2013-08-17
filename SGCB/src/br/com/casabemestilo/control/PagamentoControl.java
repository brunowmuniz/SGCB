package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagamento;

@ManagedBean
@ViewScoped
public class PagamentoControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Pagamento pagamento;
	
	private List<Pagamento> listaPagamento;
	
	private PagamentoDAO pagamentoDAO;
	
	private LazyDataModel<Pagamento> listarPagamentoGeral;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PagamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PagamentoControl() {
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
	public <T> List<T> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LazyDataModel<Pagamento> getListarPagamentosAll(){
		if(listarPagamentoGeral == null){
			listarPagamentoGeral = new LazyDataModel<Pagamento>() {
									 private List<Pagamento> listaLazyPagamento;
									 
									 public Pagamento getRowData(String idOc) {
									    	Integer id = Integer.valueOf(idOc);
									    	
									        for(Pagamento pagamento : listaLazyPagamento) {
									            if(pagamento.getId().equals(id))
									                return pagamento;
									        }
									        
									        return null;
									    }
					
									    @Override
									    public Object getRowKey(Pagamento pagamento) {
									        return pagamento.getId();
									    }
					
									    @Override
									    public List<Pagamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
									    	PagamentoDAO pagamentoDAO = new PagamentoDAO();
									    	listaLazyPagamento = pagamentoDAO.listaPagamentosAVencer(first, pageSize);
									    	if (getRowCount() <= 0) {  
									            setRowCount(pagamentoDAO.totalPagamentosAVencer());  
									        }  
									        // set the page dize  
									        setPageSize(pageSize);  
									        return listaLazyPagamento;  
									    }
								};
		}
		return listarPagamentoGeral;
	}

	/*
	 * GETTERS & SETTERS
	 * */
	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public List<Pagamento> getListaPagamento() {
		return listaPagamento;
	}

	public void setListaPagamento(List<Pagamento> listaPagamento) {
		this.listaPagamento = listaPagamento;
	}

	public PagamentoDAO getPagamentoDAO() {
		return pagamentoDAO;
	}

	public void setPagamentoDAO(PagamentoDAO pagamentoDAO) {
		this.pagamentoDAO = pagamentoDAO;
	}

	public LazyDataModel<Pagamento> getListarPagamentoGeral() {
		return listarPagamentoGeral;
	}

	public void setListarPagamentoGeral(
			LazyDataModel<Pagamento> listarPagamentoGeral) {
		this.listarPagamentoGeral = listarPagamentoGeral;
	}
	
	

}
