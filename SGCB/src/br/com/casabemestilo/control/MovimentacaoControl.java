package br.com.casabemestilo.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.DAO.LancamentoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.model.Formapagamento;
import br.com.casabemestilo.model.Lancamento;
import br.com.casabemestilo.model.Movimentacao;
import br.com.casabemestilo.model.Oc;

@ManagedBean
@ViewScoped
public class MovimentacaoControl {

	private Movimentacao movimentacao;
	
	private List<Movimentacao> movimentacoes;

	private Date dataLancamento;
	
	public MovimentacaoControl(Movimentacao movimentacao,
			List<Movimentacao> movimentacoes, Date dataLancamento) {
		
		this.movimentacao = movimentacao;
		this.movimentacoes = movimentacoes;
		this.dataLancamento = dataLancamento;
	}
	
	public MovimentacaoControl() {
		super();
	
	}

	
	public List<Movimentacao> listaCaixa(){
		OcDAO ocDAO = new OcDAO();
		LancamentoDAO lancamentoDAO = new LancamentoDAO();
		List<Oc> ocs = new ArrayList<Oc>();
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		List<Formapagamento> formapagamentos = new ArrayList<Formapagamento>();
		
		try {
			formapagamentos = new FormaPagamentoDAO().listaAtivos();
			ocs = ocDAO.buscaOcDia(dataLancamento);
			lancamentos = lancamentoDAO.lancamentoDia(dataLancamento);
			
			movimentacao = new Movimentacao();
			for(Formapagamento formapagamento : formapagamentos){
				
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
		
		return movimentacoes;
	}
	
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
