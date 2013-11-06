package br.com.casabemestilo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuariofilial", catalog = "blankerc_sgcb")
public class UsuarioFilial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Usuario usuario;
	private Filial filial;
	private Boolean deleted;
		
	public UsuarioFilial(Integer id, Usuario usuario, Filial filial,
			Boolean deleted) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.filial = filial;
		this.deleted = deleted;
	}

	public UsuarioFilial(){		
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
		if(this.usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JoinColumn(name = "filial", nullable = false)
	public Filial getFilial() {
		if(this.filial == null){
			filial = new Filial();
		}
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Column(name = "deleted")
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
	public String toString() {
		return "UsuarioFilial [usuario=" + usuario.getNome() + "]";
	}
	
	

}
