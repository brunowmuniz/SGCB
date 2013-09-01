package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.ContaContabilDAO;
import br.com.casabemestilo.DAO.LancamentoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Contacontabil;
import br.com.casabemestilo.model.Lancamento;
import br.com.casabemestilo.model.Oc;

@ManagedBean
@ViewScoped
public class LancamentoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
	
	private Lancamento lancamento;
	
	private LancamentoDAO lancamentoDAO;
	
	private Boolean ehParcelado = false;
	
	private Float totalLancamento;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private LazyDataModel<Lancamento> listaLancamentoGeral;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public LancamentoControl(String messagem, List<Lancamento> listaLancamento,
			Lancamento lancamento, LancamentoDAO lancamentoDAO) {
		super(messagem);
		this.listaLancamento = listaLancamento;
		this.lancamento = lancamento;
		this.lancamentoDAO = lancamentoDAO;
	}

	public LancamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public LancamentoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * M�TODOS
	 * */
	@Override
	public void gravar() {		
		try {
			lancamentoDAO = new LancamentoDAO();
			lancamento.setDeleted(false);
			lancamento.setContacontabil(new ContaContabilDAO().buscaObjetoId(lancamento.getContacontabil().getId()));
			if(getEhParcelado()){
				Integer idLancamentoPai = 0; 				
				for(int i = 1; i <= getLancamento().getQtdeParcela(); i++){
					lancamento.setParcela(i);
					if(i==1){
						idLancamentoPai = lancamentoDAO.insertLista(lancamento).getId();
						lancamento.setLancamentoPai(new Lancamento());
						lancamento.getLancamentoPai().setId(idLancamentoPai);
					}else{
						Calendar c = Calendar.getInstance();
						c.setTime(lancamento.getDataLancamento());
						c.add(Calendar.DAY_OF_MONTH, 30);
						lancamento.setDataLancamento(c.getTime());
						lancamentoDAO.insert(lancamento);
					}
				}				
			}else{
				lancamento.setQtdeParcela(1);
				lancamento.setParcela(1);
				lancamentoDAO.insert(lancamento);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amentos criados!"));
			lancamento = new Lancamento();
			ehParcelado = false;
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}

	@Override
	public void deletar() {
		try {
			lancamentoDAO = new LancamentoDAO();
			lancamento.setDeleted(true);
			lancamentoDAO.delete(lancamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amento Deletado!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}

	@Override
	public void alterar() {
		try {
			lancamentoDAO = new LancamentoDAO();
			lancamentoDAO.update(lancamento);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amento Alterado!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}
	
	public void deletarLote() {
		try {
			lancamentoDAO = new LancamentoDAO();
			//lancamento.setLancamentoPai(lancamentoDAO.buscaLancamentoPai(lancamento.getId()));
			lancamentoDAO.deletaLancamentoApartir(lancamento);		
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lan�amentos Deletados!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Gen�rico: " + super.mensagem, ""));
		}
	}

	@Override
	public List<Lancamento> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LazyDataModel<Lancamento> getListaLancamentoGeralAll(){
		if(listaLancamentoGeral == null){
			listaLancamentoGeral = new LazyDataModel<Lancamento>() {
				private List<Lancamento> listaLazyLancamentos;
				
				@Override
			    public Lancamento getRowData(String idOc) {
			    	Integer id = Integer.valueOf(idOc);
			    	
			        for(Lancamento lancamento : listaLazyLancamentos) {
			            if(lancamento.getId().equals(id))
			                return lancamento;
			        }
			        
			        return null;
			    }

			    @Override
			    public Object getRowKey(Lancamento lancamento) {
			        return lancamento.getId();
			    }

			    @Override
			    public List<Lancamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
			    	LancamentoDAO lancamentoDAO = new LancamentoDAO();
			    	
			    	listaLazyLancamentos = lancamentoDAO.listaLazyLancamento(first, pageSize, filters, getDataInicial(), getDataFinal());
			    	
			    	if (getRowCount() <= 0) { 
			            setRowCount(lancamentoDAO.totalLancamento(filters, getDataInicial(), getDataFinal()));  
			        }  
			    	
			        setPageSize(pageSize);  
			        return listaLazyLancamentos;
			    }
			};
		}
		return listaLancamentoGeral;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}

	public Lancamento getLancamento() {
		if(lancamento == null){
			lancamento = new Lancamento();
		}
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public LancamentoDAO getLancamentoDAO() {
		return lancamentoDAO;
	}

	public void setLancamentoDAO(LancamentoDAO lancamentoDAO) {
		this.lancamentoDAO = lancamentoDAO;
	}

	public Boolean getEhParcelado() {
		return ehParcelado;
	}

	public void setEhParcelado(Boolean ehParcelado) {
		this.ehParcelado = ehParcelado;
	}

	public Float getTotalLancamento() {
		if(ehParcelado){
			totalLancamento = lancamento.getValor() * lancamento.getQtdeParcela();
		}else{
			totalLancamento = lancamento.getValor();
			if(totalLancamento == null){
				totalLancamento = new Float(0);
			}
		}
		return totalLancamento;
	}

	public void setTotalLancamento(Float totalLancamento) {
		this.totalLancamento = totalLancamento;
	}

	public Date getDataInicial() {
		if(dataInicial == null){
			dataInicial = new Date();
		}
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		if(dataFinal == null){
			dataFinal = new Date();
		}
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LazyDataModel<Lancamento> getListaLancamentoGeral() {
		return listaLancamentoGeral;
	}

	public void setListaLancamentoGeral(
			LazyDataModel<Lancamento> listaLancamentoGeral) {
		this.listaLancamentoGeral = listaLancamentoGeral;
	}
	
}
