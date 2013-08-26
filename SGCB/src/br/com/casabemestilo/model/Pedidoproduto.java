package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse.List;
import javax.validation.constraints.Null;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Pedidoproduto generated by hbm2java
 */
@Entity
@Table(name = "pedidoproduto", catalog = "blankerc_sgcb")
public class Pedidoproduto implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;	
	private Integer id;
	private Produto produto;
	private Pedido pedido;
	private Ocproduto ocproduto;
	private int quantidade;

	public Pedidoproduto() {
	}

	public Pedidoproduto(Produto produto, Pedido pedido, int quantidade) {
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
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
	@JoinColumn(name = "produto", nullable = false)
	public Produto getProduto() {
		if(this.produto == null){
			this.produto = new Produto();
		}
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne
	@JoinColumn(name = "pedido", nullable = false)
	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Column(name = "quantidade", nullable = false)
	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocproduto")
	public Ocproduto getOcproduto() {
		return ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}
	
	

}
