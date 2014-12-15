package br.com.casabemestilo.model;

// Generated 24/05/2013 18:36:37 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fornecedor implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String razaosocial;
	private Boolean ehrede;
	private String cnpj;
	private String ie;
	private String telefone;
	private String endereco;
	private String cidade;
	private Boolean deleted;
	private List<Produto> produtos = new ArrayList<Produto>();
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private String observacoes;
	private String email;
	private UF uf;

	public Fornecedor() {
	}

	public Fornecedor(String nome) {
		this.nome = nome;
	}

	public Fornecedor(Integer id, String nome, Boolean ehrede, String cnpj,
			String telefone, String endereco, String cidade, Boolean deleted,
			List<Produto> produtos, List<Pedido> pedidos) {
		super();
		this.id = id;
		this.nome = nome;
		this.ehrede = ehrede;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cidade = cidade;
		this.deleted = deleted;
		this.produtos = produtos;
		this.pedidos = pedidos;
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

	@Column(name = "nome", nullable = false, length = 60)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "razaosocial", nullable = true, length = 60)
	public String getRazaosocial() {
		return razaosocial;
	}

	public void setRazaosocial(String razaosocial) {
		this.razaosocial = razaosocial;
	}

	@Column(name = "ehrede")
	public Boolean getEhrede() {
		return this.ehrede;
	}

	public void setEhrede(Boolean ehrede) {
		this.ehrede = ehrede;
	}

	@Column(name = "cnpj", length = 18,nullable=true)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@Column(name = "ie", nullable = true)
	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	@Column(name="deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name="telefone",length=14)
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name="endereco")
	@Length(max=150,message="Endere�o em at� 150 caracteres")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name="cidade")
	@Length(max=80,message="Endere�o em at� 80 caracteres")
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@OneToMany(targetEntity = Produto.class, mappedBy = "fornecedor", fetch = FetchType.LAZY)
	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@OneToMany(targetEntity = Pedido.class, mappedBy = "fornecedor", fetch = FetchType.LAZY)
	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	@Column(name = "observacoes", nullable = true)
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
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
		return "Fornecedor [id=" + id + ", nome=" + nome + ", ehrede=" + ehrede
				+ ", cnpj=" + cnpj + ", deleted=" + deleted + "]";
	}
	
	

}
