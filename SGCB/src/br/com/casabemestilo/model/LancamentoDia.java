package br.com.casabemestilo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LancamentoDia {

	private Date dataLancamento;
	
	private int totalLancamentos = 0;
	
	private Float valorTotalLancamentos = new Float("0");
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	public LancamentoDia(Date dataLancamento, int totalLancamentos,
			Float valorTotalLancamentos, List<Lancamento> lancamentos) {
		super();
		this.dataLancamento = dataLancamento;
		this.totalLancamentos = totalLancamentos;
		this.valorTotalLancamentos = valorTotalLancamentos;
		this.lancamentos = lancamentos;
	}

	public LancamentoDia() {
		super();
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public int getTotalLancamentos() {
		return totalLancamentos;
	}

	public void setTotalLancamentos(int totalLancamentos) {
		this.totalLancamentos = totalLancamentos;
	}

	public Float getValorTotalLancamentos() {
		return valorTotalLancamentos;
	}

	public void setValorTotalLancamentos(Float valorTotalLancamentos) {
		this.valorTotalLancamentos = valorTotalLancamentos;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
}
