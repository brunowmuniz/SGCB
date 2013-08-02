package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.swing.event.ChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.Select;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.DAO.StatusDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.Status;

@ManagedBean
@ViewScoped
public class OcControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Oc> listaOc;
	
	private Oc oc = new Oc();
	
	private OcDAO ocDAO;
	
	private List<Ocproduto> listaOcprodutos = new ArrayList<Ocproduto>();
	
	private Pagamento pagamento = new Pagamento();
	
	private List<Pagamento> listaOcPagamentos = new ArrayList<Pagamento>();
	
	private Float totalPagamento = new Float(0);
	
	
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
		// TODO Auto-generated constructor stub
	}

	public OcControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	public void defineClienteBuscaOC(SelectEvent event){
		oc.setCliente((Cliente) event.getObject());
	}
	
	public void limparCliente(){
		oc.setCliente(new Cliente());
	}
	
	public void adicionarProdutoOc(Ocproduto ocproduto){
		listaOcprodutos.add(ocproduto);
		oc.setOcprodutos(new ArrayList<Ocproduto>());
		calculaValorTotalProdutos();
	}
	
	public void gravarProdutoAdicionaOc(Ocproduto ocproduto) throws ConstraintViolationException, HibernateException, Exception{		
		Produto produto = new Produto();
		ocproduto.getProduto().setDeleted(false);
		ocproduto.getProduto().setFornecedor(new FornecedoresDAO().buscaObjetoId(ocproduto.getProduto().getFornecedor().getId()));
		produto = new ProdutoDAO().gravarProdutoAdicionarOc(ocproduto.getProduto());		
		listaOcprodutos.add(ocproduto);
		oc.setOcprodutos(new ArrayList<Ocproduto>());
		calculaValorTotalProdutos();
	}
	
	public void removerProdutoOc(Ocproduto ocproduto){
		listaOcprodutos.remove(ocproduto);
		calculaValorTotalProdutos();
	}
	
	public void removeCondicoesPagamento(Pagamento pagamento){
		oc.getPagamentos().remove(pagamento);
	}
	
	public void calculaValorTotalProdutos(){
		oc.setValor(0);
		for(Iterator<Ocproduto> iterOcProd = listaOcprodutos.iterator(); iterOcProd.hasNext();){
			Ocproduto ocproduto =  iterOcProd.next();
			oc.setValor(oc.getValor() + ocproduto.getValortotal());
		}		
	}
	
	public void atualizaValorTotal(TabChangeEvent event){
		calculaValorTotalProdutos();
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
		getPagamento().setOc(getOc());
		getOc().getPagamentos().add(getOc().getPagamentos().size(), getPagamento());		
		setPagamento(new Pagamento());
	}
	
	public boolean habilitaCondicoesPagamento(){		 
		if(getTotalPagamento() < oc.getValorfinal()){
			return false;
		}else{
			return true;
		}		
	}
	
	
	@Override
	public void gravar() {
		try {
			oc.getOcprodutos().clear();
			ocDAO = new OcDAO();
			oc.setOcprodutos(listaOcprodutos);
			oc.setStatus((Status) new StatusDAO().buscaObjetoId(1));
			calculaValorComissao();
			ocDAO.insert(oc);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OC " + oc.getId() + " foi gravada!"));
			oc = new Oc();
		} catch (ConstraintViolationException e) {			
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
		}
		
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
	public List<Oc> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Oc> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Oc> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Oc buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Oc> getListaOc() {
		return listaOc;
	}

	public void setListaOc(List<Oc> listaOc) {
		this.listaOc = listaOc;
	}
	
	public void calculaValorComissao(){
		oc.setValorcomissao(new Float(0));
		Float percentualRetencao = new Float(0);
		Float valorLiquido = null;		
		Float valorImpermeabilizacao = new Float(0);
		Float valorLiquidoImpermeabilizacao = new Float(0);
		
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

	public List<Ocproduto> getListaOcprodutos() {
		return listaOcprodutos;
	}

	public void setListaOcprodutos(List<Ocproduto> listaOcprodutos) {
		this.listaOcprodutos = listaOcprodutos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {		
		this.pagamento = pagamento;
	}

	public List<Pagamento> getListaOcPagamentos() {
		return listaOcPagamentos;
	}

	public void setListaOcPagamentos(List<Pagamento> listaOcPagamentos) {
		this.listaOcPagamentos = listaOcPagamentos;
	}

	public Float getTotalPagamento() {
		totalPagamento = new Float(0);
		for(Iterator<Pagamento> iterPagamento = oc.getPagamentos().iterator(); iterPagamento.hasNext();){
			Pagamento pagamento = iterPagamento.next();
			totalPagamento += pagamento.getValor();
		}
		return totalPagamento;
	}

	public void setTotalPagamento(Float totalPagamento) {
		this.totalPagamento = totalPagamento;
	}


}
