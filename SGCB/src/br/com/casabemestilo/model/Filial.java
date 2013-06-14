package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;

/**
 * Filial generated by hbm2java
 */
@Entity
@Table(name = "filial", catalog = "gcb")
public class Filial implements java.io.Serializable {

	private Integer id;
	private String nome;
	private Boolean deleted;	
	private Set usuarioFiliais = new HashSet(0);

	public Filial() {
	}

	public Filial(String nome) {
		this.nome = nome;
	}

	public Filial(String nome, Boolean deleted) {
		this.nome = nome;
		this.deleted = deleted;
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

	@Column(name = "nome", nullable = false, length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "deleted")
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@OneToMany(targetEntity = UsuarioFilial.class, mappedBy = "filial")
	public Set getUsuarioFiliais() {
		return usuarioFiliais;
	}

	public void setUsuarioFiliais(Set usuarioFiliais) {
		this.usuarioFiliais = usuarioFiliais;
	}

}
