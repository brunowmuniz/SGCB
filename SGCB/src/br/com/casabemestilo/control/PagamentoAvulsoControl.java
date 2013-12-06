package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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

import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.PagamentoAvulsoDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Banco;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.PagamentoAvulso;
import br.com.casabemestilo.model.Parcela;


@ManagedBean
@ViewScoped
public class PagamentoAvulsoControl extends Control implements InterfaceControl,
					Serializable{

	
	private static final long serialVersionUID = 1L;

	private PagamentoAvulso pagamentoAvulso;
	
	private List<PagamentoAvulso> pagamentoAvulsos;
	
	private PagamentoAvulsoDAO pagamentoAvulsoDAO;
	
	private Pagamento pagamento;
	
	private Cliente cliente;
	
	private Boolean ehOc;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private LazyDataModel<PagamentoAvulso> listarPagamentoAvulsoGeral;
	
	
	
	public PagamentoAvulsoControl() {

	}
		
	public PagamentoAvulsoControl(String messagem,
			PagamentoAvulso pagamentoAvulso,
			List<PagamentoAvulso> pagamentoAvulsos,
			PagamentoAvulsoDAO pagamentoAvulsoDAO, Pagamento pagamento) {
		super(messagem);
		this.pagamentoAvulso = pagamentoAvulso;
		this.pagamentoAvulsos = pagamentoAvulsos;
		this.pagamentoAvulsoDAO = pagamentoAvulsoDAO;
		this.pagamento = pagamento;
	}




	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {
		pagamentoAvulsoDAO = new PagamentoAvulsoDAO();
		try {
			if(pagamentoAvulso.getPagamentos() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Favor adicionar algum pagamento!", ""));
			}else if(pagamentoAvulso.getPagamentos().size() == 0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Favor adicionar algum pagamento!", ""));
			}else{
				pagamentoAvulsoDAO.insert(pagamentoAvulso);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamento Avulso Lançado!"));
				pagamentoAvulso = new PagamentoAvulso();
			}			
		} catch (ConstraintViolationException e) {			
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar - pagamentoavulso] Erro Constraint: " + super.mensagem);
		} catch (HibernateException e) {			
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[gravar - pagamentoavulso] Erro Hibernate: " + super.mensagem);
		} catch (Exception e) {			
			super.mensagem = e.getMessage();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[gravar - pagamentoavulso] Erro genérico: " + super.mensagem);
		}
	}

	@Override
	public void deletar() {
		pagamentoAvulsoDAO = new PagamentoAvulsoDAO();
		try {			
			pagamentoAvulso.setDeleted(true);			
			pagamentoAvulsoDAO.delete(pagamentoAvulso);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamento Avulso foi deletado!"));
			logger.info("Pagamento Avulso: " + pagamentoAvulso.getId() + " foi deletado");
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[deletar] Erro Constraint: " + super.mensagem + "-" + pagamentoAvulso.getId());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[deletar] Erro Hibernate: " + super.mensagem + "-" + pagamentoAvulso.getId());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[deletar] Erro genérico: " + super.mensagem + "-" + pagamentoAvulso.getId());
		}
		
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
	
	public void limparCliente(){
		setCliente(new Cliente());
	}
	
	public void defineFormaPagamento() throws ConstraintViolationException, HibernateException, Exception{		
		getPagamento().getCondicoesPagamento().setFormapagamento(new FormaPagamentoDAO().buscaObjetoId(getPagamento().getCondicoesPagamento().getFormapagamento().getId()));
		if(getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4){
			getPagamento().setBanco(new Banco());
			getPagamento().setCliente(new Cliente());
			getPagamento().getCliente().setId(getCliente().getId());
		}
	}

	public void defineCondicoesPagamento() throws ConstraintViolationException, HibernateException, Exception{
		CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
		condicoesPagamento = new CondicoesPagamentoDAO().buscaObjetoId(getPagamento().getCondicoesPagamento().getId());
		getPagamento().setCondicoesPagamento(condicoesPagamento);
	}
	
	public void gravaFormaPagamentoOc(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(getPagamentoAvulso().getPagamentos() == null){
			getPagamentoAvulso().setPagamentos(new ArrayList<Pagamento>());
		}
		
		if(getPagamento().getCondicoesPagamento().getAvista()){
			Parcela parcela = new Parcela();
			parcela.setPagamento(pagamento);
			parcela.setNumeroParcela(1);
			parcela.setValor(pagamento.getValor());
			parcela.setDataentrada(c.getTime());
			parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Emitido" : null);
			parcela.setStatusCartao(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getEhcartao() ? "Pendente" : null);
			pagamento.getParcelas().add(parcela);
		}else{
			for(int i = 1; i <= pagamento.getCondicoesPagamento().getParcelas(); i++){
				Parcela parcela = new Parcela();
				c.add(Calendar.DAY_OF_MONTH,30);
				parcela.setPagamento(pagamento);
				parcela.setNumeroParcela(i);
				parcela.setValor(pagamento.getValor() / pagamento.getCondicoesPagamento().getParcelas());
				parcela.setDataentrada(c.getTime());
				parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Emitido" : null);
				parcela.setStatusCartao(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getEhcartao() ? "Pendente" : null);
				pagamento.getParcelas().add(parcela);
			}
		}
		
		getPagamento().setPagamentoAvulso(getPagamentoAvulso());
		getPagamentoAvulso().getPagamentos().add(getPagamento());
		setPagamento(new Pagamento());
	}
	
	public void removeCondicoesPagamento(Pagamento pagamento){		
		pagamentoAvulso.getPagamentos().remove(pagamento);		
		pagamento = new Pagamento();
	}
	
	public void  buscaLacamentoAvulsoPorDataLancamento(){
		listarPagamentoAvulsoGeral = null;
		getListarPagamentoAvulsoGeralAll();
	}
	
	public LazyDataModel<PagamentoAvulso> getListarPagamentoAvulsoGeralAll(){
		if(listarPagamentoAvulsoGeral == null){
			listarPagamentoAvulsoGeral = new LazyDataModel<PagamentoAvulso>() {
											private List<PagamentoAvulso> listaLazyPagamentoAvulso;
											
											@Override
										    public PagamentoAvulso getRowData(String idPagamento) {
										    	Integer id = Integer.valueOf(idPagamento);
										    	
										        for(PagamentoAvulso pagamentoAvulso : listaLazyPagamentoAvulso) {
										            if(pagamentoAvulso.getId().equals(id))
										                return pagamentoAvulso;
										        }
										        
										        return null;
										    }
			
										    @Override
										    public Object getRowKey(PagamentoAvulso pagamentoAvulso) {
										        return pagamentoAvulso.getId();
										    }
			
										    @Override
										    public List<PagamentoAvulso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
										    	PagamentoAvulsoDAO pagamentoAvulsoDAO = new PagamentoAvulsoDAO();
										    	
										    	listaLazyPagamentoAvulso = pagamentoAvulsoDAO.listaLazyPagamentoAvulso(first, pageSize, filters, getDataInicial(), getDataFinal());
										    	 
										    
										    	if (getRowCount() <= 0) {  
										            setRowCount(pagamentoAvulsoDAO.totalPagamentoAvulso(filters, getDataInicial(), getDataFinal()));  
										        }  
										       
										        setPageSize(pageSize);  
										        return listaLazyPagamentoAvulso;  
										    }
							};
		}		
		return listarPagamentoAvulsoGeral;
	}
	
	public Float calculaTotalPagamentoAvulso(List<Pagamento> pagamentos){
		Float totalPagamento = new Float("0");
		for(Pagamento pagamento : pagamentos){
			totalPagamento += pagamento.getValor();
		}
		return totalPagamento;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public PagamentoAvulso getPagamentoAvulso() {
		if(pagamentoAvulso == null){
			pagamentoAvulso = new PagamentoAvulso();
		}
		return pagamentoAvulso;
	}

	public void setPagamentoAvulso(PagamentoAvulso pagamentoAvulso) {
		this.pagamentoAvulso = pagamentoAvulso;
	}

	public List<PagamentoAvulso> getPagamentoAvulsos() {
		return pagamentoAvulsos;
	}

	public void setPagamentoAvulsos(List<PagamentoAvulso> pagamentoAvulsos) {
		this.pagamentoAvulsos = pagamentoAvulsos;
	}
	
	public PagamentoAvulsoDAO getPagamentoAvulsoDAO() {
		return pagamentoAvulsoDAO;
	}

	public void setPagamentoAvulsoDAO(PagamentoAvulsoDAO pagamentoAvulsoDAO) {
		this.pagamentoAvulsoDAO = pagamentoAvulsoDAO;
	}

	public Pagamento getPagamento() {
		if(pagamento == null){
			pagamento = new Pagamento();
		}
		return pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}


	public Cliente getCliente() {
		if(cliente == null){
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Boolean getEhOc() {
		return ehOc;
	}

	public void setEhOc(Boolean ehOc) {
		this.ehOc = ehOc;
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
		if(dataFinal == null ){
			dataFinal = new Date();
		}

		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LazyDataModel<PagamentoAvulso> getListarPagamentoAvulsoGeral() {
		return listarPagamentoAvulsoGeral;
	}

	public void setListarPagamentoAvulsoGeral(
			LazyDataModel<PagamentoAvulso> listarPagamentoAvulsoGeral) {
		this.listarPagamentoAvulsoGeral = listarPagamentoAvulsoGeral;
	}
}
