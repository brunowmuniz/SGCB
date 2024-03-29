package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Montagem generated by hbm2java
 */
@Entity
@Table(name = "montagem")
public class Montagem implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Usuario usuario;
	private Ocproduto ocproduto;
	private Date datainicio;
	private Date datefim;
	private String observacoes;
	private Float valor;

	public Montagem() {
	}

	public Montagem(Usuario usuario, Ocproduto ocproduto, Date datainicio) {
		this.usuario = usuario;
		this.ocproduto = ocproduto;
		this.datainicio = datainicio;
	}

	public Montagem(Usuario usuario, Ocproduto ocproduto, Date datainicio,
			Date datefim, String observacoes, Float valor) {
		this.usuario = usuario;
		this.ocproduto = ocproduto;
		this.datainicio = datainicio;
		this.datefim = datefim;
		this.observacoes = observacoes;
		this.valor = valor;
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
	@JoinColumn(name = "montador", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ocproduto", nullable = false)
	public Ocproduto getOcproduto() {
		return this.ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datainicio", nullable = false, length = 10)
	public Date getDatainicio() {
		return this.datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datefim", length = 10)
	public Date getDatefim() {
		return this.datefim;
	}

	public void setDatefim(Date datefim) {
		this.datefim = datefim;
	}

	@Column(name="observacoes", length = 63565)
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Column(name = "valor", precision = 5)
	public Float getValor() {
		return this.valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

}
