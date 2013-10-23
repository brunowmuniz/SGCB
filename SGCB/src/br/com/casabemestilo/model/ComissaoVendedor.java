package br.com.casabemestilo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.casabemestilo.control.Control;

@Entity
@Table(name = "comissaovendedor", catalog = "blankerc_sgcb")
public class ComissaoVendedor extends Control implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Usuario vendedor;
	
	private Double valor;
	
	private Oc oc;

	
	
	public ComissaoVendedor(String messagem, Integer id, Usuario vendedor,
			Double valor, Oc oc) {
		super(messagem);
		this.id = id;
		this.vendedor = vendedor;
		this.valor = valor;
		this.oc = oc;
	}

	public ComissaoVendedor() {
		super();
	}

	public ComissaoVendedor(String messagem) {
		super(messagem);
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="vendedor", nullable = false)
	public Usuario getVendedor() {
		if(vendedor == null){
			vendedor = new Usuario();
		}
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name="oc", nullable = false)
	public Oc getOc() {
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}
	
	
}
