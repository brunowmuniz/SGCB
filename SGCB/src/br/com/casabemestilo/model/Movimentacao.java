package br.com.casabemestilo.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Movimentacao {

	private String descricao;
	
	private TipoMovimentacao tipoMovimentacao;
	
	private List<Pagamento> pagamentos;
	
	
	private Lancamento lancamento;	
	
	
	public Movimentacao(String descricao, TipoMovimentacao tipoMovimentacao,
			List<Pagamento> pagamentos) {
		super();
		this.descricao = descricao;
		this.tipoMovimentacao = tipoMovimentacao;
		this.pagamentos = pagamentos;
	}
	
	public Movimentacao() {
		super();
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	
		
}
