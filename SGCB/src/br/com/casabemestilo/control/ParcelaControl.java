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
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.Parcela;


@ManagedBean
@ViewScoped
public class ParcelaControl extends Control implements InterfaceControl,
		Serializable {


	private static final long serialVersionUID = 1L;
	
	private Parcela parcela;
	
	private List<Parcela> listaParcela;
	
	private ParcelaDAO parcelaDAO;
	
	private LazyDataModel<Parcela> listaParcelaGeral;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private List listaStatusCheque = new ArrayList();
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ParcelaControl(String messagem, Parcela parcela,
			List<Parcela> listaParcela, ParcelaDAO parcelaDAO) {
		super(messagem);
		this.parcela = parcela;
		this.listaParcela = listaParcela;
		this.parcelaDAO = parcelaDAO;
	}

	public ParcelaControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public ParcelaControl() {
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
		parcelaDAO = new ParcelaDAO();
		try {
			parcelaDAO.update(parcela);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OC: " + parcela.getPagamento().getOc().getId() + " - " + "Parcela: " + parcela.getNumeroParcela() + " foi alterada!"));
			logger.info("OC: " + parcela.getPagamento().getOc().getId() + "-" + "Parcela: " + parcela.getNumeroParcela() + " foi alterada!");
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar] Erro Constraint: " + super.mensagem + "-" + parcela.getPagamento().getOc().getId() + "-" + parcela.getNumeroParcela());
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar] Erro Hibernate: " + super.mensagem + "-" + parcela.getPagamento().getOc().getId() + "-" + parcela.getNumeroParcela());			
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[alterar] Erro genérico: " + super.mensagem + "-" + parcela.getPagamento().getOc().getId() + "-" + parcela.getNumeroParcela());
		}
		
	}

	@Override
	public List<Parcela> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyDataModel<Parcela> getListaParcelaAVencer(){
		if(listaParcelaGeral == null){
			listaParcelaGeral = new LazyDataModel<Parcela>() {
									 private List<Parcela> listaLazyParcela;
									 
									 public Parcela getRowData(String idOc) {
									    	Integer id = Integer.valueOf(idOc);
									    	
									        for(Parcela parcela : listaLazyParcela) {
									            if(parcela.getId().equals(id))
									                return parcela;
									        }
									        
									        return null;
									    }
					
									    @Override
									    public Object getRowKey(Parcela parcela) {
									        return parcela.getId();
									    }
					
									    @Override
									    public List<Parcela> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
									    	ParcelaDAO parcelaDAO = new ParcelaDAO();
									    	listaLazyParcela = parcelaDAO.listaParcelasAVencer(first, pageSize);
									    	if (getRowCount() <= 0) {  
									            setRowCount(parcelaDAO.totalParcelasAVencer());  
									        }  
									        // set the page dize  
									        setPageSize(pageSize);  
									        return listaLazyParcela;  
									    }
								};
		}
		return listaParcelaGeral;
	}
	
	public LazyDataModel<Parcela> getListaParcelaAVencerCheque(){
		if(listaParcelaGeral == null){
			listaParcelaGeral = new LazyDataModel<Parcela>() {
									 private List<Parcela> listaLazyParcela;
									 
									 public Parcela getRowData(String idOc) {
									    	Integer id = Integer.valueOf(idOc);
									    	
									        for(Parcela parcela : listaLazyParcela) {
									            if(parcela.getId().equals(id))
									                return parcela;
									        }
									        
									        return null;
									    }
					
									    @Override
									    public Object getRowKey(Parcela parcela) {
									        return parcela.getId();
									    }
					
									    @Override
									    public List<Parcela> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
									    	ParcelaDAO parcelaDAO = new ParcelaDAO();
									    	listaLazyParcela = parcelaDAO.listaParcelasAVencerCheque(first, pageSize, getDataInicial(), getDataFinal(),filters);
									    	if (getRowCount() <= 0) {  
									            setRowCount(parcelaDAO.totalParcelasAVencerCheque(getDataInicial(), getDataFinal(),filters));  
									        }  
									        // set the page dize  
									        setPageSize(pageSize);  
									        return listaLazyParcela;  
									    }
								};
		}
		return listaParcelaGeral;
	}
 
	
	/*
	 * GETTERS & SETTERS
	 * */
	@Override
	public Parcela buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

	public List<Parcela> getListaParcela() {
		return listaParcela;
	}

	public void setListaParcela(List<Parcela> listaParcela) {
		this.listaParcela = listaParcela;
	}

	public ParcelaDAO getParcelaDAO() {
		return parcelaDAO;
	}

	public void setParcelaDAO(ParcelaDAO parcelaDAO) {
		this.parcelaDAO = parcelaDAO;
	}

	public LazyDataModel<Parcela> getListaParcelaGeral() {
		return listaParcelaGeral;
	}

	public void setListaParcelaGeral(LazyDataModel<Parcela> listaParcelaGeral) {
		this.listaParcelaGeral = listaParcelaGeral;
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
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, 30);
			dataFinal = calendar.getTime();
		}
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List getListaStatusCheque() {
		if(listaStatusCheque.size() == 0){
			SelectItem si = new SelectItem();
			si.setLabel("");
			si.setNoSelectionOption(true);			
			listaStatusCheque.add(si);
			listaStatusCheque.add(new SelectItem("Emitido"));
			listaStatusCheque.add(new SelectItem("Quitado"));
			listaStatusCheque.add(new SelectItem("Pendente"));
		}		
		return listaStatusCheque;
	}

	public void setListaStatusCheque(List<String> listaStatusCheque) {
		this.listaStatusCheque = listaStatusCheque;
	}
	
}
