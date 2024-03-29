package br.com.casabemestilo.model;



import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "comissao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comissao implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Usuario usuario;
	private Boolean deleted;
	private Boolean ehComissaoIndividual;
	private Float percentualComissaoIndividual;
	private Boolean ehComissaoConjunta;
	private String usuarioComissaoConjunta;
	private Float percentualComissaoConjunta;
	private Boolean ehComissaoMontadorIndividual;
	private Float percentualComissaoMontadorIndividual;
	private Boolean ehComissaoMontadorConjunta;
	private Float percentualComissaoMontadorConjunta;
	private String usuarioComissaoMontadorConjunta;
	private Boolean ehComissaoLoja;
	private Float percentualComissaoLoja;
	private Float percentualComissaoMontadorPlanejado;
	

	public Comissao() {
	}

	public Comissao(Integer id, Usuario usuario, Boolean deleted,
			Boolean ehComissaoIndividual, Float percentualComissaoIndividual,
			Boolean ehComissaoConjunta, String usuarioComissaoConjunta,
			Float percentualComissaoConjunta,
			Boolean ehComissaoMontadorIndividual,
			Float percentualComissaoMontadorIndividual,
			Boolean ehComissaoMontadorConjunta,
			Float percentualComissaoMontadorConjunta,
			String usuarioComissaoMontadorConjunta,
			Boolean ehComissaoLoja,
			Float percentualComissaoLoja) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.deleted = deleted;
		this.ehComissaoIndividual = ehComissaoIndividual;
		this.percentualComissaoIndividual = percentualComissaoIndividual;
		this.ehComissaoConjunta = ehComissaoConjunta;
		this.usuarioComissaoConjunta = usuarioComissaoConjunta;
		this.percentualComissaoConjunta = percentualComissaoConjunta;
		this.ehComissaoMontadorIndividual = ehComissaoMontadorIndividual;
		this.percentualComissaoMontadorIndividual = percentualComissaoMontadorIndividual;
		this.ehComissaoMontadorConjunta = ehComissaoMontadorConjunta;
		this.percentualComissaoMontadorConjunta = percentualComissaoMontadorConjunta;
		this.usuarioComissaoMontadorConjunta = usuarioComissaoMontadorConjunta;
		this.ehComissaoLoja = ehComissaoConjunta;
		this.percentualComissaoLoja = percentualComissaoLoja;
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
	@JoinColumn(name = "usuario", nullable = false)
	public Usuario getUsuario() {
		if(usuario == null){
			usuario = new Usuario();
		}
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Column(name="deleted", nullable=false)
	public Boolean getDeleted() {
		if(deleted == null){
			deleted = false;
		}
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name="ehcomissaoindividual", nullable=true)
	public Boolean getEhComissaoIndividual() {
		if(ehComissaoIndividual == null){
			ehComissaoIndividual = false;
		}
		return ehComissaoIndividual;
	}

	public void setEhComissaoIndividual(Boolean ehComissaoIndividual) {
		this.ehComissaoIndividual = ehComissaoIndividual;
	}

	@Column(name="percentualcomissaoindividual", nullable=true)
	public Float getPercentualComissaoIndividual() {
		if(!ehComissaoIndividual){
			percentualComissaoIndividual = null;
		}
		return percentualComissaoIndividual;
	}

	public void setPercentualComissaoIndividual(Float percentualComissaoIndividual) {
		this.percentualComissaoIndividual = percentualComissaoIndividual;
	}

	@Column(name="ehcomissaoconjunta", nullable=true)
	public Boolean getEhComissaoConjunta() {
		if(ehComissaoConjunta == null){
			ehComissaoConjunta = false;
		}
		return ehComissaoConjunta;
	}

	public void setEhComissaoConjunta(Boolean ehComissaoConjunta) {
		this.ehComissaoConjunta = ehComissaoConjunta;
	}

	@Column(name="usuariocomissaoconjunta", nullable=true)
	public String getUsuarioComissaoConjunta() {
		return usuarioComissaoConjunta;
	}

	public void setUsuarioComissaoConjunta(String usuarioComissaoConjunta) {
		this.usuarioComissaoConjunta = usuarioComissaoConjunta;
	}

	@Column(name="percentualcomissaoconjunta", nullable=true)
	public Float getPercentualComissaoConjunta() {
		if(!ehComissaoConjunta){
			percentualComissaoConjunta = null;
		}
		return percentualComissaoConjunta;
	}

	public void setPercentualComissaoConjunta(Float percentualComissaoConjunta) {
		this.percentualComissaoConjunta = percentualComissaoConjunta;
	}

	@Column(name="ehcomissaomontadorindividual", nullable=true)
	public Boolean getEhComissaoMontadorIndividual() {
		if(ehComissaoMontadorIndividual == null){
			ehComissaoMontadorIndividual = false;
		}
		return ehComissaoMontadorIndividual;
	}

	public void setEhComissaoMontadorIndividual(Boolean ehComissaoMontadorIndividual) {
		this.ehComissaoMontadorIndividual = ehComissaoMontadorIndividual;
	}

	@Column(name="percentualcomissaomontadorindividual", nullable=true)
	public Float getPercentualComissaoMontadorIndividual() {
		if(!ehComissaoMontadorIndividual){
			percentualComissaoMontadorIndividual = null;
		}
		return percentualComissaoMontadorIndividual;
	}

	public void setPercentualComissaoMontadorIndividual(
			Float percentualComissaoMontadorIndividual) {
		this.percentualComissaoMontadorIndividual = percentualComissaoMontadorIndividual;
	}

	@Column(name="ehcomissaomontadorconjunta", nullable=true)
	public Boolean getEhComissaoMontadorConjunta() {
		if(ehComissaoMontadorConjunta == null){
			ehComissaoMontadorConjunta = false;
		}
		return ehComissaoMontadorConjunta;
	}
	
	public void setEhComissaoMontadorConjunta(Boolean ehComissaoMontadorConjunta) {
		this.ehComissaoMontadorConjunta = ehComissaoMontadorConjunta;
	}
	
	@Column(name="percentualcomissaomontadorconjunta", nullable=true)
	public Float getPercentualComissaoMontadorConjunta() {
		if(!ehComissaoMontadorConjunta){
			percentualComissaoMontadorConjunta = null;
		}
		return percentualComissaoMontadorConjunta;
	}

	public void setPercentualComissaoMontadorConjunta(
			Float percentualComissaoMontadorConjunta) {
		this.percentualComissaoMontadorConjunta = percentualComissaoMontadorConjunta;
	}

	@Column(name="usuariocomissaomontadorconjunta", nullable=true)
	public String getUsuarioComissaoMontadorConjunta() {
		return usuarioComissaoMontadorConjunta;
	}

	public void setUsuarioComissaoMontadorConjunta(
			String usuarioComissaoMontadorConjunta) {
		this.usuarioComissaoMontadorConjunta = usuarioComissaoMontadorConjunta;
	}

	@Column(name="ehcomissaoloja", nullable=true)
	public Boolean getEhComissaoLoja() {
		if(ehComissaoLoja == null){
			ehComissaoLoja = false;
		}
		return ehComissaoLoja;
	}

	public void setEhComissaoLoja(Boolean ehComissaoLoja) {
		this.ehComissaoLoja = ehComissaoLoja;
	}

	@Column(name="percentualcomissaoloja", nullable=true)
	public Float getPercentualComissaoLoja() {
		if(!ehComissaoLoja){
			percentualComissaoLoja = null;
		}
		return percentualComissaoLoja;
	}

	public void setPercentualComissaoLoja(Float percentualComissaoLoja) {
		this.percentualComissaoLoja = percentualComissaoLoja;
	}

	@Column(name = "percentualComissaoMontadorPlanejado", nullable = true)
	public Float getPercentualComissaoMontadorPlanejado() {
		return percentualComissaoMontadorPlanejado;
	}

	public void setPercentualComissaoMontadorPlanejado(
			Float percentualComissaoMontadorPlanejado) {
		this.percentualComissaoMontadorPlanejado = percentualComissaoMontadorPlanejado;
	}
		

}
