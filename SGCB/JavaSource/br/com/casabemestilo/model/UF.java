package br.com.casabemestilo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "uf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UF implements Serializable{
	
	
	private static final long serialVersionUID = -2204873278006491127L;

	private Integer id;
	
	private String sigla;
	
	private String descricao;
	
	private List<Fornecedor> fornecedores;
	
	private List<Cliente> clientes;

	public UF(Integer id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public UF() {
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name="sigla", nullable = false)
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@OneToMany(targetEntity = Fornecedor.class, mappedBy = "uf", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
		for(Fornecedor fornecedor : fornecedores){
			fornecedor.setUf(this);
		}
		this.fornecedores = fornecedores;
	}

	@OneToMany(targetEntity = Cliente.class, mappedBy = "uf", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
		for(Cliente cliente : clientes){
			cliente.setUf(this);
		}
		this.clientes = clientes;
	}
	
	
}
