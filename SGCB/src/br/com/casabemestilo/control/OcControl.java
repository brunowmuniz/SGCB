package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.sun.faces.context.flash.ELFlash;
import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.ComissaoVendedorDAO;
import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.DAO.PedidoProdutoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Banco;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.ComissaoVendedor;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Pedidoproduto;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.Status;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.ConnectionFactory;

@ManagedBean
@ViewScoped
public class OcControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Oc> listaOc;
	
	private Oc oc = new Oc();
	
	private OcDAO ocDAO;	
	
	private Pagamento pagamento = new Pagamento();
	
	private Float totalPagamento = new Float(0);
	
	private Ocproduto ocproduto;

	private Float percentualRetencao;

	private Float valorLiquido;
	
	private LazyDataModel<Oc> listarOcGeral;
	
	private List<String> listaTipoFrete;
	
	private List<Ocproduto> listaOcProdutoAcao = new ArrayList<Ocproduto>();
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String observacoesFreteMontAT;
	
	private Boolean ehClienteChequeOc;
	
		
	
	/*
	 * CONSTRUTORES
	 * */
	public OcControl(String messagem, List<Oc> listaOc, Oc oc, OcDAO ocDAO) {
		super(messagem);
		this.listaOc = listaOc;
		this.oc = oc;
		this.ocDAO = ocDAO;
	}

	public OcControl(String messagem) {
		super(messagem);
	}

	public OcControl() {}

	
	/*
	 * MÉTODOS
	 * */
	@PostConstruct
	public void init(){		
		if(ELFlash.getFlash().get("oc") != null){
			oc = (Oc) ELFlash.getFlash().get("oc");			
			ELFlash.getFlash().put("oc",null);
		}
	}
	    
	@PreDestroy
	public void destroy() {}
	
	 
	public void defineClienteBuscaOC(SelectEvent event){
		oc.setCliente((Cliente) event.getObject());
	}
	
	public void limparCliente(){
		oc.setCliente(new Cliente());
	}
	
	public void adicionarProdutoOc(Ocproduto ocproduto){
		ocproduto.setOc(this.getOc());
		ocproduto.getStatus().setId(1);
		oc.getOcprodutos().add(ocproduto);
		ocproduto = new Ocproduto();
		calculaValorTotalProdutos();
	}
	
	public void gravarProdutoAdicionaOc(Ocproduto ocproduto) throws ConstraintViolationException, HibernateException, Exception{		
		Produto produto = new Produto();
		ocproduto.getProduto().setDeleted(false);
		ocproduto.getProduto().setFornecedor(new FornecedoresDAO().buscaObjetoId(ocproduto.getProduto().getFornecedor().getId()));
		if(ocproduto.getProduto().getEhPlanejado()){
			ocproduto.getProduto().setTemMontagem(true);
		}
		produto = new ProdutoDAO().gravarProdutoAdicionarOc(ocproduto.getProduto());
		ocproduto.setOc(this.getOc());
		ocproduto.getStatus().setId(1);
		oc.getOcprodutos().add(ocproduto);
		ocproduto = new Ocproduto();
		calculaValorTotalProdutos();
	}
	
	public void removerProdutoOc(Ocproduto ocproduto){		
		try {
			OcProdutoDAO ocProdutoDAO = new OcProdutoDAO();
			ocProdutoDAO.delete(ocproduto);
			oc.getOcprodutos().remove(ocproduto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto deletado!"));
		} catch (ConstraintViolationException e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Constraint]",""));
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Hibernate]",""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Genérico]",""));
		}finally{
			calculaValorTotalProdutos();
		}
	}
	
	public void removeCondicoesPagamento(Pagamento pagamento){
		if(pagamento.getId() != null){						
			try {
				PagamentoDAO pagamentoDAO = new PagamentoDAO();
				pagamentoDAO.delete(pagamento);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condição deletada!"));
				oc.getPagamentos().remove(pagamento);
			} catch (ConstraintViolationException e) {				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Constraint]",""));
			} catch (HibernateException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Hibernate]",""));
			} catch (Exception e) {				
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Condição não deletada [Erro Genérico]",""));
			}
		}else{
			oc.getPagamentos().remove(pagamento);
		}
		pagamento = new Pagamento();
	}
	
	public void calculaValorTotalProdutos(){
		oc.setValor(0);		
		for(Ocproduto ocproduto : oc.getOcprodutos()){
			ocproduto.setValortotal(ocproduto.getValorunitario() * ocproduto.getQuantidade());
		}
		
		for(Iterator<Ocproduto> iterOcProd = oc.getOcprodutos().iterator(); iterOcProd.hasNext();){
			Ocproduto ocproduto =  iterOcProd.next();			
			oc.setValor(oc.getValor() + ocproduto.getValortotal());
		}		
		ocproduto = new Ocproduto();
	}

	
	public void defineFormaPagamento() throws ConstraintViolationException, HibernateException, Exception{		
		getPagamento().getCondicoesPagamento().setFormapagamento(new FormaPagamentoDAO().buscaObjetoId(getPagamento().getCondicoesPagamento().getFormapagamento().getId()));
		if(getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4){
			getPagamento().setBanco(new Banco());
			getPagamento().setCliente(new Cliente());
			getPagamento().getCliente().setId(oc.getCliente().getId());
		}
	}
	
	public void defineCondicoesPagamento() throws ConstraintViolationException, HibernateException, Exception{
		CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
		condicoesPagamento = new CondicoesPagamentoDAO().buscaObjetoId(getPagamento().getCondicoesPagamento().getId());
		getPagamento().setCondicoesPagamento(condicoesPagamento);
	}
	
	public void gravaFormaPagamentoOc(){
		Float totalPagamento = new Float(0);
		for(Pagamento pagamentos : getOc().getPagamentos()){
			if(!pagamentos.getDeleted()){
				totalPagamento += pagamentos.getValor();
			}
		}
		
		if(getOc().getTipoFrete().equalsIgnoreCase("loja")){
			if((totalPagamento + getPagamento().getValor()) > getOc().getValorfinal()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As condições de pagamento excedem o valor da compra", ""));
			}else{
				getPagamento().setOc(getOc());
				getOc().getPagamentos().add(getOc().getPagamentos().size(), getPagamento());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condições de pagamento: " + 
																					pagamento.getCondicoesPagamento().getFormapagamento().getNome() + " " +
																					pagamento.getCondicoesPagamento().getNome() + " inserida!"));
				setPagamento(new Pagamento());
			}
		}else{
			if((totalPagamento + getPagamento().getValor()) > (getOc().getValorfinal() - getOc().getValorfrete())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As condições de pagamento excedem o valor da compra", ""));
			}else{
				getPagamento().setOc(getOc());
				getOc().getPagamentos().add(getOc().getPagamentos().size(), getPagamento());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Condições de pagamento: " + 
																					pagamento.getCondicoesPagamento().getFormapagamento().getNome() + " " +
																					pagamento.getCondicoesPagamento().getNome() + " inserida!"));
				setPagamento(new Pagamento());
			}
		}
		
	}
	
	public boolean habilitaCondicoesPagamento(){
		if(getOc().getTipoFrete().equalsIgnoreCase("loja")){
			if(getTotalPagamento() < oc.getValorfinal()){
				return false;
			}else{
				try {
					if(oc.getStatus().getId() <= 2){
						return true;
					}else{
						return false;
					}
				} catch(NullPointerException e) {
					return true;
				}
			}
		}else{
			if(getTotalPagamento() < (oc.getValorfinal() - oc.getValorfrete())){
				return false;
			}else{
				return true;
			}
		}		
	}
	
	
	@Override
	public void gravar() {
		try {
			ocDAO = new OcDAO();
			oc.setStatus((Status) new StatusDAO().buscaObjetoId(1));
			//calculaValorComissao();
			recalculaValorTotalOcproduto();
			ocDAO.insert(oc);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OC foi gravada!"));
			logger.info("Salvo Oc: " + oc.getId());
			oc = new Oc();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar] Erro Constraint: " + super.mensagem + "-" + oc.getId());
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[gravar] Erro Hibernate: " + super.mensagem + "-" + oc.getId());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[gravar] Erro genérico: " + super.mensagem + "-" + oc.getId());
		}
		
	}

	@Override
	public void deletar() {
		try {
			ocDAO = new OcDAO();
			oc.setDeleted(true);
			oc.getStatus().setId(10);
			ocDAO.delete(oc);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OC " + oc.getId() + " foi deletada e o seu status é CANCELADO!"));
			logger.info("Deletado Oc: " + oc.getId());
			oc = new Oc();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[deletar] Erro Constraint: " + super.mensagem + "-" + oc.getId());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[deletar] Erro Hibernate: " + super.mensagem + "-" + oc.getId());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[deletar] Erro genérico: " + super.mensagem + "-" + oc.getId());
		}
	}

	@Override
	public void alterar() {
		try {
			ocDAO = new OcDAO();
			if(oc.getStatus().getId() != 7 && oc.getPagamentos().size() > 0){
				oc.setStatus(new StatusDAO().buscaObjetoId(2));
			}
			if(oc.getStatus().getId() == 7 && oc.getValorliquido() == 0){			
				calculaValorComissao();
				oc.setStatus(new OcProdutoControl().retornaMenorStatusOcProduto(oc, false));
			}
			recalculaValorTotalOcproduto();
			ocDAO.update(oc);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OC " + oc.getId() + " foi alterada!"));
			logger.info("Alterado Oc: " + oc.getId());
			oc = new Oc();
		} catch (ConstraintViolationException e) {			
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar] Erro Constraint: " + super.mensagem + "-" + oc.getId());
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar] Erro Hibernate: " + super.mensagem + "-" + oc.getId());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[alterar] Erro genérico: " + super.mensagem + "-" + oc.getId());
		}

	}
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("oc", this.getOc());
		return "cadastraoc?faces-redirect=true";
	}

	@Override
	public List<Oc> listarAtivos() {
		//List listaOcAtivo = new ArrayList();
		try{
			ocDAO = new OcDAO();
			listaOc = ocDAO.listaAtivos();
		}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
		return listaOc;
	}

	@Override
	public List<Oc> listarTodos() {
		return null;
	}

	@Override
	public List<Oc> listaSelecao(Object obj) {
		return null;
	}

	@Override
	public Oc buscaObjetoId(Integer id) {
		try {
			ocDAO = new OcDAO();
			oc = ocDAO.buscaObjetoId(id);
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
		return oc;
	}

	public List<Oc> getListaOc() {
		return listaOc;
	}

	public void setListaOc(List<Oc> listaOc) {
		this.listaOc = listaOc;
	}
	
	public void calculaValorComissao() throws ConstraintViolationException, NumberFormatException, HibernateException, Exception{				
		Float valorImpermeabilizacao = new Float(0);
		Float valorLiquidoImpermeabilizacao = new Float(0);
		float percentualFreteValorTotal = 0;
		double valorComissao = 0;  
		List<ComissaoVendedor> listaComissaoVendedor = new ArrayList<ComissaoVendedor>(); 
		percentualRetencao = new Float(0);
		valorLiquido = null;
		
		
		for(Ocproduto ocproduto : oc.getOcprodutos()){
			if(ocproduto.getProduto().getDescricao().equalsIgnoreCase("Impermeabilização")){				
				valorImpermeabilizacao = ocproduto.getValortotal();
			}
		}
		
		for(Pagamento pagamentoOc : oc.getPagamentos()){			
			percentualRetencao = null;
			valorLiquido = null;
			percentualRetencao = pagamentoOc.getCondicoesPagamento().getPercentual() / 100;
			if(pagamentoOc.getCondicoesPagamento().getFormapagamento().getEhantecipacao()){
				percentualRetencao +=  pagamentoOc.getCondicoesPagamento().getFormapagamento().getPercentualAntecipacao() /100;
			}
			valorLiquido = pagamentoOc.getValor() - (pagamentoOc.getValor() * percentualRetencao);
			
			oc.setValorliquido(oc.getValorliquido() + valorLiquido);
		}
		
		if(oc.getTipoFrete().equalsIgnoreCase("Loja") && oc.getValorfrete() > 0){
			percentualFreteValorTotal = oc.getValorfrete() / oc.getValorfinal();
		}
		
		if(valorImpermeabilizacao != 0){
			valorLiquidoImpermeabilizacao = (oc.getValorliquido() / oc.getValorfinal()) * valorImpermeabilizacao;
		}
		
		ComissaoDAO comissaoDAO = new ComissaoDAO();		
		Comissao comissao = comissaoDAO.buscaComissaoUsuarioVendedor(oc.getUsuario());
		
		
		
		if(comissao != null){
			ComissaoVendedor comissaoVendedor = new ComissaoVendedor();
			if(comissao.getEhComissaoIndividual()){
				valorComissao = ((oc.getValorliquido() - valorLiquidoImpermeabilizacao - (oc.getValorliquido() * percentualFreteValorTotal)) * (comissao.getPercentualComissaoIndividual() / 100))
								+ new Float(valorLiquidoImpermeabilizacao * 0.25);
				
				comissaoVendedor.setOc(oc);
				comissaoVendedor.setValor(valorComissao);
				comissaoVendedor.setVendedor(comissao.getUsuario());
				listaComissaoVendedor.add(comissaoVendedor);				
			}else{
				valorComissao = ((oc.getValorliquido() - valorLiquidoImpermeabilizacao - (oc.getValorliquido() * percentualFreteValorTotal)) * (comissao.getPercentualComissaoConjunta() / 100))
								+ new Float(valorLiquidoImpermeabilizacao * 0.25);
				
				comissaoVendedor.setOc(oc);
				comissaoVendedor.setValor(valorComissao / (comissao.getUsuarioComissaoConjunta().split(",").length + 1));
				comissaoVendedor.setVendedor(comissao.getUsuario());
				listaComissaoVendedor.add(comissaoVendedor);
				for(int i = 0; i < comissao.getUsuarioComissaoConjunta().split(",").length; i++){
					comissaoVendedor = new ComissaoVendedor();
					comissaoVendedor.setOc(oc);
					comissaoVendedor.setValor(valorComissao / (comissao.getUsuarioComissaoConjunta().split(",").length + 1));
					comissaoVendedor.setVendedor(new UsuarioDAO().buscaObjetoId(Integer.parseInt(comissao.getUsuarioComissaoConjunta().split(",")[i])));
					listaComissaoVendedor.add(comissaoVendedor);	
				}				
			}
			oc.setComissaoVendedores(listaComissaoVendedor);	
		}
	}
	
	public void liberarPagamento(){
		try {			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			for(Ocproduto ocprodutos: oc.getOcprodutos()){
				Produto produto = new Produto();				
				produto = produtoDAO.buscaObjetoId(ocprodutos.getProduto().getId());
				if(ocprodutos.getTiposaida().equalsIgnoreCase("estoque")){
					produto.setEstoque(produto.getEstoque() - ocprodutos.getQuantidade());
					ocprodutos.setStatus((Status) new StatusDAO().buscaObjetoId(7));
				}else if(ocprodutos.getTiposaida().equalsIgnoreCase("showroom")){
					produto.setShowroom(produto.getShowroom() - ocprodutos.getQuantidade());
					ocprodutos.setStatus((Status) new StatusDAO().buscaObjetoId(7));
				}else{
					produto.setEncomenda(produto.getEncomenda() + ocprodutos.getQuantidade());
					ocprodutos.setStatus((Status) new StatusDAO().buscaObjetoId(3));
				}
				produtoDAO.update(produto);
			}			
			oc.setStatus(new StatusDAO().buscaObjetoId(7));
			lancaParcelas();
			alterar();			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produtos da OC separados!"));
			logger.info(oc.getId() + " - com produtos separados!");
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na separação de produtos [Erro Constraint]", ""));
			logger.error("Erro constraint - separar produtos: [OC:" + oc.getId() + "] - " + super.mensagem);
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na separação de produtos [Erro Hibernate]", ""));
			logger.error("Erro hibernate - separar produtos: [OC:" + oc.getId() + "] - " + super.mensagem);
		} catch (Exception e) {
			super.mensagem = e.getMessage();			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na separação de produtos [Erro Genérico] [" + e.getMessage() + "]", ""));
			logger.error("Erro genérico - separar produtos: [OC:" + oc.getId() + "] - " + super.mensagem);
			e.printStackTrace();
		}
	}
	
	public void lancaParcelas(){
		Float valorParcelas = new Float(0.0);				
		try {
			for(Pagamento pagamento : oc.getPagamentos()){
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				if(pagamento.getCondicoesPagamento().getFormapagamento().getEhantecipacao()){
					Parcela parcela = new Parcela();
					c.add(Calendar.DAY_OF_MONTH, 1);
					percentualRetencao = pagamento.getCondicoesPagamento().getPercentual() / 100;
					valorLiquido = pagamento.getValor() - (pagamento.getValor() * percentualRetencao);				
					valorParcelas = valorLiquido / pagamento.getCondicoesPagamento().getParcelas();
					if(pagamento.getCondicoesPagamento().getFormapagamento().getEhantecipacao()){
						percentualRetencao +=  pagamento.getCondicoesPagamento().getFormapagamento().getPercentualAntecipacao() /100;
					}
					valorLiquido = pagamento.getValor() - (pagamento.getValor() * percentualRetencao);
					parcela.setPagamento(pagamento);
					parcela.setNumeroParcela(1);
					parcela.setValor(valorLiquido);
					parcela.setDataentrada(c.getTime());
					parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Emitido" : null);
					pagamento.getParcelas().add(parcela);
				}else if(pagamento.getCondicoesPagamento().getAvista()){					
					Parcela parcela = new Parcela();
					parcela.setPagamento(pagamento);
					parcela.setNumeroParcela(1);
					parcela.setValor(pagamento.getValor());
					parcela.setDataentrada(c.getTime());
					parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Emitido" : null);
					pagamento.getParcelas().add(parcela);
				}else{		
					percentualRetencao = pagamento.getCondicoesPagamento().getPercentual() / 100;				
					valorLiquido = pagamento.getValor() - (pagamento.getValor() * percentualRetencao);				
					valorParcelas = valorLiquido / pagamento.getCondicoesPagamento().getParcelas();
					for(int i = 1; i <= pagamento.getCondicoesPagamento().getParcelas(); i++){
						Parcela parcela = new Parcela();
						c.add(Calendar.DAY_OF_MONTH,30);
						parcela.setPagamento(pagamento);
						parcela.setNumeroParcela(i);
						parcela.setValor(valorParcelas);
						parcela.setDataentrada(c.getTime());
						parcela.setSituacaoCheque(parcela.getPagamento().getCondicoesPagamento().getFormapagamento().getId() == 4 ? "Emitido" : null);
						pagamento.getParcelas().add(parcela);
					}
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Parcelas lançadas!"));
			logger.info("Parcelas lançadas: " + oc.getId());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no lançamento das parcelas [OC:" + oc.getId() + "]", ""));
			logger.error("Erro genérico - Lançamento parcelas: [OC:" + oc.getId() + "] - " + super.mensagem);
		}		
	}
	
	public void addProdutoAcao(SelectEvent selectEvent){
		ocproduto = (Ocproduto) selectEvent.getObject();
		if(listaOcProdutoAcao.size() == 0){
			listaOcProdutoAcao.add(ocproduto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto " + ocproduto.getProduto().getDescricao() + " adicionado a lista de ações!"));
		}else{
			if(listaOcProdutoAcao.get(0).getStatus().getId() == ocproduto.getStatus().getId()){
				listaOcProdutoAcao.add(ocproduto);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto " + ocproduto.getProduto().getDescricao() + " adicionado a lista de ações!"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto não adicionado, pois tem status diferente dos demais selecionados", ""));
			}
		}
	}
	
	public void subProdutoAcao(UnselectEvent unselectEvent){
		try {
			ocproduto = (Ocproduto) unselectEvent.getObject();
			listaOcProdutoAcao.remove(ocproduto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto " + ocproduto.getProduto().getDescricao() + " retirado da lista de ações!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto não retirado da lista!", ""));
		}
		
	}
	
	public void acaoProduto(Long idAcao, List<Integer> listaMontadores) {	
		try {
			ocDAO = new OcDAO();
			if(listaOcProdutoAcao.size() > 0){
				Status status = (Status) new StatusDAO().buscaObjetoId(idAcao.intValue());
				Frete frete = new Frete();
				Assistenciatecnica assistenciatecnica = new Assistenciatecnica();				
				if(idAcao.intValue() == 6){
					frete = new FreteControl().gravarFreteOcProduto(getListaOcProdutoAcao(), listaMontadores, getObservacoesFreteMontAT());
				}
				if(idAcao.intValue() == 8){
					assistenciatecnica = new AssistenciaTecnicaControl().gravarAssistTecnicaProduto(getListaOcProdutoAcao(), listaMontadores, getObservacoesFreteMontAT());
				}
				for(Ocproduto ocprodutoAcao : listaOcProdutoAcao){					
					for(Ocproduto ocproduto : getOc().getOcprodutos()){
						if(ocproduto.equals(ocprodutoAcao)){
							ocproduto.setStatus(status);
							if(idAcao.intValue() == 6){
								ocproduto.setFrete(frete);
							}if(idAcao.intValue() == 8){
								ocproduto.setAssistenciatecnica(assistenciatecnica);
							}
						}
					}
				}
				oc.setStatus(new OcProdutoControl().retornaMenorStatusOcProduto(oc, false));
				ocDAO.update(oc);
				logger.info("Ação: " + status.getDescricao() + " da OC " + getOc().getId() + " foi gravado");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produtos alterados com sucesso para o status " + status.getDescricao()));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Favor selecionar um produto da OC para a ação!", ""));
			}
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar_status] Erro Constraint: " + super.mensagem + "-" + "Alteração de status não gravada");
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar_status] Erro Hibernate: " + super.mensagem + "-" + "Alteração de status não gravada");			
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("[alterar_status] Erro Hibernate: " + super.mensagem + "-" + "Alteração de status não gravada");
		}
	}
	
	public List<Oc> listaCalculaVendasPorVendedor(){
		ocDAO = new OcDAO();
		listaOc = new ArrayList<Oc>();
		listaOc = ocDAO.calculaVendasPorVendedor(getDataInicial(), getDataFinal());
		return listaOc;
	}
	
	public void buscaOcPorDataLancamento(){
		listarOcGeral = null;
		getListarOcGeralAll();
	}

	public List<Oc> getListaVendasMesAno(){
		ocDAO = new OcDAO();
		listaOc = new ArrayList<Oc>();
		listaOc = ocDAO.calcultaTotalVendasMesAno(getDataInicial(),getDataFinal());		
		return listaOc;
	}
	
	public void buscarVendasMesAno(){
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(dataInicial);
		calendar.set(calendar.DATE, 1);
		dataInicial = calendar.getTime();
		
		calendar.setTime(dataFinal);
		calendar.set(calendar.DATE, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		dataFinal = calendar.getTime();
		
	}
	
	
	public void calculaValorTotal(){
		oc.setValorfinal(oc.getValor() + oc.getValorfrete() + oc.getValormontagem());
	}
	
	public Double calculaVendaBruto(Date dataInicial, Date dataFinal) {
		ocDAO = new OcDAO();
		Double totalVendasBruto = ocDAO.buscaVendasBruto(dataInicial, dataFinal);
		return totalVendasBruto;
	}
	
	public Double calculaFretePago(Date dataInicial, Date dataFinal) {
		ocDAO = new OcDAO();
		Double totalFretePago = ocDAO.buscaFretePago(dataInicial, dataFinal);
		return totalFretePago;
	}
	
	public void impressaoOc(Oc oc){		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();			
			InputStream caminho = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Map<String, Object> parametros = new HashMap<String, Object>();
			String caminhoRelatorio = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/br/com/casabemestilo/relatorio/");
			
			listaOc = new ArrayList<Oc>();
			
			if(caminhoRelatorio.indexOf("home") > -1)
				caminhoRelatorio += "/";
			else
				caminhoRelatorio += "\\";
			
			caminho = getClass().getResourceAsStream("../relatorio/oc.jrxml");
			response.setContentType("application/pdf");			
			parametros.put("SUBREPORT_DIR", caminhoRelatorio);
			response.setHeader("Content-Disposition","attachment; filename=\"OC:" + oc.getId() +" - " + oc.getCliente().getNome() +".pdf\"");
			JasperReport pathReport = JasperCompileManager.compileReport(caminho);
			
			listaOc.add(oc);
			JRDataSource jrds = new JRBeanArrayDataSource(listaOc.toArray());
			
			JasperPrint preencher = JasperFillManager.fillReport(pathReport,parametros,jrds);		
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
		}
	}
	
	public void estornar(){
		Status status = new Status();
		Pedidoproduto pedidoproduto = new Pedidoproduto();
		
		try {			
			status = new StatusDAO().buscaObjetoId(10);
			ocDAO = new OcDAO();
			
			//Define status da OC para cancelado
			oc.setStatus(status);
			oc.setDeleted(true);
			
			//Define status dos PRODUTOS para cancelado
			for(Ocproduto ocproduto : oc.getOcprodutos()){
				ocproduto.setStatus(status);
			}
			
			/*Retorna todos os produtos para ESTOQUE ou SHOWROOM
			 * caso esteja encomendado e não chegou ainda a encomenda, é
			 * retirada o vinculo da ocproduto da encomenda para que quando chegar
			 * o mesmo vá para ESTOQUE, se já chegou realoca no ESTOQUE. 
			 */
			for(Ocproduto ocproduto : oc.getOcprodutos()){
				if(ocproduto.getTiposaida().equals("estoque")){
					ocproduto.getProduto().setEstoque(ocproduto.getProduto().getEstoque() + ocproduto.getQuantidade());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto: " + ocproduto.getProduto().getDescricao() + "-" +
																		ocproduto.getProduto().getFornecedor().getNome() + 
																		" foi realocado para ESTOQUE"));
				}else if(ocproduto.getTiposaida().equals("showroom")){
					ocproduto.getProduto().setShowroom(ocproduto.getProduto().getShowroom() + ocproduto.getQuantidade());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto: " + ocproduto.getProduto().getDescricao() + "-" +
																		ocproduto.getProduto().getFornecedor().getNome() + 
																		" foi realocado para SHOWROOM"));
				}else{
					pedidoproduto = new PedidoProdutoDAO().buscaPedidoOcProduto(ocproduto);
					if(pedidoproduto != null){
						if(pedidoproduto.getPedido().getDatachegada() != null){
							ocproduto.getProduto().setEstoque(ocproduto.getProduto().getEstoque() + ocproduto.getQuantidade());
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto: " + 
																				ocproduto.getProduto().getDescricao() + "-" + 
																				ocproduto.getProduto().getFornecedor().getNome() + 
																				" foi realocado no estoque", ""));
						}else{
							pedidoproduto.setOcproduto(null);
							new PedidoProdutoDAO().desvicunlarOcProdutoPedido(pedidoproduto);
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto: " + 
																				ocproduto.getProduto().getDescricao() + "-" + 
																				ocproduto.getProduto().getFornecedor().getNome() + 
																				" já foi encomendado mas não chegou, o produto no pedido teve seu vinculo retirado, " +
																				" quando o pedido chegar o mesmo será realocado no estoque!", ""));
						}						
					}
					ocproduto.getProduto().setEncomenda(ocproduto.getProduto().getEncomenda() - ocproduto.getQuantidade());
				}
			}
			
			//Define todas os PAGAMENTOS e PARCELAS para deletado. 
			for(Pagamento pagamento : oc.getPagamentos()){
				pagamento.setDeleted(true);
				for(Parcela parcela : pagamento.getParcelas()){
					parcela.setDeleted(true);
				}
			}
			
			//Busca as comissões dos vendedores
			oc.setComissaoVendedores(new ComissaoVendedorDAO().listaComissaoOc(oc));
	
			//Define a comissão como deletado
			for(ComissaoVendedor comissaoVendedor : oc.getComissaoVendedores()){
				comissaoVendedor.setDeleted(true);
			}
			
			//Persiste todas as alterações.
			ocDAO.update(oc);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produtos alterados com sucesso para o status de CANCELADO"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pagamentos DELETADOS"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comissão do vendedor DELETADOS!"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Processo de ESTORNO realizado com sucesso!"));			
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto(s) retornado(s) para o status anterior!", ""));
			e.printStackTrace();
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto(s) retornado(s) para o status anterior!", ""));
			e.printStackTrace();
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto(s) retornado(s) para o status anterior!", ""));
			e.printStackTrace();
		}		
	}
	
	public void recalculaValorTotalOcproduto(){
		for(Ocproduto ocproduto : oc.getOcprodutos()){
			ocproduto.setValortotal(ocproduto.getValorunitario() * ocproduto.getQuantidade());
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Oc getOc() {
		if(oc == null){
			oc = new Oc();
		}
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}

	public OcDAO getOcDAO() {
		return ocDAO;
	}

	public void setOcDAO(OcDAO ocDAO) {
		this.ocDAO = ocDAO;
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


	public Float getTotalPagamento() {
		totalPagamento = new Float(0);
		for(Iterator<Pagamento> iterPagamento = oc.getPagamentos().iterator(); iterPagamento.hasNext();){
			Pagamento pagamento = iterPagamento.next();
			if(!pagamento.getDeleted()){
				totalPagamento += pagamento.getValor();
			}			
		}
		return totalPagamento;
	}

	public void setTotalPagamento(Float totalPagamento) {
		this.totalPagamento = totalPagamento;
	}

	public Ocproduto getOcproduto() {
		if(ocproduto == null){
			ocproduto = new Ocproduto();
		}
		return ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}

	
	public LazyDataModel<Oc> getListarOcGeral() {		
		return listarOcGeral;
	}

	public void setListarOcGeral(LazyDataModel<Oc> listarOcGeral) {
		this.listarOcGeral = listarOcGeral;
	}
	
	public LazyDataModel<Oc> getListarOcGeralAll(){
		if(listarOcGeral == null){
			listarOcGeral = new LazyDataModel<Oc>() {
								private List<Oc> listaLazyOc;
								
								@Override
							    public Oc getRowData(String idOc) {
							    	Integer id = Integer.valueOf(idOc);
							    	
							        for(Oc oc : listaLazyOc) {
							            if(oc.getId().equals(id))
							                return oc;
							        }
							        
							        return null;
							    }

							    @Override
							    public Object getRowKey(Oc oc) {
							        return oc.getId();
							    }

							    @Override
							    public List<Oc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
							    	OcDAO ocDAO = new OcDAO();  
							    	
							    	listaLazyOc = ocDAO.listaLazy(first, pageSize, filters, getDataInicial(), getDataFinal());
							    	
							    	if (getRowCount() <= 0) {  
							            setRowCount(ocDAO.totalOc(filters, getDataInicial(), getDataFinal()));  
							        }  
							       
							        setPageSize(pageSize);  
							        return listaLazyOc;  
							    }
				};
		}		
		return listarOcGeral;
	}
	
	public LazyDataModel<Oc> getListarOcMontFreteAll(){
		if(listarOcGeral == null){
			listarOcGeral = new LazyDataModel<Oc>() {
								private List<Oc> listaLazyOc;
								
								@Override
							    public Oc getRowData(String idOc) {
							    	Integer id = Integer.valueOf(idOc);
							    	
							        for(Oc oc : listaLazyOc) {
							            if(oc.getId().equals(id))
							                return oc;
							        }
							        
							        return null;
							    }

							    @Override
							    public Object getRowKey(Oc oc) {
							        return oc.getId();
							    }

							    @Override
							    public List<Oc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
							    	OcDAO ocDAO = new OcDAO(); 
							    	
							    	listaLazyOc = ocDAO.listaLazyMontFrete(first, pageSize);
							    	
							    	if (getRowCount() <= 0) { 
							            setRowCount(ocDAO.totalOcMontFrete());  
							        }  
							    	
							        setPageSize(pageSize);  
							        return listaLazyOc;
							    }
				};
		}		
		return listarOcGeral;
	}
	
	public LazyDataModel<Oc> getListaOcStatusProduto(){
		if(listarOcGeral == null){
			listarOcGeral = new LazyDataModel<Oc>() {
								private List<Oc> listaLazyOc;
								
								@Override
							    public Oc getRowData(String idOc) {
							    	Integer id = Integer.valueOf(idOc);
							    	
							        for(Oc oc : listaLazyOc) {
							            if(oc.getId().equals(id))
							                return oc;
							        }
							        
							        return null;
							    }

							    @Override
							    public Object getRowKey(Oc oc) {
							        return oc.getId();
							    }

							    @Override
							    public List<Oc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
							    	OcDAO ocDAO = new OcDAO();
							    	
							    	listaLazyOc = ocDAO.listaLazyStatusProduto(first, pageSize, filters);
							    	
							    	if (getRowCount() <= 0) { 
							            setRowCount(ocDAO.totalOcStatusProduto(filters));  
							        }  
							    	
							        setPageSize(pageSize);  
							        return listaLazyOc;
							    }
				};
		}		
		return listarOcGeral;
	}
	
	public List<String> getListaTipoFrete() {
		if(listaTipoFrete == null){
			listaTipoFrete = new ArrayList<String>();
			listaTipoFrete.add("Local");
			listaTipoFrete.add("Loja");
			listaTipoFrete.add("Brinde");
		}
		return listaTipoFrete;
	}

	public void setListaTipoFrete(List<String> listaTipoFrete) {
		this.listaTipoFrete = listaTipoFrete;
	}

	public List<Ocproduto> getListaOcProdutoAcao() {
		return listaOcProdutoAcao;
	}

	public void setListaOcProdutoAcao(List<Ocproduto> listaOcProdutoAcao) {
		this.listaOcProdutoAcao = listaOcProdutoAcao;
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

	public String getObservacoesFreteMontAT() {
		return observacoesFreteMontAT;
	}

	public void setObservacoesFreteMontAT(String observacoesFreteMontAT) {
		this.observacoesFreteMontAT = observacoesFreteMontAT;
	}

	public Boolean getEhClienteChequeOc() {
		if(ehClienteChequeOc == null){
			ehClienteChequeOc = true;
		}
		return ehClienteChequeOc;
	}

	public void setEhClienteChequeOc(Boolean ehClienteChequeOc) {
		this.ehClienteChequeOc = ehClienteChequeOc;
	}

		
}
