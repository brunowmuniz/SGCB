package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", catalog = "lacodevidas02")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements java.io.Serializable {

	private Integer id;
	private Perfil perfil;
	private String nome;
	private Boolean deleted;
	private String login;
	private String senha;
	private Set comissaos = new HashSet(0);
	private List<Oc> ocs= new ArrayList<Oc>();
	private Set fretes = new HashSet(0);
	private Set vales = new HashSet(0);
	private Set montagems = new HashSet(0);
	private Set assistenciatecnicas = new HashSet(0);
	private List<UsuarioFilial> usuarioFiliais = new ArrayList<UsuarioFilial>();

	public Usuario() {
	}

	public Usuario(Perfil perfil, Filial filial, String nome, Boolean deleted) {
		this.perfil = perfil;
		this.nome = nome;
		this.deleted = deleted;
	}

	public Usuario(Perfil perfil, String nome, Boolean deleted,
			Set comissaos, List<Oc> ocs, Set fretes, Set vales, Set montagems,
			Set assistenciatecnicas, String login,String senha) {
		this.perfil = perfil;
		this.nome = nome;
		this.deleted = deleted;
		this.login = login;
		this.senha = senha;
		this.comissaos = comissaos;
		this.ocs = ocs;
		this.fretes = fretes;
		this.vales = vales;
		this.montagems = montagems;
		this.assistenciatecnicas = assistenciatecnicas;
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

	@ManyToOne
	@JoinColumn(name = "perfil", nullable = false)
	public Perfil getPerfil() {
		if(this.perfil == null){
			this.perfil = new Perfil();
		}
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Column(name = "nome", nullable = false, length = 150)
	@Length(min=10, max=150, message="Favor informar nome do usu�rio com 10 caracteres ao menos!")
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
	
	@Column(name = "login")
	@Length(min=5, max=12, message="Favor informar o login entre 5 e 12 caracteres")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "senha")
	@Length(min=3, message="Favor informar senha com no m�nimo 3 caracteres")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@OneToMany(targetEntity = Comissao.class, mappedBy = "usuario")
	public Set getComissaos() {
		return this.comissaos;
	}

	public void setComissaos(Set comissaos) {
		this.comissaos = comissaos;
	}

	@OneToMany(targetEntity = Oc.class, mappedBy = "usuario", fetch = FetchType.EAGER)
	public List<Oc> getOcs() {
		return this.ocs;
	}

	public void setOcs(List<Oc> ocs) {
		this.ocs = ocs;
	}

	@OneToMany(targetEntity = Frete.class, mappedBy = "usuario")
	public Set getFretes() {
		return this.fretes;
	}

	public void setFretes(Set fretes) {
		this.fretes = fretes;
	}

	@OneToMany(targetEntity = Vale.class, mappedBy = "usuario")
	public Set getVales() {
		return this.vales;
	}

	public void setVales(Set vales) {
		this.vales = vales;
	}

	@OneToMany(targetEntity = Montagem.class, mappedBy = "usuario")
	public Set getMontagems() {
		return this.montagems;
	}

	public void setMontagems(Set montagems) {
		this.montagems = montagems;
	}

	@OneToMany(targetEntity = Assistenciatecnica.class, mappedBy = "usuario")
	public Set getAssistenciatecnicas() {
		return this.assistenciatecnicas;
	}

	public void setAssistenciatecnicas(Set assistenciatecnicas) {
		this.assistenciatecnicas = assistenciatecnicas;
	}
		
	@OneToMany(targetEntity = UsuarioFilial.class, mappedBy = "usuario", cascade = CascadeType.ALL)
	public List<UsuarioFilial> getUsuarioFiliais() {
		return usuarioFiliais;
	}

	public void setUsuarioFiliais(List<UsuarioFilial> usuarioFiliais) {
		this.usuarioFiliais = usuarioFiliais;
	}

	@Override
	public String toString() {
		return "Usuario [perfil=" + perfil.getDescricao() + ", nome="
				+ nome + ", deleted=" + deleted + "]";
	}

	
	

}
