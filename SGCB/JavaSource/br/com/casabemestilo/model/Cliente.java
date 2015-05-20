package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UF uf;
	private String nome;
	private String endereco;
	private String cidade;
	private String telefone;
	private String telefoneadicional;
	private String cpf;
	private String cnpj;
	private String tipoPessoa;
	private String rg;
	private Date datadenascimento;
	private Boolean deleted;
	private String ie;
	private String bairro;
	private String cep;
	private String email;
	private List<Oc> ocs = new ArrayList<Oc>();
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

	public Cliente() {
	}

	public Cliente(String nome, String endereco, String cidade,
			String telefone, String cpf, String rg) {
		this.nome = nome;
		this.endereco = endereco;
		this.cidade = cidade;
		this.telefone = telefone;
		this.cpf = cpf;
		this.rg = rg;
	}

	public Cliente(String nome, String endereco, String cidade,
			String telefone, String telefoneadicional, String cpf, String rg,
			Date datadenascimento, List<Oc> ocs) {
		this.nome = nome;
		this.endereco = endereco;
		this.cidade = cidade;
		this.telefone = telefone;
		this.telefoneadicional = telefoneadicional;
		this.cpf = cpf;
		this.rg = rg;
		this.datadenascimento = datadenascimento;
		this.ocs = ocs;
	}
	
	

	public Cliente(String nome, String telefone, String telefoneadicional,
			Date datadenascimento, String email) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.telefoneadicional = telefoneadicional;
		this.datadenascimento = datadenascimento;
		this.email = email;
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

	@Column(name = "nome", nullable = false, length = 150)
	@Length(max=150, message="Nome do cliente at� 150 caracteres")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "endereco", nullable = false, length = 150)
	@Length(max=150, message="Endere�o do cliente at� 150 caracteres")
	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "cidade", nullable = false, length = 80)
	@Length(max=80, message="Nome da cidade at� 80 caracteres")
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "telefone", nullable = false, length = 14)
	@Length(max=14, message="Telefone incorreto (51) 9999-9999")
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "telefoneadicional", length = 14)
	@Length(max=14, message="Telefone incorreto (51) 9999-9999")
	public String getTelefoneadicional() {
		return this.telefoneadicional;
	}

	public void setTelefoneadicional(String telefoneadicional) {
		this.telefoneadicional = telefoneadicional;
	}

	@Column(name = "cpf", nullable = false, length = 14)
	@Length(max=14, message="CPF incorreto!")
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "rg", nullable = true)
	@Length(max=20, message="RG incorreto!")
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datadenascimento")	
	public Date getDatadenascimento() {
		return this.datadenascimento;
	}

	public void setDatadenascimento(Date datadenascimento) {
		this.datadenascimento = datadenascimento;
	}

	@OneToMany(targetEntity = Oc.class, mappedBy = "cliente", fetch = FetchType.EAGER)
	public List<Oc> getOcs() {
		return this.ocs;
	}

	public void setOcs(List<Oc> ocs) {
		this.ocs = ocs;
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
	
	@OneToMany(targetEntity = Pagamento.class, mappedBy = "cliente", fetch = FetchType.LAZY)
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@Column(name="tipopessoa", nullable = false)
	public String getTipoPessoa() {
		if(tipoPessoa == null){
			tipoPessoa = "pf";
		}
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	@Column(name = "cnpj", nullable = true, length = 18)
	@Length(max=18, message="CNPJ incorreto!")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}	
	
	@Column(name="ie", nullable = true)
	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	@Column(name="bairro", nullable = true)
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name="cep", nullable = true)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name="email", nullable = true)
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne
	@JoinColumn(name = "uf", nullable = false)
	public UF getUf() {
		if(uf == null){
			uf = new UF();
		}
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", endereco="
				+ endereco + ", cidade=" + cidade + ", telefone=" + telefone
				+ ", telefoneadicional=" + telefoneadicional + ", cpf=" + cpf
				+ ", cnpj=" + cnpj + ", tipoPessoa=" + tipoPessoa + ", rg="
				+ rg + ", datadenascimento=" + datadenascimento + ", deleted="
				+ deleted + ", ie=" + ie + ", bairro=" + bairro + ", cep="
				+ cep + "]";
	}
	

}
