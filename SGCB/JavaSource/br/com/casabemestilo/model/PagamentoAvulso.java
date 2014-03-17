package br.com.casabemestilo.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "pagamentoavulso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PagamentoAvulso implements  Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private Integer id;	
	private String descricao;
	private Boolean deleted;
	private List<Pagamento> pagamentos;
	private Date dataLancamento;
	private Boolean ehRenegociacao;
	private Cliente cliente;
	
	public PagamentoAvulso(Integer id, List<Pagamento> pagamentos, String descricao, Boolean deleted) {		
		this.id = id;
		this.descricao = descricao;
		this.pagamentos = pagamentos;
		this.deleted = deleted;
	}
	
	public PagamentoAvulso() {		
		
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

	@OneToMany(targetEntity = Pagamento.class, mappedBy = "pagamentoAvulso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@BatchSize(size = 5)
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@Column(name="deleted", nullable = false)
	public Boolean getDeleted() {
		if(deleted == null){
			deleted = false;
		}
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name="dataLancamento", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDataLancamento() {
		if(dataLancamento == null){
			dataLancamento = new Date();
		}
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	@Column(name="ehrenegociacao", nullable = false)
	public Boolean getEhRenegociacao() {
		if(ehRenegociacao == null){
			ehRenegociacao = false;
		}
		return ehRenegociacao;
	}

	public void setEhRenegociacao(Boolean ehRenegociacao) {
		this.ehRenegociacao = ehRenegociacao;
	}
	

	@Override
	public String toString() {
		String ret = "";
		for(Pagamento pagamento : pagamentos){
			ret += "PagamentoAvulso [pagamentos=" + pagamento.getCondicoesPagamento().getFormapagamento().getNome() + "]";
		}
		return ret;
	}

	
}
