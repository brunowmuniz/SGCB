package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Status generated by hbm2java
 */
@Entity
@Table(name = "status", catalog = "blankerc_sgcb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Status implements java.io.Serializable {

	private Integer id;
	private Boolean deleted;
	private String descricao;
	private List<Oc> ocs = new ArrayList<Oc>();
	private List<Ocproduto> ocprodutos = new ArrayList<Ocproduto>();

	public Status() {
	}

	public Status(String descricao) {
		this.descricao = descricao;
	}

	public Status(Boolean deleted, String descricao, List<Oc> ocs, List<Ocproduto> ocprodutos) {
		this.deleted = deleted;
		this.descricao = descricao;
		this.ocs = ocs;
		this.ocprodutos = ocprodutos;
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

	@Column(name = "deleted")
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "descricao", nullable = false, length = 45)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(targetEntity = Oc.class, mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Oc> getOcs() {
		return this.ocs;
	}

	public void setOcs(List<Oc> ocs) {
		this.ocs = ocs;
	}

	@OneToMany(targetEntity = Ocproduto.class, mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Ocproduto> getOcprodutos() {
		return this.ocprodutos;
	}

	public void setOcprodutos(List<Ocproduto> ocprodutos) {
		this.ocprodutos = ocprodutos;
	}

	
}
