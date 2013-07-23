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

/**
 * Oc generated by hbm2java
 */
@Entity
@Table(name = "oc", catalog = "gcb")
public class Oc implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private Status status;
	private Cliente cliente;
	private float valorfrete;
	private float valormontagem;
	private Date prazoentrega;
	private String observacoes;
	private float valor;
	private Float valorfinanciado;
	private float valorfinal;
	private float valorliquido;
	private Date datalancamento;
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();
	private List<Ocproduto> ocprodutos = new ArrayList<Ocproduto>();
	private List<ComissaoUsuario> comissaoUsuario = new ArrayList<ComissaoUsuario>();

	public Oc() {
	}

	public Oc(Usuario usuario, Status status, Cliente cliente,
			float valorfrete, float valormontagem, float valor,
			float valorfinal, float valorliquido, Date datalancamento) {
		this.usuario = usuario;
		this.status = status;
		this.cliente = cliente;
		this.valorfrete = valorfrete;
		this.valormontagem = valormontagem;
		this.valor = valor;
		this.valorfinal = valorfinal;
		this.valorliquido = valorliquido;
		this.datalancamento = datalancamento;
	}

	public Oc(Usuario usuario, Status status, Cliente cliente,
			float valorfrete, float valormontagem, Date prazoentrega,
			String observacoes, float valor, Float valorfinanciado,
			float valorfinal, float valorliquido, Date datalancamento,
			List<Pagamento> pagamentos, List<Ocproduto> ocprodutos) {
		this.usuario = usuario;
		this.status = status;
		this.cliente = cliente;
		this.valorfrete = valorfrete;
		this.valormontagem = valormontagem;
		this.prazoentrega = prazoentrega;
		this.observacoes = observacoes;
		this.valor = valor;
		this.valorfinanciado = valorfinanciado;
		this.valorfinal = valorfinal;
		this.valorliquido = valorliquido;
		this.datalancamento = datalancamento;
		this.pagamentos = pagamentos;
		this.ocprodutos = ocprodutos;
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
	@JoinColumn(name = "vendedor", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "cliente", nullable = false)
	public Cliente getCliente() {
		if(cliente == null){
			cliente = new Cliente();
		}
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "valorfrete", nullable = false, precision = 6, scale = 2)
	public float getValorfrete() {
		return this.valorfrete;
	}

	public void setValorfrete(float valorfrete) {
		this.valorfrete = valorfrete;
	}

	@Column(name = "valormontagem", nullable = false, precision = 6, scale = 2)
	public float getValormontagem() {
		return this.valormontagem;
	}

	public void setValormontagem(float valormontagem) {
		this.valormontagem = valormontagem;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "prazoentrega", length = 10)
	public Date getPrazoentrega() {
		return this.prazoentrega;
	}

	public void setPrazoentrega(Date prazoentrega) {
		this.prazoentrega = prazoentrega;
	}

	@Column(name = "observacoes", length = 65535)
	public String getObservacoes() {
		return this.observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Column(name = "valor", nullable = false, precision = 8, scale = 2)
	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Column(name = "valorfinanciado", precision = 8, scale = 2)
	public Float getValorfinanciado() {
		return this.valorfinanciado;
	}

	public void setValorfinanciado(Float valorfinanciado) {
		this.valorfinanciado = valorfinanciado;
	}

	@Column(name = "valorfinal", nullable = false, precision = 8, scale = 2)
	public float getValorfinal() {
		this.valorfinal = getValor() + getValorfrete() + getValormontagem();
		return this.valorfinal;
	}

	public void setValorfinal(float valorfinal) {
		this.valorfinal = valorfinal;
		
	}

	@Column(name = "valorliquido", nullable = false, precision = 6)
	public float getValorliquido() {
		return this.valorliquido;
	}

	public void setValorliquido(float valorliquido) {
		this.valorliquido = valorliquido;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "datalancamento", nullable = false, length = 10)
	public Date getDatalancamento() {
		return this.datalancamento;
	}

	public void setDatalancamento(Date datalancamento) {
		this.datalancamento = datalancamento;
	}

	@OneToMany(targetEntity = Pagamento.class, mappedBy = "oc")
	public List<Pagamento> getPagamentos() {
		return this.pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@OneToMany(targetEntity = Ocproduto.class, mappedBy = "oc", cascade= CascadeType.ALL)
	public List<Ocproduto> getOcprodutos() {
		return this.ocprodutos;
	}

	public void setOcprodutos(List<Ocproduto> ocprodutos) {
		this.ocprodutos = ocprodutos;
	}

	@OneToMany(targetEntity=ComissaoUsuario.class, mappedBy= "oc")
	public List<ComissaoUsuario> getComissaoUsuario() {
		return comissaoUsuario;
	}

	public void setComissaoUsuario(List<ComissaoUsuario> comissaoUsuario) {
		this.comissaoUsuario = comissaoUsuario;
	}

}
