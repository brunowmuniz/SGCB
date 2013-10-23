package br.com.casabemestilo.model;


import java.util.List;


public class Movimentacao {

	private String descricao;
	
	private TipoMovimentacao tipoMovimentacao;
	
	private List<Formapagamento> pagamentos;
	
	private Double valor;
	
	
	
	public Movimentacao(String descricao, TipoMovimentacao tipoMovimentacao,
			List<Formapagamento> pagamentos, Double valor) {
		super();
		this.descricao = descricao;
		this.tipoMovimentacao = tipoMovimentacao;
		this.pagamentos = pagamentos;
		this.valor = valor;
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

	public List<Formapagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Formapagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	
}
