package br.com.casabemestilo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="permissao")
public class Permissao  implements Serializable{

	
	private Integer id;
	
	private Usuario usuario;
	
	private Pagina pagina;
	
	private Boolean deleted;


	public Permissao(Integer id, Usuario usuario, Pagina pagina, Boolean deleted) {
		this.id = id;
		this.usuario = usuario;
		this.pagina = pagina;
		this.deleted = deleted;
	}
	
	

	public Permissao() {
		
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

	@ManyToOne
	@JoinColumn(name = "usuario", nullable = false)
	public Usuario getUsuario() {
		if(usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name = "pagina", nullable = false)
	public Pagina getPagina() {
		if(pagina == null){
			pagina = new Pagina();
		}
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
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
}
