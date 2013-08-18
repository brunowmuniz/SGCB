package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Pedido generated by hbm2java
 */
@Entity
@Table(name = "pedido", catalog = "blankerc_sgcb")
public class Pedido implements java.io.Serializable {

	private Integer id;
	private Fornecedor fornecedor;
	private Date datasolicitacao;
	private Date datachegada;
	private String observacao;
	private List<Pedidoproduto> pedidoprodutos = new ArrayList<Pedidoproduto>();

	public Pedido() {
	}

	public Pedido(Fornecedor fornecedor, Date datasolicitacao) {
		this.fornecedor = fornecedor;
		this.datasolicitacao = datasolicitacao;
	}

	public Pedido(Fornecedor fornecedor, Date datasolicitacao,
			Date datachegada, String observacao, List<Pedidoproduto> pedidoprodutos) {
		this.fornecedor = fornecedor;
		this.datasolicitacao = datasolicitacao;
		this.datachegada = datachegada;
		this.observacao = observacao;
		this.pedidoprodutos = pedidoprodutos;
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

	@ManyToOne
	@JoinColumn(name = "fornecedor", nullable = false)
	public Fornecedor getFornecedor() {
		if(fornecedor == null){
			fornecedor = new Fornecedor();
		}
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datasolicitacao", nullable = false, length = 10)
	public Date getDatasolicitacao() {		
		return this.datasolicitacao;
	}

	public void setDatasolicitacao(Date datasolicitacao) {
		this.datasolicitacao = datasolicitacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datachegada", length = 10, nullable = true)
	public Date getDatachegada() {
		return this.datachegada;
	}

	public void setDatachegada(Date datachegada) {
		this.datachegada = datachegada;
	}

	@Column(name = "observacao", length = 65535)
	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@OneToMany(targetEntity = Pedidoproduto.class, mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Pedidoproduto> getPedidoprodutos() {
		return this.pedidoprodutos;
	}

	public void setPedidoprodutos(List<Pedidoproduto> pedidoprodutos) {		
		this.pedidoprodutos = pedidoprodutos;
		for(Pedidoproduto pedidoproduto : pedidoprodutos){
			pedidoproduto.setPedido(this);
		}
		this.pedidoprodutos = pedidoprodutos;
	}

}
