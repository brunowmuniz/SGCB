package br.com.casabemestilo.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "condicoespagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CondicoesPagamento implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Integer id;
	private Formapagamento formapagamento;
	private String nome;
	private float percentual;
	private Boolean deleted;
	private Integer parcelas;
	private Boolean avista;
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

	public CondicoesPagamento() {
	}

	public CondicoesPagamento(Formapagamento formapagamento, String nome, float percentual, Boolean deleted) {
		this.formapagamento = formapagamento;
		this.nome = nome;
		this.percentual = percentual;
		this.deleted = deleted;
		
	}
	
	public CondicoesPagamento(Integer id, Formapagamento formapagamento, String nome,
			float percentual, Boolean deleted, Integer parcelas, Boolean avista, List<Pagamento> pagamentos) {
		super();
		this.id = id;
		this.formapagamento = formapagamento;
		this.nome = nome;
		this.percentual = percentual;
		this.deleted = deleted;
		this.parcelas = parcelas;
		this.avista	= avista;
		this.pagamentos = pagamentos;
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
	@JoinColumn(name = "formapagamento", nullable = false)
	public Formapagamento getFormapagamento() {
		if(formapagamento == null){
			formapagamento = new Formapagamento();
		}
		return this.formapagamento;
	}

	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	@Column(name = "nome", nullable = false, length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "percentual", nullable = false, precision = 2)
	public float getPercentual() {
		return this.percentual;
	}

	public void setPercentual(float percentual) {
		this.percentual = percentual;
	}
	
	@Column(name = "deleted", nullable = false)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "parcelas", nullable = false)
	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
	@Column(name = "avista", nullable = false)
	public Boolean getAvista() {		
		return avista;
	}

	public void setAvista(Boolean avista) {
		this.avista = avista;
	}
	
	@OneToMany(targetEntity=Pagamento.class, mappedBy= "condicoesPagamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@Override
	public String toString() {
		return "CondicoesPagamento [id=" + id + ", formapagamento=" + formapagamento.getNome()
				+ ", nome=" + nome + ", percentual=" + percentual
				+ ", deleted=" + deleted + "]";
	}

}
