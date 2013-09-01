package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Lancamento generated by hbm2java
 */
@Entity
@Table(name = "lancamento", catalog = "blankerc_sgcb")
public class Lancamento implements java.io.Serializable {

	private Integer id;
	private Vale vale;
	private Contacontabil contacontabil;
	private String descricao;
	private float valor;
	private Boolean deleted;
	private Lancamento lancamentoPai;
	private Integer parcela;
	private Integer qtdeParcela;
	private Date dataLancamento;

	public Lancamento() {
	}

	public Lancamento(int id, Contacontabil contacontabil, String descricao,
			float valor) {
		this.id = id;
		this.contacontabil = contacontabil;
		this.descricao = descricao;
		this.valor = valor;
	}

	public Lancamento(int id, Vale vale, Contacontabil contacontabil,
			String descricao, float valor, Boolean deleted, Lancamento lancamentoPai) {
		this.id = id;
		this.vale = vale;
		this.contacontabil = contacontabil;
		this.descricao = descricao;
		this.valor = valor;
		this.lancamentoPai = lancamentoPai;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vale")
	public Vale getVale() {
		return this.vale;
	}

	public void setVale(Vale vale) {
		this.vale = vale;
	}

	@ManyToOne
	@JoinColumn(name = "contacontabil", nullable = false)
	public Contacontabil getContacontabil() {
		if(contacontabil == null){
			contacontabil = new Contacontabil();
		}
		return this.contacontabil;
	}

	public void setContacontabil(Contacontabil contacontabil) {
		this.contacontabil = contacontabil;
	}

	@Column(name = "descricao", nullable = false, length = 80)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "valor", nullable = false, precision = 7, scale = 2)
	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Column(name="deleted", nullable=false)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lancamentopai")
	public Lancamento getLancamentoPai() {
		return lancamentoPai;
	}

	public void setLancamentoPai(Lancamento lancamentoPai) {
		this.lancamentoPai = lancamentoPai;
	}

	@Column(name="parcela", nullable = true)
	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	@Column(name="qtdeparcela", nullable = true)
	public Integer getQtdeParcela() {
		return qtdeParcela;
	}

	public void setQtdeParcela(Integer qtdeParcela) {
		this.qtdeParcela = qtdeParcela;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="datalancamento", nullable = false)
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
