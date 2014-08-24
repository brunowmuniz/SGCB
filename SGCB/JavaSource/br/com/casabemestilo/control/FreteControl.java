package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.jasper.compiler.ServletWriter;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import br.com.casabemestilo.util.ConnectionFactory;

import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.ComissaoMontador;
import br.com.casabemestilo.model.ComissaoVendedor;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Oc;
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
	
	private List<Integer> listaUsuarioMontadores = new ArrayList<Integer>();
	
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
	
	public Frete gravarFreteOcProduto(List<Ocproduto> listaOcprodutos, List<Integer> listaMontadores, String observacoes) {				
		try {
			freteDAO = new FreteDAO();
			frete = new Frete();
			frete.setDatainicio(new Date());
			frete.setDatafim(new Date());
			frete.setOcprodutos(listaOcprodutos);
			frete.setValor(listaOcprodutos.get(0).getOc().getValorfrete());
			observacoes += "(Frete/Montagem por:";
			for(Iterator iterMontador = listaMontadores.iterator(); iterMontador.hasNext();){
				Integer idMontador = Integer.parseInt(iterMontador.next().toString());				
				
				observacoes += new UsuarioDAO().buscaObjetoId(idMontador).getNome();
				if(iterMontador.hasNext()){
					observacoes += ", ";
				}
			}
			observacoes += ")";
			frete.setObservacoes(observacoes);
			this.listaMontadores = listaMontadores;
			calculaComissaoMontagem();
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
			if(frete.getOcprodutos().get(0).getOc().getTipoFrete().indexOf("Local") != -1){			
				setTotalLocal(getTotalLocal() + frete.getValor());
			}else if(frete.getOcprodutos().get(0).getOc().getTipoFrete().indexOf("Loja") != -1){
				setTotalLoja(getTotalLoja() + frete.getValor());
			}else{
				setTotalBrinde(getTotalBrinde() + frete.getValor());
			}
		}
	} 
	
	public void calculaComissaoMontagem() throws ConstraintViolationException, HibernateException, Exception{
		Oc oc = new Oc();
		float valorLiquidoProduto = 0;		
		float valorTotalBrutoProdutoMontagem = 0;
		float valorLiquidoProdPlanejado = 0;
		float valorTotalBrutoProdPlanejado = 0;
		float valorComissao = 0;
		String[] montadoresComissaoCjta = null;
		List<ComissaoMontador> listaComissaoMontador = new ArrayList<ComissaoMontador>();

		oc = frete.getOcprodutos().get(0).getOc();
		
		for(Ocproduto ocproduto : frete.getOcprodutos()){
			if(ocproduto.getProduto().getTemMontagem() && !ocproduto.getProduto().getEhPlanejado()){
				valorTotalBrutoProdutoMontagem += ocproduto.getValortotal();
			}
			if(ocproduto.getProduto().getEhPlanejado()){
				valorTotalBrutoProdPlanejado += ocproduto.getValortotal();
			}
		}
		
		
		if(valorTotalBrutoProdutoMontagem != 0 || valorTotalBrutoProdPlanejado != 0){			
			valorLiquidoProduto = valorTotalBrutoProdutoMontagem * (oc.getValorliquido() / oc.getValorfinal());
			valorLiquidoProdPlanejado = valorTotalBrutoProdPlanejado * (oc.getValorliquido() / oc.getValorfinal());
					
			for(Iterator iterMontador = listaMontadores.iterator(); iterMontador.hasNext();){
				Integer idUsuarioMontador = Integer.parseInt(iterMontador.next().toString());
				Comissao comissao = new ComissaoDAO().buscaComissaoUsuarioMontador(idUsuarioMontador);				
				if(comissao.getEhComissaoMontadorIndividual()){
					if(!listaUsuarioMontadores.contains(comissao.getUsuario().getId())){						
						listaUsuarioMontadores.add(comissao.getUsuario().getId());
					}
				}else{
					if(!listaUsuarioMontadores.contains(comissao.getUsuario().getId())){
						listaUsuarioMontadores.add(comissao.getUsuario().getId());
					}
					montadoresComissaoCjta = comissao.getUsuarioComissaoMontadorConjunta().split(",");
					for(int i = 0; i < montadoresComissaoCjta.length; i++){
						Usuario usuario =  new UsuarioDAO().buscaObjetoId(Integer.parseInt(montadoresComissaoCjta[i]));
						if(!listaUsuarioMontadores.contains(usuario.getId())){
							listaUsuarioMontadores.add(usuario.getId());
						}
					}
					
				}				
			}
			
			for(Integer idUsuario : listaUsuarioMontadores){
				Comissao comissao = new ComissaoDAO().buscaComissaoUsuarioMontador(idUsuario);
				ComissaoMontador comissaoMontador = new ComissaoMontador();				
				
				comissaoMontador.setMontador(new UsuarioDAO().buscaObjetoId(idUsuario));
				comissaoMontador.setFreteMontagem(frete);
				
				if(comissao.getEhComissaoMontadorIndividual()){
					valorComissao = ((comissao.getPercentualComissaoMontadorIndividual() / 100) * valorLiquidoProduto + 
									 (comissao.getPercentualComissaoMontadorPlanejado() / 100) * valorLiquidoProdPlanejado);
					comissaoMontador.setValor(new Double(valorComissao));
				}else{
					valorComissao = ((comissao.getPercentualComissaoMontadorConjunta() / 100) * valorLiquidoProduto +
									 (comissao.getPercentualComissaoMontadorPlanejado() / 100) * valorLiquidoProdPlanejado);
					comissaoMontador.setValor(new Double(valorComissao / (comissao.getUsuarioComissaoMontadorConjunta().split(",").length + 1)));
				}
				listaComissaoMontador.add(comissaoMontador);
			}
			frete.setComissaoMontadores(listaComissaoMontador);
		}		
	}
	
	public void impressaoFrete(Frete frete){
		Connection conexaoFactory = null;		
		try {
			conexaoFactory = new ConnectionFactory().getConexao();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			Map<String, Object> parametros = new HashMap<String, Object>();
			InputStream caminho = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("UsuarioLogado");
			String caminhoRelatorio = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/br/com/casabemestilo/relatorio/");			
			caminho = getClass().getResourceAsStream("../relatorio/solicitacaofretemontagem.jrxml");
			Float totalMontagem = new Float("0");
			
			for(ComissaoMontador comissaoMontador : frete.getComissaoMontadores()){
				totalMontagem += comissaoMontador.getValor().floatValue();
			}
	
			if(caminhoRelatorio.indexOf("home") > -1)
				caminhoRelatorio += "/";
			else
				caminhoRelatorio += "\\";
			
			response.setContentType("application/pdf");			
			parametros.put("IDFRETE", frete.getId());
			parametros.put("DATAHORA_IMPRESSAO", new Date());
			parametros.put("USUARIO_IMPRESSAO", usuarioLogado.getNome());
			parametros.put("SUBREPORT_DIR", caminhoRelatorio);
			parametros.put("TOTALCOMISSAOMONTAGEM", totalMontagem);
			parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
			response.setHeader("Content-Disposition","attachment; filename=\"Solicitação_Frete-" + frete.getId() +".pdf\"");
			JasperReport pathReport = JasperCompileManager.compileReport(caminho);			
			
			JasperPrint preencher = JasperFillManager.fillReport(pathReport,parametros,conexaoFactory);		
			JasperExportManager.exportReportToPdfStream(preencher, response.getOutputStream());
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro IO: " + super.mensagem, ""));
			e.printStackTrace();
		} catch(JRException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro JasperReport: " + super.mensagem, ""));
			e.printStackTrace();
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			e.printStackTrace();
		}finally{
			try {
				conexaoFactory.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
			c.add(Calendar.MONTH, -1);
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
