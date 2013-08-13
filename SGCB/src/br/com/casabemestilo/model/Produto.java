package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

/**
 * Produto generated by hbm2java
 */
@Entity
@Table(name = "produto", catalog = "lacodevidas02")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produto implements java.io.Serializable {

	private Integer id;
	private Fornecedor fornecedor;
	private String descricao;
	private float valorsugerido;
	private Integer showroom;
	private Integer estoque;
	private Integer encomenda;
	private String codigo;
	private Boolean deleted;
	private List<Ocproduto> ocprodutos = new ArrayList<Ocproduto>();
	private List<Pedidoproduto> pedidoprodutos = new ArrayList<Pedidoproduto>();

	public Produto() {
	}

	public Produto(Fornecedor fornecedor, String descricao,
			float valorsugerido, Integer showroom, Integer estoque, Integer encomenda) {
		this.fornecedor = fornecedor;
		this.descricao = descricao;
		this.valorsugerido = valorsugerido;
		this.showroom = showroom;
		this.estoque = estoque;
		this.encomenda = encomenda;
	}

	public Produto(Fornecedor fornecedor, String descricao,
			float valorsugerido, int showroom, int estoque, int encomenda,
			List<Ocproduto> ocprodutos, List<Pedidoproduto> pedidoprodutos) {
		this.fornecedor = fornecedor;
		this.descricao = descricao;
		this.valorsugerido = valorsugerido;
		this.showroom = showroom;
		this.estoque = estoque;
		this.encomenda = encomenda;
		this.ocprodutos = ocprodutos;
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fornecedor", nullable = false)
	public Fornecedor getFornecedor() {
		if(this.fornecedor == null){
			this.fornecedor = new Fornecedor();
		}
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Column(name = "descricao", nullable = false, length = 150)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "valorsugerido", nullable = false, scale = 6, precision=2)
	public float getValorsugerido() {
		return this.valorsugerido;
	}

	public void setValorsugerido(float valorsugerido) {
		this.valorsugerido = valorsugerido;
	}

	@Column(name = "showroom", nullable = false)
	@Max(value=999)
	@Min(value=0)
	public Integer getShowroom() {
		if(this.showroom == null){
			showroom = 0;
		}			
		return this.showroom;
	}

	public void setShowroom(Integer showroom) {
		this.showroom = showroom;
	}

	@Column(name = "estoque", nullable = false)
	@Max(value=999)
	public Integer getEstoque() {
		if(this.estoque == null){
			estoque = 0;
		}
		return this.estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	@Column(name = "encomenda", nullable = false)
	@Max(value=999)
	@Min(value=0)
	public Integer getEncomenda() {
		if(this.encomenda == null){
			this.encomenda = 0;
		}
		return this.encomenda;
	}

	public void setEncomenda(Integer encomenda) {
		this.encomenda = encomenda;
	}
	
	@Column(name="codigo", nullable = true)
	@Length(max=45)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="deleted", nullable = false)
	public Boolean getDeleted() {
		if(deleted == null){
			deleted = false;
		}
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@OneToMany(targetEntity = Ocproduto.class, mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	public List<Ocproduto> getOcprodutos() {
		return this.ocprodutos;
	}

	public void setOcprodutos(List<Ocproduto> ocprodutos) {		
		this.ocprodutos = ocprodutos;
		for(Ocproduto ocproduto : ocprodutos){
			if(ocproduto.getProduto() == null){
				ocproduto.setProduto(this);
			}			
		}
		this.ocprodutos = ocprodutos;
	}

	@OneToMany(targetEntity = Pedidoproduto.class, mappedBy = "produto")
	public List<Pedidoproduto> getPedidoprodutos() {
		return this.pedidoprodutos;
	}

	public void setPedidoprodutos(List<Pedidoproduto> pedidoprodutos) {
		this.pedidoprodutos = pedidoprodutos;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", descricao=" + descricao
				+ ", valorsugerido=" + valorsugerido + ", showroom=" + showroom
				+ ", estoque=" + estoque + ", encomenda=" + encomenda
				+ ", codigo=" + codigo + ", deleted=" + deleted + "]";
	}

}
