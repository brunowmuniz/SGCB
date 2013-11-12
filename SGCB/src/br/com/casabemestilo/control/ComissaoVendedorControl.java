package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import br.com.casabemestilo.DAO.ComissaoVendedorDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.ComissaoVendedor;

@ManagedBean
@ViewScoped
public class ComissaoVendedorControl extends Control implements  Serializable, InterfaceControl{
	
	private List<ComissaoVendedor> listaComissaoVendedores;
	
	private ComissaoVendedor comissaoVendedor;
	
	private ComissaoVendedorDAO comissaoVendedorDAO;

	private Date dataInicial;
	
	private Date dataFinal;
	
	private LazyDataModel<ComissaoVendedor> listarComissaoVendedorGeral;
	
	private Float totalComissaoVendedor;
	
	/*
	 * CONSTRUTOR
	 * */
	public ComissaoVendedorControl(String messagem,
			List<ComissaoVendedor> listaComissaoVendedores,
			ComissaoVendedor comissaoVendedor,
			ComissaoVendedorDAO comissaoVendedorDAO) {
		super(messagem);
		this.listaComissaoVendedores = listaComissaoVendedores;
		this.comissaoVendedor = comissaoVendedor;
		this.comissaoVendedorDAO = comissaoVendedorDAO;
	}
	
	public ComissaoVendedorControl() {
		listaComissaoVendedores = new ArrayList<ComissaoVendedor>();
		comissaoVendedor = new ComissaoVendedor();
		comissaoVendedorDAO = new ComissaoVendedorDAO();
	}

	public ComissaoVendedorControl(String messagem) {
		super(messagem);
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
	
	public LazyDataModel<ComissaoVendedor> getListarVendasAnalVendedor(){
		if(listarComissaoVendedorGeral == null){
			listarComissaoVendedorGeral = new LazyDataModel<ComissaoVendedor>() {
									private List<ComissaoVendedor> listaLazyComissaoVendedor;
									
									@Override
								    public ComissaoVendedor getRowData(String idOc) {
								    	Integer id = Integer.valueOf(idOc);
								    	
								        for(ComissaoVendedor comissaoVendedor : listaLazyComissaoVendedor) {
								            if(comissaoVendedor.getId().equals(id))
								            	return comissaoVendedor;
								        }								        
								        return null;
								    }
	
								    @Override
								    public Object getRowKey(ComissaoVendedor comissaoVendedor) {
								        return comissaoVendedor.getId();
								    }
	
								    @Override
								    public List<ComissaoVendedor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
								    	comissaoVendedorDAO = new ComissaoVendedorDAO();						    	
								    								    		
							    		setTotalComissaoVendedor(comissaoVendedorDAO.calculaComissaoVendedor(getComissaoVendedor().getVendedor().getId(), getDataInicial(), getDataFinal()));
							    		
							    		listaLazyComissaoVendedor = comissaoVendedorDAO.listaLazyVendedorAnalitico(first, pageSize, getComissaoVendedor().getVendedor().getId(), getDataInicial(), getDataFinal());								    	
								    	if (getRowCount() <= 0) {  
								            setRowCount(comissaoVendedorDAO.totalVendedorAnalitico(getComissaoVendedor().getVendedor().getId(), getDataInicial(), getDataFinal()));  
								        }  
								    	
								        setPageSize(pageSize);
								        
								        return listaLazyComissaoVendedor;  
								    }
					};
		}		
		return listarComissaoVendedorGeral;
	}
	
	public void buscaVendasVendedorAnalitico(){		
		listarComissaoVendedorGeral = null;
		getListarVendasAnalVendedor();
		if(listarComissaoVendedorGeral == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Vendedor sem comissão para o período!",""));	
		}
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<ComissaoVendedor> getListaComissaoVendedores() {
		return listaComissaoVendedores;
	}

	public void setListaComissaoVendedores(
			List<ComissaoVendedor> listaComissaoVendedores) {
		this.listaComissaoVendedores = listaComissaoVendedores;
	}

	public ComissaoVendedor getComissaoVendedor() {
		return comissaoVendedor;
	}

	public void setComissaoVendedor(ComissaoVendedor comissaoVendedor) {
		this.comissaoVendedor = comissaoVendedor;
	}

	public ComissaoVendedorDAO getComissaoVendedorDAO() {
		return comissaoVendedorDAO;
	}

	public void setComissaoVendedorDAO(ComissaoVendedorDAO comissaoVendedorDAO) {
		this.comissaoVendedorDAO = comissaoVendedorDAO;
	}

	public Date getDataInicial() {
		if(dataInicial == null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH,-30);
			dataInicial = calendar.getTime();			
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

	public LazyDataModel<ComissaoVendedor> getListarComissaoVendedorGeral() {
		return listarComissaoVendedorGeral;
	}

	public void setListarComissaoVendedorGeral(
			LazyDataModel<ComissaoVendedor> listarComissaoVendedorGeral) {
		this.listarComissaoVendedorGeral = listarComissaoVendedorGeral;
	}

	public Float getTotalComissaoVendedor() {
		return totalComissaoVendedor;
	}

	public void setTotalComissaoVendedor(Float totalComissaoVendedor) {
		this.totalComissaoVendedor = totalComissaoVendedor;
	}
	
}
