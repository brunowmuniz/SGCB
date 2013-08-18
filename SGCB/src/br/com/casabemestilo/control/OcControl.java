package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.sun.faces.context.flash.ELFlash;
import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.Status;
import br.com.casabemestilo.util.LazyOcDataModel;

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
			calculaValorComissao();
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
			calculaValorComissao();
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
	
	public void calculaValorComissao(){				
		Float valorImpermeabilizacao = new Float(0);
		Float valorLiquidoImpermeabilizacao = new Float(0);
		oc.setValorcomissao(new Float(0));
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
		
		if(valorImpermeabilizacao != 0){
			valorLiquidoImpermeabilizacao = (oc.getValorliquido() / oc.getValorfinal()) * valorImpermeabilizacao;
		}
		
		ComissaoDAO comissaoDAO = new ComissaoDAO();		
		Comissao comissao = comissaoDAO.buscaComissaoUsuario(oc.getUsuario());
		
		if(comissao != null){
			if(comissao.getEhComissaoIndividual()){			
				oc.setValorcomissao(((oc.getValorliquido() - valorLiquidoImpermeabilizacao) * (comissao.getPercentualComissaoIndividual() / 100))
									+ new Float(valorLiquidoImpermeabilizacao * 0.25));
			}else{
				oc.setValorcomissao(((oc.getValorliquido() - valorLiquidoImpermeabilizacao) * (comissao.getPercentualComissaoConjunta() / 100))
									+ new Float(valorLiquidoImpermeabilizacao * 0.25));
			}
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
			oc.setStatus((Status) new StatusDAO().buscaObjetoId(7));			
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
		}
	}
	
	public void lancaParcelas(){
		Float valorParcelas = new Float(0.0);
		Calendar c = Calendar.getInstance();		
		try {
			for(Pagamento pagamento : oc.getPagamentos()){
				if(pagamento.getCondicoesPagamento().getFormapagamento().getEhantecipacao()){
					Parcela parcela = new Parcela();
					c.setTime(new Date());
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
					pagamento.getParcelas().add(parcela);
				}else if(pagamento.getCondicoesPagamento().getAvista()){
					c.setTime(new Date());
					Parcela parcela = new Parcela();
					parcela.setPagamento(pagamento);
					parcela.setNumeroParcela(1);
					parcela.setValor(pagamento.getValor());
					parcela.setDataentrada(c.getTime());
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
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Oc getOc() {
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
			listarOcGeral = new LazyOcDataModel();
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
	
}
