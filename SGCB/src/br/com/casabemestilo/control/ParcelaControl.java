package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Banco;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.ParcelaDia;


@ManagedBean
@ViewScoped
public class ParcelaControl extends Control implements InterfaceControl,
		Serializable {


	private static final long serialVersionUID = 1L;
	
	private Parcela parcela = new Parcela();
	
	private List<Parcela> listaParcela;
	
	private ParcelaDAO parcelaDAO;
	
	private LazyDataModel<Parcela> listaParcelaGeral;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private List listaStatusCheque = new ArrayList();
	
	private List listaStatusCartao = new ArrayList();
	
	private String acao;
	
	private String retorno;
	
	private Banco bancoDepCheque;
	
	private DataTable dataTableParcela;
	
	private String clienteFiltro;
	
	private Integer bancoFiltro;
	
	private Integer ocFiltro;
	
	private String statusFiltro = "";
	
	
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
		parcelaDAO = new ParcelaDAO();
		PagamentoDAO pagamentoDAO = new PagamentoDAO();
		Pagamento pagamento = new Pagamento();
		Float totalPagamento = new Float("0");
		try {
			parcela.setDeleted(true);
			parcelaDAO.delete(parcela);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parcela: " + parcela.getId() + " foi deletada!"));
			for(Parcela parcelaPagamento : parcela.getPagamento().getParcelas()){
				if(!parcelaPagamento.getDeleted()){
					totalPagamento += parcelaPagamento.getValor();
				}				
			}
			parcela.getPagamento().setValor(totalPagamento);
			pagamentoDAO.update(parcela.getPagamento());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamento com valor total alterado!"));			
		} catch (ConstraintViolationException e) {		
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[deletar] Erro Constraint: " + super.mensagem + "-" + parcela.getId() + "-" + parcela.getNumeroParcela());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[deletar] Erro Hibernate: " + super.mensagem + "-" + parcela.getId() + "-" + parcela.getNumeroParcela());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[deletar] Erro Hibernate: " + super.mensagem + "-" + parcela.getId() + "-" + parcela.getNumeroParcela());
		}
	}

	@Override
	public void alterar() {
		parcelaDAO = new ParcelaDAO();
		try {
			retorno = "";
			if(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4){
				if(parcela.getSituacaoCheque().equalsIgnoreCase("Antecipado")){					
					retorno = "Antecipado";
				}
			}else{
				if(parcela.getStatusCartao().equalsIgnoreCase("Antecipado")){					
					retorno = "Antecipado";
				}
			}
			
			if(retorno.equals("Antecipado")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Parcelas Antecipadas: Para parcelas antecipadas, favor utilizar a ação de 'Antecipar' e logo após os botões de 'Antecipar Seleção' ou 'Antecipar Todos' logo abaixo de ação nesta tela", ""));				
			}else{
				parcelaDAO.update(parcela);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parcela: " + parcela.getNumeroParcela() + " foi alterada!"));
				logger.info("Parcela: " + parcela.getNumeroParcela() + " foi alterada!");
			}			
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
									    	listaLazyParcela = parcelaDAO.listaParcelasAVencer(first, pageSize, getDataInicial(), getDataFinal(), filters);
									    	if (getRowCount() <= 0) {  
									            setRowCount(parcelaDAO.totalParcelasAVencer(getDataInicial(), getDataFinal(), filters));  
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
									        
									        setPageSize(pageSize);  
									        return listaLazyParcela;  
									    }
								};
		}
		return listaParcelaGeral;
	}
 
	public void buscaParcelasAvencer(){
		listaParcelaGeral = null;
		getListaParcelaAVencer();
	}
	
	public void buscaParcelasAVencerCheque(){
		listaParcelaGeral = null;
		getListaParcelaAVencerCheque();
	}
	
	
	public void anteciparTodos(){
		parcelaDAO = new ParcelaDAO();
		if(getBancoDepCheque().getId() != null){
			listaParcela = parcelaDAO.listaParcelasAVencerCheque(0, 1000, getDataInicial(), getDataFinal(),dataTableParcela.getFilters());
		}else{
			listaParcela = parcelaDAO.listaParcelasAVencer(0, 1000, getDataInicial(), getDataFinal(),dataTableParcela.getFilters());
		}
		
		for(Parcela parcela : listaParcela){
			parcela.setDataAntecipacao(new Date());
			if(getBancoDepCheque().getId() != null){
				parcela.setBancoDepositoCheque(bancoDepCheque);
				parcela.setSituacaoCheque("Antecipado");
			}else{
				parcela.setStatusCartao("Antecipado");
			}
		}
		retorno = parcelaDAO.antecipar(listaParcela);
		if(retorno.equals("ok")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parcelas quitadas com sucesso!"));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, retorno, ""));
		}
	}
	
	public void anteciparSelecao(){
		parcelaDAO = new ParcelaDAO();		
		for(Parcela parcela : listaParcela){
			parcela.setDataAntecipacao(new Date());
			if(getBancoDepCheque().getId() != null){
				parcela.setBancoDepositoCheque(bancoDepCheque);
				parcela.setSituacaoCheque("Antecipado");
			}else{
				parcela.setStatusCartao("Antecipado");
			}
		}
		
		retorno = parcelaDAO.antecipar(listaParcela);
		if(retorno.equals("ok")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parcelas quitadas com sucesso!"));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, retorno, ""));
		}
	}
	
	public List<ParcelaDia> listarParcelasDia(){
		List<ParcelaDia> listaParcelasDia = new ArrayList<ParcelaDia>();
		ParcelaDia parcelaDia = new ParcelaDia();
		parcelaDAO = new ParcelaDAO();
		defineFiltro();
		int linhas = parcelaDAO.totalParcelasAVencerCheque(getDataInicial(), getDataFinal(), dataTableParcela.getFilters());
		listaParcela = parcelaDAO.listaParcelasAVencerCheque(0, linhas, getDataInicial(), getDataFinal(), dataTableParcela.getFilters());
		Calendar diaLancamento = Calendar.getInstance();
		for(Parcela cheque : listaParcela){
			if(cheque.getId() == listaParcela.get(0).getId()){
				diaLancamento.setTime(cheque.getDataentrada());
				parcelaDia.setDataParcela(cheque.getDataentrada());
				parcelaDia.setTotalCheques(1);
				parcelaDia.setValorTotalParcelas(cheque.getValor());
				parcelaDia.getParcelas().add(cheque);
			}else{
				if(!diaLancamento.getTime().equals(cheque.getDataentrada())){
					listaParcelasDia.add(parcelaDia);
					parcelaDia = new ParcelaDia();
					diaLancamento.setTime(cheque.getDataentrada());
					parcelaDia.setDataParcela(cheque.getDataentrada());
					parcelaDia.setTotalCheques(1);
					parcelaDia.setValorTotalParcelas(cheque.getValor());
					parcelaDia.getParcelas().add(cheque);
				}else{
					parcelaDia.getParcelas().add(cheque);
					parcelaDia.setTotalCheques(parcelaDia.getTotalCheques() + 1);
					parcelaDia.setValorTotalParcelas(parcelaDia.getValorTotalParcelas() + cheque.getValor());
				}
			}
		}
		listaParcelasDia.add(parcelaDia);
		return listaParcelasDia;
	}
	
	public float totalValorParcelasPeriodo(){
		float totalValorCheques = 0;
		for(Parcela cheque : listaParcela){
			totalValorCheques += cheque.getValor();
		}
		return totalValorCheques;
	}
	
	private void defineFiltro(){
		dataTableParcela.setFilters(new HashMap<String, String>());
		if(statusFiltro.equals("")){
			statusFiltro = "pendente,emitido";
		}
		if(statusFiltro.indexOf(",") > -1){
			dataTableParcela.getFilters().put("situacaoCheque", statusFiltro.split(",")[0]);
			dataTableParcela.getFilters().put("situacaoCheque_2", statusFiltro.split(",")[1]);
		}else{
			dataTableParcela.getFilters().put("situacaoCheque", statusFiltro);
		}
		
		
		if(clienteFiltro != null){
			dataTableParcela.getFilters().put("pagamento.cliente.nome", getClienteFiltro());
		}
		if(bancoFiltro != 0){
			dataTableParcela.getFilters().put("pagamento.banco.id", getBancoFiltro().toString());
		}
		if(ocFiltro != 0){
			dataTableParcela.getFilters().put("pagamento.oc.id", getOcFiltro().toString());
		}
		if(getParcela().getNumeroCheque() != null){
			if(!getParcela().getNumeroCheque().equals("")){				
				dataTableParcela.getFilters().put("numeroCheque", getParcela().getNumeroCheque());
			}			
		}
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
			listaStatusCheque.add(new SelectItem("Antecipado"));
		}		
		return listaStatusCheque;
	}

	public void setListaStatusCheque(List<String> listaStatusCheque) {
		this.listaStatusCheque = listaStatusCheque;
	}

	public List getListaStatusCartao(String filter) {
		if(listaStatusCartao.size() == 0){
			if(filter.equals("filter")){							
				SelectItem si = new SelectItem();
				si.setLabel("");
				si.setNoSelectionOption(true);			
				listaStatusCartao.add(si);
			}			
			listaStatusCartao.add(new SelectItem("Antecipado"));
			listaStatusCartao.add(new SelectItem("Pendente"));
			listaStatusCartao.add(new SelectItem("Quitado"));
		}
		return listaStatusCartao;
	}

	public String getAcao() {
		if(acao == null){
			acao = "editar";
		}
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Banco getBancoDepCheque() {
		if(bancoDepCheque == null){
			bancoDepCheque = new Banco();
		}
		return bancoDepCheque;
	}

	public void setBancoDepCheque(Banco bancoDepCheque) {
		this.bancoDepCheque = bancoDepCheque;
	}

	public DataTable getDataTableParcela() {
		return dataTableParcela;
	}

	public void setDataTableParcela(DataTable dataTableParcela) {
		this.dataTableParcela = dataTableParcela;
	}

	public String getClienteFiltro() {
		return clienteFiltro;
	}

	public void setClienteFiltro(String clienteFiltro) {
		this.clienteFiltro = clienteFiltro;
	}

	public Integer getBancoFiltro() {
		if(bancoFiltro == null){
			bancoFiltro = 0;
		}
		return bancoFiltro;
	}

	public void setBancoFiltro(Integer bancoFiltro) {
		this.bancoFiltro = bancoFiltro;
	}

	public Integer getOcFiltro() {
		if(ocFiltro == null){
			ocFiltro = 0;
		}
		return ocFiltro;
	}

	public void setOcFiltro(Integer ocFiltro) {
		this.ocFiltro = ocFiltro;
	}

	public String getStatusFiltro() {
		return statusFiltro;
	}

	public void setStatusFiltro(String statusFiltro) {
		this.statusFiltro = statusFiltro;
	}
	
	
	
}
