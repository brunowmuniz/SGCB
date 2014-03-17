package br.com.casabemestilo.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import main.DataUtil;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.BancoDAO;
import br.com.casabemestilo.DAO.ClienteDAO;
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
	
	private Date dataPrimeiraParcela;
	
	
	
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
				cliente = new Cliente();
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
		pagamentoAvulsoDAO = new PagamentoAvulsoDAO();		
		
		try {			
			pagamentoAvulsoDAO.update(pagamentoAvulso);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamento Avulso Alterado!"));			
		} catch (ConstraintViolationException e) {			
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar - pagamentoavulso] Erro Constraint: " + super.mensagem);
		} catch (HibernateException e) {			
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar - pagamentoavulso] Erro Hibernate: " + super.mensagem);
		} catch (Exception e) {			
			super.mensagem = e.getMessage();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[alterar - pagamentoavulso] Erro genérico: " + super.mensagem);
		}		
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
		}
		getPagamento().setCliente(new Cliente());
		getPagamento().getCliente().setId(getCliente().getId());
	}

	public void defineCondicoesPagamento() throws ConstraintViolationException, HibernateException, Exception{
		CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
		condicoesPagamento = new CondicoesPagamentoDAO().buscaObjetoId(getPagamento().getCondicoesPagamento().getId());
		getPagamento().setCondicoesPagamento(condicoesPagamento);
	}
	
	
	public void gravaFormaPagamentoOc(){
		List<Date> dataParcelas = new ArrayList<Date>();
		if(getPagamentoAvulso().getPagamentos() == null){
			getPagamentoAvulso().setPagamentos(new ArrayList<Pagamento>());
		}
		
		dataParcelas = new DataUtil().gerarDatas(getDataPrimeiraParcela(), pagamento.getCondicoesPagamento().getParcelas(), true);
		
		if(getPagamento().getCondicoesPagamento().getAvista()){
			Parcela parcela = new Parcela();
			parcela.setPagamento(pagamento);
			parcela.setNumeroParcela(1);
			parcela.setValor(pagamento.getValor());
			parcela.setDataentrada(new Date());
			parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Quitado" : null);
			parcela.setStatusCartao(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getEhcartao() ? "Quitado" : null);
			pagamento.getParcelas().add(parcela);
		}else{			
			for(int i = 1; i <= pagamento.getCondicoesPagamento().getParcelas(); i++){
				Parcela parcela = new Parcela();
				parcela.setPagamento(pagamento);
				parcela.setNumeroParcela(i);
				parcela.setValor(pagamento.getValor() / pagamento.getCondicoesPagamento().getParcelas());
				parcela.setDataentrada(dataParcelas.get(i-1));
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
	
	public void insereChequesPendentes(){		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("c:\\temp\\casabem\\receber-pendentes.csv")));
			String linha = null;
			Calendar calendar = Calendar.getInstance();
			Calendar dataLimite = Calendar.getInstance();
			Calendar dataLancamento = Calendar.getInstance();
			String diaAtual = "";			
			pagamentoAvulsos = new ArrayList<PagamentoAvulso>();			
			Pagamento pagamento = new Pagamento();
			Parcela parcela = new Parcela();
			List<Pagamento> pagamentos = new ArrayList<Pagamento>();
			Boolean existeCliente = false;
			
			dataLimite.set(dataLimite.YEAR, 2013);
			dataLimite.set(dataLimite.MONTH, 11);
			dataLimite.set(dataLimite.DATE, 05);
			
			while((linha = reader.readLine()) != null){
				String[] linhaCheque = linha.split(",");	
				calendar = Calendar.getInstance();
				dataLancamento = Calendar.getInstance();
				existeCliente = false;
				dataLancamento.set(dataLancamento.YEAR, Integer.parseInt(linhaCheque[5].split("/")[2]));
				dataLancamento.set(dataLancamento.MONTH, Integer.parseInt(linhaCheque[5].split("/")[1])-1); // Os meses no dataLancamento começam no 0
				dataLancamento.set(dataLancamento.DATE, Integer.parseInt(linhaCheque[5].split("/")[0]));
				
				if(dataLancamento.compareTo(dataLimite) <= 0){
					if(diaAtual.equals("")){
						diaAtual =  linhaCheque[0];
								
						pagamentoAvulso = new PagamentoAvulso();
						pagamento = new Pagamento(); 
						pagamentoAvulso.setDescricao("Super Gerente");
						pagamentoAvulso.setDataLancamento(new Date());
						pagamentoAvulso.setDeleted(false);
						pagamentoAvulso.setEhRenegociacao(false);
						pagamentoAvulso.setPagamentos(new ArrayList<Pagamento>());
						
						pagamento = new Pagamento();
						pagamento.setBanco(null);
						pagamento.setCliente(new ClienteDAO().buscaObjetoId(Integer.parseInt(linhaCheque[6])));
						pagamento.setCondicoesPagamento(new CondicoesPagamentoDAO().buscaObjetoId(52));
						pagamento.setDatalancamento(new Date());
						pagamento.setDeleted(false);
						pagamento.setPagamentoAvulso(pagamentoAvulso);
						pagamento.setParcelas(new ArrayList<Parcela>());
						pagamento.setValor(new Float("0"));
						pagamento.setBanco(new BancoDAO().buscaObjetoId(1));
						
						parcela = new Parcela();
						parcela.setSituacaoCheque("Pendente");
						parcela.setDeleted(false);
						calendar.set(calendar.YEAR, Integer.parseInt(linhaCheque[0].split("/")[2]));
						calendar.set(calendar.MONTH, Integer.parseInt(linhaCheque[0].split("/")[1])-1); // Os meses no Calendar começam no 0
						calendar.set(calendar.DATE, Integer.parseInt(linhaCheque[0].split("/")[0]));
						//parcela.setDataentrada(new SimpleDateFormat("yyyy-mm-dd").parse(linhaCheque[0]));
						parcela.setDataentrada(calendar.getTime());
						parcela.setDeleted(false);
						parcela.setNumeroCheque(linhaCheque[4].split("/")[0]);
						parcela.setNumeroParcela(Integer.parseInt(linhaCheque[4].split("/")[1]));
						parcela.setPagamento(pagamento);
						parcela.setValor(new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));					
						pagamento.getParcelas().add(parcela);
						pagamento.setValor(new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));
						pagamentos.add(pagamento);
					}else{
						for(Pagamento pagamentoCliente : pagamentos){
							try {
								if(pagamentoCliente.getCliente().getId() == Integer.parseInt(linhaCheque[6])){
									parcela = new Parcela();								
									parcela.setSituacaoCheque("Pendente");
									parcela.setDeleted(false);
									calendar.set(calendar.YEAR, Integer.parseInt(linhaCheque[0].split("/")[2]));
									calendar.set(calendar.MONTH, Integer.parseInt(linhaCheque[0].split("/")[1])-1); // Os meses no Calendar começam no 0
									calendar.set(calendar.DATE, Integer.parseInt(linhaCheque[0].split("/")[0]));
									//parcela.setDataentrada(new SimpleDateFormat("yyyy-mm-dd").parse(linhaCheque[0]));
									parcela.setDataentrada(calendar.getTime());
									parcela.setDeleted(false);
									parcela.setNumeroCheque(linhaCheque[4].split("/")[0]);
									parcela.setNumeroParcela(Integer.parseInt(linhaCheque[4].split("/")[1]));
									parcela.setPagamento(pagamento);
									parcela.setValor(new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));								
									pagamentoCliente.getParcelas().add(parcela);
									pagamentoCliente.setValor(pagamentoCliente.getValor() + new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));
									existeCliente = true;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						if(!existeCliente){
							pagamento = new Pagamento();
							pagamento.setBanco(null);
							pagamento.setCliente(new ClienteDAO().buscaObjetoId(Integer.parseInt(linhaCheque[6])));
							if(pagamento.getCliente() == null){
								System.out.println("Erro cliente");
							}
							if(pagamento.getCliente().getId() == null){
								System.out.println("Erro id");
							}
							pagamento.setCondicoesPagamento(new CondicoesPagamentoDAO().buscaObjetoId(52));
							pagamento.setDatalancamento(new Date());
							pagamento.setDeleted(false);
							pagamento.setPagamentoAvulso(pagamentoAvulso);
							pagamento.setParcelas(new ArrayList<Parcela>());
							pagamento.setValor(new Float("0"));
							pagamento.setBanco(new BancoDAO().buscaObjetoId(1));
							
							parcela = new Parcela();
							parcela.setSituacaoCheque("Pendente");
							parcela.setDeleted(false);
							calendar.set(calendar.YEAR, Integer.parseInt(linhaCheque[0].split("/")[2]));
							calendar.set(calendar.MONTH, Integer.parseInt(linhaCheque[0].split("/")[1])-1); // Os meses no Calendar começam no 0
							calendar.set(calendar.DATE, Integer.parseInt(linhaCheque[0].split("/")[0]));
							//parcela.setDataentrada(new SimpleDateFormat("yyyy-mm-dd").parse(linhaCheque[0]));
							parcela.setDataentrada(calendar.getTime());
							parcela.setDeleted(false);
							parcela.setNumeroCheque(linhaCheque[4].split("/")[0]);
							parcela.setNumeroParcela(Integer.parseInt(linhaCheque[4].split("/")[1]));
							parcela.setPagamento(pagamento);
							parcela.setValor(new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));					
							pagamento.getParcelas().add(parcela);
							pagamento.setValor(new Float(linhaCheque[12].replace(".", "") + "." + linhaCheque[13]));
							pagamentos.add(pagamento);
						}
					}
				}else{
					System.out.println("Lançamento depois: " + linhaCheque[5]);
				}
			}
			
			for(Pagamento pagamentoCliente : pagamentos){
				pagamentoAvulso.getPagamentos().add(pagamentoCliente);
			}
			
			pagamentoAvulso.getPagamentos().add(pagamento);
			
			pagamentoAvulsoDAO = new PagamentoAvulsoDAO();
			pagamentoAvulsoDAO.insert(pagamentoAvulso);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
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

	public Date getDataPrimeiraParcela() {
		if(dataPrimeiraParcela == null){
			dataPrimeiraParcela = new Date();
		}
		return dataPrimeiraParcela;
	}

	public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
		this.dataPrimeiraParcela = dataPrimeiraParcela;
	}
	
}
