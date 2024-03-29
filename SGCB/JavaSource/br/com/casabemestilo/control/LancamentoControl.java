package br.com.casabemestilo.control;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.servlet.http.HttpServletResponse;

import main.DataUtil;
import net.sf.jasperreports.engine.export.oasis.Style;

import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFontFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.taglib.html_basic.FormTag;

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
	
	private String tipoArquivo = "xls";
	
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
	 * M�TODOS
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amentos criados!"));
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
			e.printStackTrace();
		}
	}

	@Override
	public void deletar() {
		try {
			lancamentoDAO = new LancamentoDAO();
			lancamento.setDeleted(true);
			lancamentoDAO.delete(lancamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amento Deletado!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}

	@Override
	public void alterar() {
		try {
			if((lancamento.getContacontabil().getId() == 19 || lancamento.getContacontabil().getId() == 10) && lancamento.getDescricao().equals("")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sem fornecedor/freteiro definido!", ""));
			}else{
				lancamento.setContacontabil(new ContaContabilDAO().buscaObjetoId(lancamento.getContacontabil().getId()));
				lancamentoDAO = new LancamentoDAO();
				lancamentoDAO.update(lancamento);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amento Alterado!"));
			}			
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}
	
	public void deletarLote() {
		try {
			lancamentoDAO = new LancamentoDAO();
			//lancamento.setLancamentoPai(lancamentoDAO.buscaLancamentoPai(lancamento.getId()));
			lancamentoDAO.deletaLancamentoApartir(lancamento);		
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amentos Deletados!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
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
		lancamento = new Lancamento();
		
		if(tipoLancamento.equalsIgnoreCase("vale")){			
			lancamento.setEhVale(true);
		}else{					
			lancamento.setEhVale(false);
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
			        setRowCount(lancamentoDAO.totalLancamento(filters, getDataInicial(), getDataFinal(), getLancamento().getEhVale()));  			        			    	
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
																		"Quantidade de n� de boletos diferente da quantidade de parcelas!", ""));
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
		dataTableLancamento = new DataTable();
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
	
	public void exportarArquivo(DataTable tabela, String nomeArquivo, String tipoArquivo) throws IOException{
		this.tipoArquivo = tipoArquivo;
		Exporter exporter = null;
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
		nomeArquivo += df.format(dataInicial) + " at� " + df.format(dataFinal);
		FacesContext context = FacesContext.getCurrentInstance();
		if(tipoArquivo.equals("xls")){
			exporter = new ExtendedExcelExporter();
		}else{
			exporter = new ExtendedPDFExporter();
		}	    
	    exporter.export(context,tabela, nomeArquivo, false, false, "ISO-8859-1", null, null);
	    context.responseComplete();
	}	
	
	public void buscaLancamentoPeriodo(){		
		listaLancamentoGeralAll("lancamento");
	}
	
	public void gerarExcelGeral(){
		try {
			int iLinha = 0;
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
			HSSFWorkbook wbGeral = new HSSFWorkbook();
			HSSFSheet planPrincipal = null;
			planPrincipal = wbGeral.createSheet("Geral");
			HSSFRow linha = null;			
			String nomeArquivo = "Controle Geral-Todas Contas-Periodo de ";			 
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			HSSFFont fonteCabecalho = wbGeral.createFont();
			HSSFCellStyle styleMoeda = wbGeral.createCellStyle();
			HSSFCellStyle styleCabecalho = wbGeral.createCellStyle();
			
			// Pode ser usado o 2 ou 4. Os demais s�o com o $			 
			styleMoeda.setDataFormat((short) 2);
			
			//Definindo estilo da fonte de cabe�alho
			fonteCabecalho.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleCabecalho.setFont(fonteCabecalho);			
			
			linha = planPrincipal.createRow(0);		
			linha.createCell(0).setCellValue("Conta Contabil");			
			linha.createCell(1).setCellValue("Sa�das (R$)");
			linha.createCell(2).setCellValue("Entradas (R$)");
			
			for(int i = 0; i < 3; i++){
				linha.getCell(i).setCellStyle(styleCabecalho);
			}			
			
			for (Lancamento lancamentoLinha : listaLancamento) {
				double valor =  lancamentoLinha.getValor();
				BigDecimal bdValor = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
				linha = planPrincipal.createRow(++iLinha);				
				linha.createCell(0).setCellValue(lancamentoLinha.getContacontabil().getNome());				
				linha.createCell(1).setCellValue(bdValor.doubleValue() < 0 ? bdValor.doubleValue() : 0.00);
				linha.getCell(1).setCellStyle(styleMoeda);
				linha.createCell(2).setCellValue(bdValor.doubleValue() > 0 ? bdValor.doubleValue() : 0.00);
				linha.getCell(2).setCellStyle(styleMoeda);				
			}
			
			linha = planPrincipal.createRow(++iLinha);
			linha = planPrincipal.createRow(++iLinha);			
			linha.createCell(0).setCellValue("Somat�rio Entradas (R$):");
			linha.createCell(1).setCellValue(this.valorEntradas);
			linha.getCell(0).setCellStyle(styleCabecalho);
			linha.getCell(1).setCellStyle(styleMoeda);
			
			linha = planPrincipal.createRow(++iLinha);
			linha.createCell(0).setCellValue("Somat�rio Sa�das (R$):");
			linha.createCell(1).setCellValue(this.valorSaidas);
			linha.getCell(0).setCellStyle(styleCabecalho);
			linha.getCell(1).setCellStyle(styleMoeda);
			
			linha = planPrincipal.createRow(++iLinha);
			linha.createCell(0).setCellValue("Total Per�odo (R$):");
			linha.createCell(1).setCellValue(this.valorEntradas + this.valorSaidas);
			linha.getCell(0).setCellStyle(styleCabecalho);
			linha.getCell(1).setCellStyle(styleMoeda);
			
			nomeArquivo += df.format(getDataInicial()) + " at� " + df.format(getDataFinal()) + ".xls";
			
			planPrincipal = wbGeral.getSheetAt(0);
			
			//Definem o ajuste das colunas com a largura dos campos
			for(int i = 0; i < 3; i++){
				planPrincipal.autoSizeColumn(i);	
			}	
					
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wbGeral.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentType("application/octet-stream"); //Diz que o contentType � um stream
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // elimina o cache do browser
			response.setHeader("Content-Disposition", "attachment; filename= " + nomeArquivo);
			
			response.getOutputStream().write(outArray);
			response.getOutputStream().flush();
			response.getOutputStream().close() ;
			context.responseComplete();	// Importante no uso JSF, para n�o reutilizar					
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro IO: " + e.getMessage(), ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + e.getMessage(), ""));
		}	
	}
	
	public String gerarExcelCC(){
		try {
			int iLinha = 0;
			String cabecalho = "Data Lan�amento,Descri��o,N� Boleto,Forma Pgto.,� Parcelado,Parcela,Valor da Parcela";
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
			HSSFWorkbook wbGeral = new HSSFWorkbook();
			HSSFSheet planPrincipal = null;
			planPrincipal = wbGeral.createSheet(lancamento.getContacontabil().getNome() + " - Quitado");
			HSSFRow linha = null;			
			String nomeArquivo = "Controle Geral - " + lancamento.getContacontabil().getNome() + " - Periodo de ";			 
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			HSSFFont fonteCabecalho = wbGeral.createFont();
			HSSFDataFormat formatData = wbGeral.createDataFormat();
			HSSFCellStyle styleMoeda = wbGeral.createCellStyle();
			HSSFCellStyle styleCabecalho = wbGeral.createCellStyle();
			HSSFCellStyle styleData = wbGeral.createCellStyle();
			
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("contacontabil.id", lancamento.getContacontabil().getId().toString());
			filters.put("status", "Quitado");
			listaLancamento = lancamentoDAO.listaLazyLancamento(0, 9999, filters, getDataInicial(), getDataFinal(), false);
			
			// Pode ser usado o 2 ou 4. Os demais s�o com o $			 
			styleMoeda.setDataFormat((short) 2);
			
			//Define o padr�o de Data						
			styleData.setDataFormat((short) 14);
					
			//Definindo estilo da fonte de cabe�alho
			fonteCabecalho.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleCabecalho.setFont(fonteCabecalho);			
			
			linha = planPrincipal.createRow(0);
			
			for(int i = 0; i < cabecalho.split(",").length; i++){
				linha.createCell(i).setCellValue(cabecalho.split(",")[i]);
				linha.getCell(i).setCellStyle(styleCabecalho);
			}			
			
			//String cabecalho = "Data Lan�amento,Descri��o,N� Boleto,Forma Pgto.,� Parcelado,Parcela,Valor da Parcela";
			
			for (Lancamento lancamentoLinha : listaLancamento) {
				double valor =  lancamentoLinha.getValor();
				BigDecimal bdValor = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
				linha = planPrincipal.createRow(++iLinha);
				
				linha.createCell(0).setCellValue(lancamentoLinha.getDataLancamento());
				linha.getCell(0).setCellStyle(styleData);
				linha.createCell(1).setCellValue(lancamentoLinha.getDescricao());
				linha.createCell(2).setCellValue(lancamentoLinha.getNumBoleto());
				linha.createCell(3).setCellValue(lancamentoLinha.getFormapagamento().getNome());
				linha.createCell(4).setCellValue(lancamentoLinha.getQtdeParcela() == 1 ? "N�o" : "Sim");				
				linha.createCell(5).setCellValue(lancamentoLinha.getQtdeParcela() == 1 ? "Parcela �nica" : lancamentoLinha.getParcela() + "/" + lancamentoLinha.getQtdeParcela());
				linha.createCell(6).setCellValue(lancamentoLinha.getValor());
				linha.getCell(6).setCellStyle(styleMoeda);								
			}			
						
			nomeArquivo += df.format(getDataInicial()) + " at� " + df.format(getDataFinal()) + ".xls";
			
			planPrincipal = wbGeral.getSheetAt(0);
			
			//Definem o ajuste das colunas com a largura dos campos
			for(int i = 0; i < cabecalho.split(",").length; i++){
				planPrincipal.autoSizeColumn(i);
			}	
					
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wbGeral.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			response.setContentType("application/octet-stream"); //Diz que o contentType � um stream
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // elimina o cache do browser
			response.setHeader("Content-Disposition", "attachment; filename= " + nomeArquivo);
			
			response.getOutputStream().write(outArray);
			response.getOutputStream().flush();
			response.getOutputStream().close() ;
			context.responseComplete();	// Importante no uso JSF, para n�o reutilizar					
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro IO: " + e.getMessage(), ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + e.getMessage(), ""));
		}
		return "controlegeral";
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

	public void setListaLancamentoGeral(LazyDataModel<Lancamento> listaLancamentoGeral) {
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

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

}
