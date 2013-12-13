package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.math.MathContext;
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
import javax.persistence.Transient;
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
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Perfil perfil;
	private String nome;
	private Boolean deleted;
	private String login;
	private String senha;
	private List<Comissao> comissaos = new ArrayList<Comissao>();
	private List<Oc> ocs= new ArrayList<Oc>();	
	private List<UsuarioFilial> usuarioFiliais = new ArrayList<UsuarioFilial>();
	private List<ComissaoVendedor> comissaoVendedores = new ArrayList<ComissaoVendedor>();
	private List<ComissaoMontador> comissaoMontadores = new ArrayList<ComissaoMontador>();
	private BigDecimal totalComissaoLoja;
	private BigDecimal totalComissaoVendedor;
	private BigDecimal totalComissaoMontador;
	private BigDecimal totalValeFuncionario;
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private List<Permissao> permissoes = new ArrayList<Permissao>();

	public Usuario() {
	}

	public Usuario(Perfil perfil, Filial filial, String nome, Boolean deleted) {
		this.perfil = perfil;
		this.nome = nome;
		this.deleted = deleted;
	}

	public Usuario(Perfil perfil, String nome, Boolean deleted,
			List<Comissao> comissaos, List<Oc> ocs, String login, String senha) {
		this.perfil = perfil;
		this.nome = nome;
		this.deleted = deleted;
		this.login = login;
		this.senha = senha;
		this.comissaos = comissaos;
		this.ocs = ocs;
	}
	
	public Usuario(Integer id, String nome, Perfil perfil, Double comissaoLoja, Double comissaoVenda){
		this.id = id;
		this.nome = nome;
		this.perfil = perfil;
		this.totalComissaoLoja = new BigDecimal(comissaoLoja, MathContext.DECIMAL64);
		this.totalComissaoVendedor = new BigDecimal(comissaoVenda, MathContext.DECIMAL64);		
	}	
	
	public Usuario(Perfil perfil, Integer id, String nome,  Double comissaoMontador){
		this.id = id;
		this.nome = nome;
		this.perfil = perfil;
		this.totalComissaoMontador = new BigDecimal(comissaoMontador, MathContext.DECIMAL64);				
	}
	
	public Usuario(Integer id, String nome, Perfil perfil, Double comissaoLoja){
		this.id = id;
		this.nome = nome;
		this.perfil = perfil;
		this.totalComissaoLoja = new BigDecimal(comissaoLoja, MathContext.DECIMAL64);		
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
	public List<Comissao> getComissaos() {
		return this.comissaos;
	}

	public void setComissaos(List<Comissao> comissaos) {
		this.comissaos = comissaos;
	}

	@OneToMany(targetEntity = Oc.class, mappedBy = "usuario", fetch = FetchType.EAGER)
	public List<Oc> getOcs() {
		return this.ocs;
	}

	public void setOcs(List<Oc> ocs) {
		this.ocs = ocs;
	}
		
	@OneToMany(targetEntity = UsuarioFilial.class, mappedBy = "usuario", cascade = CascadeType.ALL)
	public List<UsuarioFilial> getUsuarioFiliais() {
		return usuarioFiliais;
	}

	public void setUsuarioFiliais(List<UsuarioFilial> usuarioFiliais) {
		this.usuarioFiliais = usuarioFiliais;
	}
	
	@OneToMany(targetEntity = ComissaoVendedor.class, mappedBy = "vendedor", fetch = FetchType.LAZY)
	public List<ComissaoVendedor> getComissaoVendedores() {
		return comissaoVendedores;
	}

	public void setComissaoVendedores(List<ComissaoVendedor> comissaoVendedores) {
		this.comissaoVendedores = comissaoVendedores;
	}

	@OneToMany(targetEntity = ComissaoMontador.class, mappedBy = "montador", fetch = FetchType.LAZY)
	public List<ComissaoMontador> getComissaoMontadores() {
		return comissaoMontadores;
	}

	public void setComissaoMontadores(List<ComissaoMontador> comissaoMontadores) {
		this.comissaoMontadores = comissaoMontadores;
	}

	@Transient
	public BigDecimal getTotalComissaoLoja() {
		return totalComissaoLoja;
	}

	public void setTotalComissaoLoja(BigDecimal totalComissaoLoja) {
		this.totalComissaoLoja = totalComissaoLoja;
	}

	@Transient
	public BigDecimal getTotalComissaoVendedor() {
		return totalComissaoVendedor;
	}

	public void setTotalComissaoVendedor(BigDecimal totalComissaoVendedor) {
		this.totalComissaoVendedor = totalComissaoVendedor;
	}

	@Transient
	public BigDecimal getTotalComissaoMontador() {
		return totalComissaoMontador;
	}

	public void setTotalComissaoMontador(BigDecimal totalComissaoMontador) {
		this.totalComissaoMontador = totalComissaoMontador;
	}
	
	@Transient	
	public BigDecimal getTotalValeFuncionario() {
		if(totalValeFuncionario == null){
			totalValeFuncionario = new BigDecimal("0");
		}
		return totalValeFuncionario;
	}

	public void setTotalValeFuncionario(BigDecimal totalValeFuncionario) {
		this.totalValeFuncionario = totalValeFuncionario;
	}

	@OneToMany(targetEntity = Lancamento.class, mappedBy = "usuario")
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	@OneToMany(targetEntity = Permissao.class, mappedBy = "usuario",fetch = FetchType.LAZY)
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + "]";
	}

	
}
