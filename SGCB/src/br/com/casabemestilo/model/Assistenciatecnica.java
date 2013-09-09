package br.com.casabemestilo.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assistenciatecnica", catalog = "blankerc_sgcb")
public class Assistenciatecnica implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private int id;
	private String montador;
	private List<Ocproduto> ocprodutos;
	private Date datainicio;
	private Date datafim;
	private String observacoes;

	public Assistenciatecnica() {
	}

	public Assistenciatecnica(int id, String montador, List<Ocproduto> ocprodutos,
			Date datainicio, Date datafim) {
		this.id = id;
		this.montador = montador;
		this.ocprodutos = ocprodutos;
		this.datainicio = datainicio;
		this.datafim = datafim;
	}

	public Assistenciatecnica(int id, String montador, List<Ocproduto> ocprodutos,
			Date datainicio, Date datafim, String observacoes) {
		this.id = id;
		this.montador = montador;
		this.ocprodutos = ocprodutos;
		this.datainicio = datainicio;
		this.datafim = datafim;
		this.observacoes = observacoes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "montador", nullable = false)
	public String getMontador() {
		if(montador == null){
			montador = "";
		}
		return this.montador;
	}

	public void setMontador(String montador) {
		this.montador = montador;
	}

	@OneToMany(targetEntity = Ocproduto.class, mappedBy = "assistenciatecnica", fetch = FetchType.LAZY)
	public List<Ocproduto> getOcprodutos() {
		return this.ocprodutos;
	}

	public void setOcprodutos(List<Ocproduto> ocprodutos) {
		this.ocprodutos = ocprodutos;
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
	@Column(name = "datafim", nullable = false, length = 10)
	public Date getDatafim() {
		return this.datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
	}

	@Column(name = "observacoes", length = 65535)
	public String getObservacoes() {
		return this.observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	
}
