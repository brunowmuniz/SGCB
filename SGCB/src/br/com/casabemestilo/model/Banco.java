package br.com.casabemestilo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "banco", catalog = "blankerc_sgcb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banco implements Serializable{

	
	private static final long serialVersionUID = 5772724871731672833L;

	private Integer id;

	private String numero;
	
	private String descricao;
	
	private Boolean deleted;
	
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();


	
	/*
	 * CONSTRUTORES
	 * */
	public Banco(Integer id, String descricao, Boolean deleted, String numero) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.deleted = deleted;
		this.numero = numero;
	}


	public Banco() {
		super();
	}


	
	/*
	 * GETTERS & SETTERS
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique = true)	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
		
	@Column(name="numero", nullable = false)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


	@Column(name="descricao", nullable = false)
	public String getDescricao() {
		if(descricao == null){
			descricao = "";
		}		
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name="deleted", nullable = false)
	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@OneToMany(targetEntity = Pagamento.class, mappedBy = "banco", fetch = FetchType.LAZY)
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}
	
	

	
}
