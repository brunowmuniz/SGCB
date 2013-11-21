package br.com.casabemestilo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pagina", catalog = "blankerc_sgcb")
public class Pagina implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String descricao;
	
	private String nomePagina;
	
	private Boolean deleted;
	
	private List<Permissao> permissoes;
	

	public Pagina(Integer id, String descricao, String nomePagina, List<Permissao> permissoes, Boolean deleted) {
		this.id = id;
		this.descricao = descricao;
		this.nomePagina = nomePagina;
		this.permissoes = permissoes;
		this.deleted = deleted;
	}	
	
	public Pagina(String nomePagina){
		this.nomePagina = nomePagina;
	}
	
	public Pagina() {
		super();
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

	@Column(name="nomepagina", nullable = false)
	public String getNomePagina() {
		return nomePagina;
	}

	public void setNomePagina(String nomePagina) {
		this.nomePagina = nomePagina;
	}

	@OneToMany(targetEntity = Permissao.class, mappedBy = "pagina", cascade = CascadeType.ALL)
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getDeleted() {
		if(deleted == null){
			deleted = false;
		}
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	 public boolean equals (Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        return this.nomePagina.equalsIgnoreCase(nomePagina);
    }
	
	
	
}
