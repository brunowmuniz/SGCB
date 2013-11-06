package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import net.sf.ehcache.config.TerracottaConfiguration.Consistency;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.org.apache.bcel.internal.generic.SALOAD;

import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.LancamentoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.model.Formapagamento;
import br.com.casabemestilo.model.Lancamento;
import br.com.casabemestilo.model.Movimentacao;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.model.TipoMovimentacao;

@ManagedBean
@ViewScoped
public class MovimentacaoControl extends Control implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Movimentacao movimentacao;
	
	private List<Movimentacao> movimentacoes;

	private Date dataLancamento;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public MovimentacaoControl(Movimentacao movimentacao,
			List<Movimentacao> movimentacoes, Date dataLancamento) {		
		this.movimentacao = movimentacao;
		this.movimentacoes = movimentacoes;
		this.dataLancamento = dataLancamento;
	}
	
	public MovimentacaoControl() {
		super();	
	}

	
	/*
	 * MÉTODOS
	 * */
	public List<Movimentacao> listaCaixa(){
		OcDAO ocDAO = new OcDAO();
		LancamentoDAO lancamentoDAO = new LancamentoDAO();
		List<Oc> ocs = new ArrayList<Oc>();
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		List<Formapagamento> formapagamentos = new ArrayList<Formapagamento>();
		movimentacoes = new ArrayList<Movimentacao>();
		
		try {
			formapagamentos = new FormaPagamentoDAO().listaAtivos();
			ocs = ocDAO.buscaOcDia(dataLancamento);
			lancamentos = lancamentoDAO.lancamentoDia(dataLancamento);
			List<Pagamento> listaSaldoAnteriorPagamentos = new PagamentoDAO().calculaSaldoAnterior(dataLancamento);
			List<Lancamento> listaSaldoAnteriorLancamentos = lancamentoDAO.calculaSaldoAnterior(dataLancamento);
			
			movimentacao = new Movimentacao();
			movimentacao.setPagamentos(new ArrayList<Pagamento>());
			
			for(Formapagamento formapagamento : formapagamentos){
				Float valorLancamento = new Float("0");
				Float valorPagamento = new Float("0");
				CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
				Pagamento pagamentoSaldoAnterior = new Pagamento();
				
				condicoesPagamento.setFormapagamento(formapagamento);
				pagamentoSaldoAnterior.setCondicoesPagamento(condicoesPagamento);				
				
				for(Pagamento pagamento : listaSaldoAnteriorPagamentos){
					if(pagamento.getCondicoesPagamento().getFormapagamento().getId() == formapagamento.getId()){
						valorPagamento = pagamento.getValor();
					}
				}
				
				for(Lancamento lancamento : listaSaldoAnteriorLancamentos){
					if(lancamento.getFormapagamento().getId() == formapagamento.getId()){
						valorLancamento = lancamento.getValor();
					}
				}				
				
				pagamentoSaldoAnterior.setValor(valorPagamento + valorLancamento);
				movimentacao.getPagamentos().add(pagamentoSaldoAnterior);
			}
			
			movimentacao.setDescricao("Saldo Anterior");
			movimentacao.setLancamento(null);
			movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
			movimentacoes.add(movimentacao);
			
			for(Oc oc : ocs){
				movimentacao = new Movimentacao();
				formapagamentos = new ArrayList<Formapagamento>();
				movimentacao.setDescricao("OC: " + oc.getId() + " - Cliente: " + oc.getCliente().getNome());
				movimentacao.setPagamentos(oc.getPagamentos());				
				movimentacao.setLancamento(null);
				movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
				movimentacoes.add(movimentacao);
			}
			
			for(Lancamento lancamento : lancamentos){
				movimentacao = new Movimentacao();
				movimentacao.setDescricao(lancamento.getDescricao());
				movimentacao.setPagamentos(null);				
				movimentacao.setLancamento(lancamento);
				movimentacao.setTipoMovimentacao(lancamento.getContacontabil().getTipo() == "D" ? TipoMovimentacao.SAIDA : TipoMovimentacao.ENTRADA);
				movimentacoes.add(movimentacao);
			}		
			
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		}catch (NullPointerException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Null: " + super.mensagem, ""));
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
		}
		
		return movimentacoes;
	}
	
	public String defineEstiloLancamento(Float valorLancamento){
		if(valorLancamento > 0)
			return "credito";
		else
			return "debito";
	}
	
	public Float totalMomentoFormaPagamento(Integer idFormaPgto){
		Float totalFormaPgto = new Float("0");
		try {
			if(movimentacoes == null){
				listaCaixa();
			}
			for(Movimentacao movimentacao : movimentacoes){
				if(movimentacao.getPagamentos() != null){
					for(Pagamento pagamento : movimentacao.getPagamentos()){
						if(pagamento.getCondicoesPagamento().getFormapagamento().getId() == idFormaPgto){
							totalFormaPgto += pagamento.getValor();
						}
					}
					
				}else{
					if(movimentacao.getLancamento().getFormapagamento().getId() == idFormaPgto){
						totalFormaPgto += movimentacao.getLancamento().getValor();
					}
				}
			}
		} catch (NullPointerException e) {
			super.mensagem = e.getMessage();
			logger.error("[totalMomentoFormaPagamento] Erro NullPointer: " + super.mensagem + "-" + "calculo total momento não realizado!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Null: " + super.mensagem, ""));
		}
		
		return totalFormaPgto;
	}
	
	public Float calculaSaldoInicialDia(){
		Float totalInicialDia = new Float("0");
		movimentacao = movimentacoes.get(0);
		
		for(Pagamento pagamento : movimentacao.getPagamentos()){
			totalInicialDia += pagamento.getValor();
		}
		
		return totalInicialDia;
	}
	
	public Float calculaSaldoAtualDia(){
		Float totalAtualDia = new Float("0");
		
		for(Movimentacao movimentacao : movimentacoes){
			if(movimentacao.getPagamentos() != null){
				for(Pagamento pagamento : movimentacao.getPagamentos()){					
					totalAtualDia += pagamento.getValor();					
				}
			}else{
				totalAtualDia += movimentacao.getLancamento().getValor();				
			}
		}
			
		return totalAtualDia;
	}
	
	public void defineDataLancamento(ValueChangeEvent value){
		this.setDataLancamento((Date) value.getNewValue());
		listaCaixa();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Date getDataLancamento() {
		if(dataLancamento == null){
			dataLancamento = new Date();
		}
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	
}
