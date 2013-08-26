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

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Usuario;

@ManagedBean
@ViewScoped
public class FreteControl extends Control implements Serializable,
		InterfaceControl {

	
	private static final long serialVersionUID = 1L;
	
	private Frete frete;
	
	private List<Frete> listaFrete;
	
	private FreteDAO freteDAO;

	private List<Integer> listaMontadores = new ArrayList<Integer>();
	
	private LazyDataModel<Frete> listaFreteGeral;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private Float totalLoja;
	
	private Float totalBrinde;
	
	private Float totalLocal;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public FreteControl(String messagem, Frete frete, List<Frete> listaFrete,
			FreteDAO freteDAO) {
		super(messagem);
		this.frete = frete;
		this.listaFrete = listaFrete;
		this.freteDAO = freteDAO;
	}

	public FreteControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public FreteControl() {
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
	public List<Frete> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Frete buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public Frete gravarFreteOcProduto(List<Ocproduto> listaOcprodutos, List<Integer> listaMontadores) {				
		try {
			freteDAO = new FreteDAO();
			frete = new Frete();
			frete.setDatainicio(new Date());
			frete.setDatafim(new Date());
			frete.setOcprodutos(listaOcprodutos);		
			frete.setObservacoes("Frete " + listaOcprodutos.get(0).getOc().getTipoFrete());
			frete.setValor(listaOcprodutos.get(0).getOc().getValorfrete());
			for (int i = 0; i < listaMontadores.size(); i++) {
				frete.setFreteiro(frete.getFreteiro().concat((String) (i==0 ? listaMontadores.get(i) : "," + listaMontadores.get(i))));
			}			
			frete = freteDAO.insertFrete(frete);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Frete gerado para os produtos!"));
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return frete;
	}
	
	public LazyDataModel<Frete> getListaFreteGeralAll(){
		if(listaFreteGeral == null){
			listaFreteGeral = new LazyDataModel<Frete>() {
				private List<Frete> listaLazyFrete;
				 
				 public Frete getRowData(String idFrete) {
				    	Integer id = Integer.valueOf(idFrete);
				    	
				        for(Frete frete : listaLazyFrete) {
				            if(frete.getId().equals(id))
				                return frete;
				        }
				        
				        return null;
				    }

				    @Override
				    public Object getRowKey(Frete frete) {
				        return frete.getId();
				    }

				    @Override
				    public List<Frete> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
				    	freteDAO = new FreteDAO();
				    	listaLazyFrete = freteDAO.listaFrete(first, pageSize, getDataInicial(),getDataFinal());
				    	if (getRowCount() <= 0) {  
				            setRowCount(freteDAO.totalFrete(getDataInicial(),getDataFinal()));
				        }
				    	setListaFrete(freteDAO.listaFrete(0, getRowCount(), getDataInicial(),getDataFinal()));				    	
				    	calculaValoresFrete();
				        setPageSize(pageSize);  
				        return listaLazyFrete;  
				    }
			};
		}
		return listaFreteGeral;
	}
	
	public void pesquisaFrete(){
		getListaFreteGeralAll();
	}
	
	public List<Ocproduto> listaOcprodutoFrete(Integer idFrete){
		List<Ocproduto> listaOcProdutoFrete = new ArrayList<Ocproduto>();
		listaOcProdutoFrete = new FreteDAO().buscaOcProdutoFrete(idFrete);
		return listaOcProdutoFrete;
	} 

	public void calculaValoresFrete(){
		setTotalLocal(new Float(0));
		setTotalLoja(new Float(0));
		setTotalBrinde(new Float(0));
		
		for(Frete frete : getListaFrete()){
			if(frete.getObservacoes().indexOf("Local") != -1){
				setTotalLocal(getTotalLocal() + frete.getValor());
			}else if(frete.getObservacoes().indexOf("Loja") != -1){
				setTotalLoja(getTotalLoja() + frete.getValor());
			}else{
				setTotalBrinde(getTotalBrinde() + frete.getValor());
			}
		}
	} 
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Frete getFrete() {
		if(frete == null){
			frete = new Frete();
		}
		return frete;
	}

	public void setFrete(Frete frete) {
		this.frete = frete;
	}

	public List<Frete> getListaFrete() {
		return listaFrete;
	}

	public void setListaFrete(List<Frete> listaFrete) {
		this.listaFrete = listaFrete;
	}

	public FreteDAO getFreteDAO() {
		return freteDAO;
	}

	public void setFreteDAO(FreteDAO freteDAO) {
		this.freteDAO = freteDAO;
	}

	public List<Integer> getListaMontadores() {
		return listaMontadores;
	}

	public void setListaMontadores(List<Integer> listaMontadores) {
		this.listaMontadores = listaMontadores;
	}

	public LazyDataModel<Frete> getListaFreteGeral() {
		return listaFreteGeral;
	}

	public void setListaFreteGeral(LazyDataModel<Frete> listaFreteGeral) {
		this.listaFreteGeral = listaFreteGeral;
	}

	public Date getDataInicial() {
		if(dataInicial == null){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_MONTH, -1);
			dataInicial = c.getTime();
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

	public Float getTotalLoja() {
		if(totalLoja == null){
			totalLoja = new Float(0);
		}
		return totalLoja;
	}

	public void setTotalLoja(Float totalLoja) {
		this.totalLoja = totalLoja;
	}

	public Float getTotalBrinde() {
		if(totalBrinde == null){
			totalBrinde = new Float(0);
		}
		return totalBrinde;
	}

	public void setTotalBrinde(Float totalBrinde) {
		this.totalBrinde = totalBrinde;
	}

	public Float getTotalLocal() {
		if(totalLocal == null){
			totalLocal = new Float(0);
		}
		return totalLocal;
	}

	public void setTotalLocal(Float totalLocal) {
		this.totalLocal = totalLocal;
	}
	
}
