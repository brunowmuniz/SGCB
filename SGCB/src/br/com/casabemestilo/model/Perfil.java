package br.com.casabemestilo.model;



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


@Entity
@Table(name = "perfil", catalog = "blankerc_sgcb")
public class Perfil implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String descricao;
	private Boolean deleted;
	private Set usuarios = new HashSet(0);

	public Perfil() {
	}

	public Perfil(String descricao) {
		this.descricao = descricao;
	}

	public Perfil(String descricao, Boolean deleted, Set usuarios) {
		this.descricao = descricao;
		this.deleted = deleted;
		this.usuarios = usuarios;
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

	@Column(name = "descricao", nullable = false, length = 45)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "deleted")
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@OneToMany(targetEntity = Usuario.class, mappedBy = "perfil")
	public Set getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", descricao=" + descricao + ", deleted="
				+ deleted + ", usuarios=" + usuarios + "]";
	}	
	

}
