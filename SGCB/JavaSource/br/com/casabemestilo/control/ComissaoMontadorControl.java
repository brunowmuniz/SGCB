package br.com.casabemestilo.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.ComissaoMontadorDAO;
import br.com.casabemestilo.DAO.ComissaoVendedorDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.ComissaoMontador;
import br.com.casabemestilo.model.ComissaoVendedor;

@ManagedBean
@ViewScoped
public class ComissaoMontadorControl extends Control implements InterfaceControl {

	private ComissaoMontador comissaoMontador;
	
	private List<ComissaoMontador> comissaoMontadores;
	
	private ComissaoMontadorDAO comissaoMontadorDAO;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private LazyDataModel<ComissaoMontador> listaComissaoMontadorGeral;
	
	private Float totalComissaoMontador;
	
	/*
	 * CONSTRUTORES
	 * */
	public ComissaoMontadorControl(String messagem,
			ComissaoMontador comissaoMontador,
			List<ComissaoMontador> comissaoMontadores,
			ComissaoMontadorDAO comissaoMontadorDAO) {
		super(messagem);
		this.comissaoMontador = comissaoMontador;
		this.comissaoMontadores = comissaoMontadores;
		this.comissaoMontadorDAO = comissaoMontadorDAO;
	}

	public ComissaoMontadorControl() {
		super();
	}
	
	public ComissaoMontadorControl(String messagem) {
		super(messagem);
	}


	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {		

	}

	@Override
	public void deletar() {		

	}

	@Override
	public void alterar() {		

	}

	@Override
	public List<ComissaoMontador> listarAtivos() {
		return null;
	}

	@Override
	public List<ComissaoMontador> listarTodos() {		
		return null;
	}

	@Override
	public List<ComissaoMontador> listaSelecao(Object obj) {		
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {		
		return null;
	}
	
	public LazyDataModel<ComissaoMontador> getListarComissaoMontador(){
		if(listaComissaoMontadorGeral == null){
						listaComissaoMontadorGeral = new LazyDataModel<ComissaoMontador>() {
							private List<ComissaoMontador> listaLazyComissaoMontador;
							
							@Override
						    public ComissaoMontador getRowData(String idComissaoMontador) {
						    	Integer id = Integer.valueOf(idComissaoMontador);
						    	
						        for(ComissaoMontador comissaoMontador : listaLazyComissaoMontador) {
						            if(comissaoMontador.getId().equals(id))
						            	return comissaoMontador;
						        }								        
						        return null;
						    }
			
						    @Override
						    public Object getRowKey(ComissaoMontador comissaoMontador) {
						        return comissaoMontador.getId();
						    }
			
						    @Override
						    public List<ComissaoMontador> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
						    	comissaoMontadorDAO = new ComissaoMontadorDAO();						    	
						    								    		
					    		setTotalComissaoMontador(comissaoMontadorDAO.calculaComissaoMontador(getComissaoMontador().getMontador().getId(), getDataInicial(), getDataFinal()));
					    		
					    		listaLazyComissaoMontador = comissaoMontadorDAO.listaLazyMontadorAnalitico(first, pageSize, getComissaoMontador().getMontador().getId(), getDataInicial(), getDataFinal());								    	
						    	if (getRowCount() <= 0) {  
						            setRowCount(comissaoMontadorDAO.totalMontadorAnalitico(getComissaoMontador().getMontador().getId(), getDataInicial(), getDataFinal()));  
						        }  
						    	
						        setPageSize(pageSize);
						        
						        return listaLazyComissaoMontador;  
						    }
			};
		}
		return listaComissaoMontadorGeral;
	}

	public void buscaComissaoMontadorAnalitico(){
		listaComissaoMontadorGeral = null;
		getListarComissaoMontador();
		if(listaComissaoMontadorGeral == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Montador sem comissão para o período!",""));	
		}
	}
	

	/*
	 * GETTERS & SETTERS
	 * */
	public ComissaoMontador getComissaoMontador() {
		if(comissaoMontador == null){
			comissaoMontador = new ComissaoMontador();
		}
		return comissaoMontador;
	}

	public void setComissaoMontador(ComissaoMontador comissaoMontador) {
		this.comissaoMontador = comissaoMontador;
	}

	public List<ComissaoMontador> getComissaoMontadores() {
		return comissaoMontadores;
	}

	public void setComissaoMontadores(List<ComissaoMontador> comissaoMontadores) {
		this.comissaoMontadores = comissaoMontadores;
	}

	public ComissaoMontadorDAO getComissaoMontadorDAO() {
		return comissaoMontadorDAO;
	}

	public void setComissaoMontadorDAO(ComissaoMontadorDAO comissaoMontadorDAO) {
		this.comissaoMontadorDAO = comissaoMontadorDAO;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LazyDataModel<ComissaoMontador> getListaComissaoMontadorGeral() {
		return listaComissaoMontadorGeral;
	}

	public void setListaComissaoMontadorGeral(
			LazyDataModel<ComissaoMontador> listaComissaoMontadorGeral) {
		this.listaComissaoMontadorGeral = listaComissaoMontadorGeral;
	}

	public Float getTotalComissaoMontador() {
		return totalComissaoMontador;
	}

	public void setTotalComissaoMontador(Float totalComissaoMontador) {
		this.totalComissaoMontador = totalComissaoMontador;
	}
	
	
}
