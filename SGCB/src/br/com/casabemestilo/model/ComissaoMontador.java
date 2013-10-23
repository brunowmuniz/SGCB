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
@Table(name = "comissaomontador", catalog = "blankerc_sgcb")
public class ComissaoMontador extends Control implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Frete freteMontagem;
	
	private Usuario montador;
	
	private Double valor;
	
	
	

	public ComissaoMontador(String messagem, Integer id, Frete freteMontagem,
			Usuario montador, Double valor) {
		super(messagem);
		this.id = id;
		this.freteMontagem = freteMontagem;
		this.montador = montador;
		this.valor = valor;
	}

	public ComissaoMontador(Integer id, Double valor, Usuario montador){
		this.id = id;
		this.montador = montador;
		this.valor = valor;
	}
	
	public ComissaoMontador() {
		super();		
	}

	public ComissaoMontador(String messagem) {
		super(messagem);
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="fretemontagem")
	public Frete getFreteMontagem() {
		return freteMontagem;
	}

	public void setFreteMontagem(Frete freteMontagem) {
		this.freteMontagem = freteMontagem;
	}

	@ManyToOne
	@JoinColumn(name="montador", nullable = false)
	public Usuario getMontador() {
		return montador;
	}

	public void setMontador(Usuario montador) {
		this.montador = montador;
	}

	@Column(name = "valor", nullable = false)
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
