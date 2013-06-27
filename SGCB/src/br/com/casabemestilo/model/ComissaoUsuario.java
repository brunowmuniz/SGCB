package br.com.casabemestilo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "comissaousuario", catalog = "gcb")
public class ComissaoUsuario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Oc oc;
	private Ocproduto ocProduto;
	private Float valor;
	private Boolean deleted;
	
	
	public ComissaoUsuario() {
		// TODO Auto-generated constructor stub
	}


	public ComissaoUsuario(Integer id, Oc oc, Ocproduto ocProduto, Float valor,
			Boolean deleted) {
		super();
		this.id = id;
		this.oc = oc;
		this.ocProduto = ocProduto;
		this.valor = valor;
		this.deleted = deleted;
	}

	@Id
	@GeneratedValue(strategy= IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "oc", nullable = true)
	public Oc getOc() {
		return oc;
	}


	public void setOc(Oc oc) {
		this.oc = oc;
	}


	@ManyToOne
	@JoinColumn(name = "ocProduto", nullable = true)
	public Ocproduto getOcProduto() {
		return ocProduto;
	}


	public void setOcProduto(Ocproduto ocProduto) {
		this.ocProduto = ocProduto;
	}


	@Column(name="valor",nullable=false)
	public Float getValor() {
		return valor;
	}


	public void setValor(Float valor) {
		this.valor = valor;
	}

	@Column(name="deleted",nullable=true)
	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
	
}
