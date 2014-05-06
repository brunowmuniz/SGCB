package br.com.casabemestilo.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.DataUtil;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.ContaContabilDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.LancamentoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Contacontabil;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Lancamento;
import br.com.casabemestilo.model.LancamentoDia;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.util.ExtendedExcelExporter;
import br.com.casabemestilo.util.ExtendedPDFExporter;

@ManagedBean
@ViewScoped
public class LancamentoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
	
	private Lancamento lancamento = new Lancamento();
	
	private LancamentoDAO lancamentoDAO;
	
	private Boolean ehParcelado = false;
	
	private Float totalLancamento;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private LazyDataModel<Lancamento> listaLancamentoGeral;
	
	private Float valorEntradas;
	
	private Float valorSaidas;
	
	private String numBoletos;
	
	private DataTable dataTableLancamento;
	
	private Fornecedor fornecedor = new Fornecedor();
	
	/*
	 * CONSTRUTORES
	 * */
	public LancamentoControl(String messagem, List<Lancamento> listaLancamento,
			Lancamento lancamento, LancamentoDAO lancamentoDAO) {
		super(messagem);
		this.listaLancamento = listaLancamento;
		this.lancamento = lancamento;
		this.lancamentoDAO = lancamentoDAO;
	}

	public LancamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public LancamentoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {		
		List<Date> dataParcelas = new ArrayList<Date>();
		try {
			if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
				fornecedor = new FornecedoresDAO().buscaObjetoId(fornecedor.getId());
				lancamento.setDescricao(fornecedor.getNome());
			}
			
			lancamentoDAO = new LancamentoDAO();
			lancamento.setDeleted(false);			
			lancamento.setContacontabil(new ContaContabilDAO().buscaObjetoId(lancamento.getContacontabil().getId()));
			dataParcelas = new DataUtil().gerarDatas(lancamento.getDataLancamento(), lancamento.getQtdeParcela(), true);
			
			if(lancamento.getContacontabil().getTipo().equals("D")){
				lancamento.setValor(- lancamento.getValor());
			}
			
			if(!lancamento.getEhVale()){
				lancamento.setUsuario(null);
			}
			
			if(lancamento.getContacontabil().getTipo().equals("D")){
				if(new Date().before(lancamento.getDataLancamento())){
					lancamento.setStatus("Pendente");
				}else{
					lancamento.setStatus("Quitado");				
				}
			}
			
			
			if(getEhParcelado()){
				Integer idLancamentoPai = 0;				
				for(int i = 1; i <= getLancamento().getQtdeParcela(); i++){
					lancamento.setParcela(i);
					lancamento.setDataLancamento(dataParcelas.get(i-1));
					if(i==1){
						if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
							lancamento.setNumBoleto(getNumBoletos().split(";")[0]);
						}						
						idLancamentoPai = lancamentoDAO.insertLista(lancamento).getId();
						lancamento.setLancamentoPai(new Lancamento());
						lancamento.getLancamentoPai().setId(idLancamentoPai);						
					}else{
						if(lancamento.getContacontabil().getTipo().equals("D")){
							lancamento.setStatus("Pendente");
						}						
						if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
							lancamento.setNumBoleto(getNumBoletos().split(";")[i-1]);
						}
						lancamentoDAO.insert(lancamento);
					}
				}				
			}else{
				if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
					lancamento.setNumBoleto(getNumBoletos().split(";")[0]);
				}
				lancamento.setDataLancamento(dataParcelas.get(0));
				lancamento.setQtdeParcela(1);
				lancamento.setParcela(1);
				lancamentoDAO.insert(lancamento);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lançamentos criados!"));
			lancamento = new Lancamento();
			ehParcelado = false;
			
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			e.printStackTrace();
		}
	}

	@Override
	public void deletar() {
		try {
			lancamentoDAO = new LancamentoDAO();
			lancamento.setDeleted(true);
			lancamentoDAO.delete(lancamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lançamento Deletado!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
		}
	}

	@Override
	public void alterar() {
		try {
			if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
				fornecedor = new FornecedoresDAO().buscaObjetoId(fornecedor.getId());
				lancamento.setDescricao(fornecedor.getNome());
			}
			lancamento.setContacontabil(new ContaContabilDAO().buscaObjetoId(lancamento.getContacontabil().getId()));
			lancamentoDAO = new LancamentoDAO();
			lancamentoDAO.update(lancamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lançamento Alterado!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
		}
	}
	
	public void deletarLote() {
		try {
			lancamentoDAO = new LancamentoDAO();
			//lancamento.setLancamentoPai(lancamentoDAO.buscaLancamentoPai(lancamento.getId()));
			lancamentoDAO.deletaLancamentoApartir(lancamento);		
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lançamentos Deletados!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
		}
	}

	@Override
	public List<Lancamento> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LazyDataModel<Lancamento> listaLancamentoGeralAll(String tipoLancamento){
		if(tipoLancamento.equalsIgnoreCase("vale")){
			lancamento = new Lancamento();
			lancamento.setEhVale(true);
		}		
		if(listaLancamentoGeral == null){
			listaLancamentoGeral = new LazyDataModel<Lancamento>() {
				private List<Lancamento> listaLazyLancamentos;
				
				@Override
			    public Lancamento getRowData(String idOc) {
			    	Integer id = Integer.valueOf(idOc);
			    	
			        for(Lancamento lancamento : listaLazyLancamentos) {
			            if(lancamento.getId().equals(id))
			                return lancamento;
			        }
			        
			        return null;
			    }

			    @Override
			    public Object getRowKey(Lancamento lancamento) {
			        return lancamento.getId();
			    }

			    @Override
			    public List<Lancamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
			    	LancamentoDAO lancamentoDAO = new LancamentoDAO();
			    	
			    	listaLazyLancamentos = lancamentoDAO.listaLazyLancamento(first, pageSize, filters, getDataInicial(), getDataFinal(), getLancamento().getEhVale());
			    	
			    	if (getRowCount() <= 0) { 
			            setRowCount(lancamentoDAO.totalLancamento(filters, getDataInicial(), getDataFinal(), getLancamento().getEhVale()));  
			        }  
			    	
			        setPageSize(pageSize);  
			        return listaLazyLancamentos;
			    }
			};
		}
		return listaLancamentoGeral;
	}
	
	public List<Lancamento> listaControleGeral(){
		lancamentoDAO = new LancamentoDAO();
		OcControl ocControl = new OcControl();
		Double valorVendasBrutas = ocControl.calculaVendaBruto(getDataInicial(), getDataFinal());
		Double valorPagamentoAvulsos = new PagamentoDAO().calculaPagamentosAvulsos(getDataInicial(), getDataFinal());
		Double valorFretePago = ocControl.calculaFretePago(getDataInicial(), getDataFinal());
		Double valorMontagemPago = ocControl.calculaMontagemPago(getDataInicial(), getDataFinal());
		setValorEntradas(new Float(0));
		setValorSaidas(new Float(0));
		
		valorVendasBrutas = valorVendasBrutas == null ? new Double("0") : valorVendasBrutas;
		valorPagamentoAvulsos = valorPagamentoAvulsos == null ? new Double("0") : valorPagamentoAvulsos;
		valorVendasBrutas += valorPagamentoAvulsos;
		
		try {
			listaLancamento = lancamentoDAO.listaControleGeral(getDataInicial(), getDataFinal());
			listaLancamento.add(new Lancamento(null,new ContaContabilDAO().buscaObjetoId(4), valorVendasBrutas));
			listaLancamento.add(new Lancamento(null,new ContaContabilDAO().buscaObjetoId(11), valorFretePago == null ? new Double(new Float(0).doubleValue()) : valorFretePago));
			listaLancamento.add(new Lancamento(null,new ContaContabilDAO().buscaObjetoId(28), valorMontagemPago == null ? new Double(new Float(0).doubleValue()) : valorMontagemPago));
			
			for(Lancamento lancamento : listaLancamento){
				if(lancamento.getContacontabil().getTipo().equalsIgnoreCase("D")){
					setValorSaidas(getValorSaidas() + lancamento.getValor()); 
				}
				if(lancamento.getContacontabil().getTipo().equalsIgnoreCase("C")){
					setValorEntradas(getValorEntradas() + lancamento.getValor());
				}
			}
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaLancamento;
	}
	
	public Boolean validaParcelasFornecedor(){
		Boolean isParcelaNumBoleto = true;
		if(lancamento.getContacontabil().getId() != null){
			if(lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10){
				if(getNumBoletos().split(";").length != lancamento.getQtdeParcela()){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
																		"Quantidade de nº de boletos diferente da quantidade de parcelas!", ""));
					isParcelaNumBoleto = false;
				}
			}
		}
		return isParcelaNumBoleto;
	}
	
	public void importaContasPagar(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("c:\\temp\\casabem\\contas-pagar-pendente-14122013.csv")));
			String linha = null;
			lancamentoDAO = new LancamentoDAO();
			listaLancamento = new ArrayList<Lancamento>();
			while((linha = reader.readLine()) != null){
				String[] linhaPagar = linha.split(",");
				lancamento = new Lancamento();
				lancamento.setContacontabil(new ContaContabilDAO().buscaObjetoId(19));
				Calendar dataLancamento = Calendar.getInstance();
				
				dataLancamento.set(dataLancamento.DATE,Integer.parseInt(linhaPagar[0].split("/")[0]));
				dataLancamento.set(dataLancamento.MONTH,Integer.parseInt(linhaPagar[0].split("/")[1]) - 1);
				dataLancamento.set(dataLancamento.YEAR,Integer.parseInt(linhaPagar[0].split("/")[2]));
				
				lancamento.setDescricao(linhaPagar[7]);
				lancamento.setValor(- new Float(linhaPagar[11].replace(".", "") + "." + linhaPagar[12]));
				lancamento.setDeleted(false);
				lancamento.setDataLancamento(dataLancamento.getTime());
				lancamento.setFormapagamento(new FormaPagamentoDAO().buscaObjetoId(10));
				lancamento.setLancamentoPai(null);
				lancamento.setUsuario(null);
				
				
				lancamento.setParcela(1);
				lancamento.setQtdeParcela(1);
				
				lancamento.setStatus("Pendente");
				
				lancamento.setNumBoleto(linhaPagar[4]);
				listaLancamento.add(lancamento);
				//lancamentoDAO.insert(lancamento);
			}
			lancamentoDAO.insertCarga(listaLancamento);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public List<LancamentoDia> listaLancamentosDia(){
		List<LancamentoDia> listaLancamentosDia = new ArrayList<LancamentoDia>();
		LancamentoDia lancamentoDia = new LancamentoDia();
		lancamentoDAO = new LancamentoDAO();
		defineFiltro();
		int linhas = lancamentoDAO.totalLancamento(dataTableLancamento.getFilters(), getDataInicial(), getDataFinal(), false);
		listaLancamento = lancamentoDAO.listaLazyLancamento(0, linhas, dataTableLancamento.getFilters(), getDataInicial(), getDataFinal(), false);
		Calendar diaLancamento = Calendar.getInstance();
		for(Lancamento lancamento : listaLancamento){
			if(lancamento.getId() == listaLancamento.get(0).getId()){
				diaLancamento.setTime(lancamento.getDataLancamento());
				lancamentoDia.setDataLancamento(lancamento.getDataLancamento());
				lancamentoDia.setTotalLancamentos(1);
				lancamentoDia.setValorTotalLancamentos(lancamento.getValor());
				lancamentoDia.getLancamentos().add(lancamento);
			}else{
				if(!diaLancamento.getTime().equals(lancamento.getDataLancamento())){
					listaLancamentosDia.add(lancamentoDia);
					lancamentoDia = new LancamentoDia();
					diaLancamento.setTime(lancamento.getDataLancamento());
					lancamentoDia.setDataLancamento(lancamento.getDataLancamento());
					lancamentoDia.setTotalLancamentos(1);
					lancamentoDia.setValorTotalLancamentos(lancamento.getValor());
					lancamentoDia.getLancamentos().add(lancamento);
				}else{
					lancamentoDia.getLancamentos().add(lancamento);
					lancamentoDia.setTotalLancamentos(lancamentoDia.getTotalLancamentos() + 1);
					lancamentoDia.setValorTotalLancamentos(lancamentoDia.getValorTotalLancamentos()+ lancamento.getValor());
				}
			}
		}
		listaLancamentosDia.add(lancamentoDia);
		return listaLancamentosDia;
	}
	
	public float totalValorLancamentosPeriodo(){
		float totalValorLancamento = 0;
		for(Lancamento lancamento : listaLancamento){
			totalValorLancamento += lancamento.getValor();
		}
		return totalValorLancamento;
	}
	
	private void defineFiltro(){
		dataTableLancamento.setFilters(new HashMap<String, String>());
		if(lancamento.getStatus() == null){
			lancamento.setStatus("pendente");
		}
		if(lancamento.getStatus().equals("")){
			lancamento.setStatus("pendente");
		}
		dataTableLancamento.getFilters().put("status", lancamento.getStatus());
		dataTableLancamento.getFilters().put("contacontabil.tipo", "D");
		if(lancamento.getDescricao() != null){
			if(!lancamento.getDescricao().equals("")){
				dataTableLancamento.getFilters().put("descricao", lancamento.getDescricao());
			}
		}
		if(lancamento.getContacontabil().getId() != null){
			if(lancamento.getContacontabil().getId() != 0){
				dataTableLancamento.getFilters().put("contacontabil.id", lancamento.getContacontabil().getId().toString());
			}			
		}		
		if(lancamento.getNumBoleto() != null){
			if(!lancamento.getNumBoleto().equals("")){				
				dataTableLancamento.getFilters().put("numBoleto", lancamento.getNumBoleto());
			}			
		}
	}
	
	public void exportarExcel(DataTable tabela, String nomeArquivo) throws IOException{
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
		nomeArquivo += df.format(dataInicial) + " até " + df.format(dataFinal);
		FacesContext context = FacesContext.getCurrentInstance();
	    Exporter exporter = new ExtendedExcelExporter();
	    exporter.export(context,tabela, nomeArquivo, false, false, "ISO-8859-1", null, null);
	    context.responseComplete();
	}
	
	public void exportarPDF(DataTable tabela, String nomeArquivo) throws IOException{
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
		nomeArquivo += df.format(dataInicial) + " até " + df.format(dataFinal);
		FacesContext context = FacesContext.getCurrentInstance();
	    Exporter exporter = new ExtendedPDFExporter();
	    exporter.export(context, tabela, nomeArquivo, false, false, "ISO-8859-1", null, null);
	    context.responseComplete();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}

	public Lancamento getLancamento() {
		if(lancamento == null){
			lancamento = new Lancamento();
		}
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public LancamentoDAO getLancamentoDAO() {
		return lancamentoDAO;
	}

	public void setLancamentoDAO(LancamentoDAO lancamentoDAO) {
		this.lancamentoDAO = lancamentoDAO;
	}

	public Boolean getEhParcelado() {
		if(lancamento.getEhVale()){
			ehParcelado = false;
		}
		return ehParcelado;
	}

	public void setEhParcelado(Boolean ehParcelado) {
		this.ehParcelado = ehParcelado;
	}

	public Float getTotalLancamento() {
		if(ehParcelado){
			totalLancamento = lancamento.getValor() * lancamento.getQtdeParcela();
		}else{
			totalLancamento = lancamento.getValor();
			if(totalLancamento == null){
				totalLancamento = new Float(0);
			}
		}
		return totalLancamento;
	}

	public void setTotalLancamento(Float totalLancamento) {
		this.totalLancamento = totalLancamento;
	}

	public Date getDataInicial() {
		String pagina = FacesContext.getCurrentInstance().getViewRoot().getViewId();		
		if(dataInicial == null){
			if(pagina.indexOf("controlegeral") > -1){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				dataInicial = calendar.getTime(); 
			}else{
				dataInicial = new Date();
			}
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

	public LazyDataModel<Lancamento> getListaLancamentoGeral() {
		return listaLancamentoGeral;
	}

	public void setListaLancamentoGeral(
			LazyDataModel<Lancamento> listaLancamentoGeral) {
		this.listaLancamentoGeral = listaLancamentoGeral;
	}

	public Float getValorEntradas() {
		return valorEntradas;
	}

	public void setValorEntradas(Float valorEntradas) {
		this.valorEntradas = valorEntradas;
	}

	public Float getValorSaidas() {
		return valorSaidas;
	}

	public void setValorSaidas(Float valorSaidas) {
		this.valorSaidas = valorSaidas;
	}

	public String getNumBoletos() {
		if(numBoletos == null){
			numBoletos = "";
		}
		return numBoletos;
	}

	public void setNumBoletos(String numBoletos) {
		this.numBoletos = numBoletos;
	}

	public DataTable getDataTableLancamento() {
		return dataTableLancamento;
	}

	public void setDataTableLancamento(DataTable dataTableLancamento) {
		this.dataTableLancamento = dataTableLancamento;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
}
