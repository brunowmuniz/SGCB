package br.com.casabemestilo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "contacontabil", catalog = "blankerc_sgcb")
public class Contacontabil implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tipo;
	private String nome;
	private Boolean deleted;
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private Boolean contaBaixa;
	

	public Contacontabil() {
	}

	public Contacontabil(String tipo, String nome) {
		this.tipo = tipo;
		this.nome = nome;
	}

	public Contacontabil(String tipo, String nome, List<Lancamento> lancamentos) {
		this.tipo = tipo;
		this.nome = nome;
		this.lancamentos = lancamentos;
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

	@Column(name = "tipo", nullable = false, length = 2)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "nome", nullable = false, length = 60)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(targetEntity = Lancamento.class, mappedBy = "contacontabil", fetch = FetchType.LAZY)
	public List<Lancamento> getLancamentos() {
		return this.lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		for(Lancamento lancamento : lancamentos){
			lancamento.setContacontabil(this);
		}
		this.lancamentos = lancamentos;
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
	
	@Column(name="contabaixa", nullable = false)
	public Boolean getContaBaixa() {
		if(contaBaixa == null){
			contaBaixa = false;
		}
		return contaBaixa;
	}

	public void setContaBaixa(Boolean contaBaixa) {
		this.contaBaixa = contaBaixa;
	}

}
