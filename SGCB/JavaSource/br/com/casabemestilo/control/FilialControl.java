package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.FilialDAO;
import br.com.casabemestilo.DAO.CondicoesPagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.CondicoesPagamento;

@ManagedBean
@ViewScoped
public class FilialControl extends Control implements InterfaceControl, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Filial filial;
	
	private List<Filial> listaFilial;
	
	private FilialDAO filialDAO;
	
	private List listaFilialCombo;

	
	/*
	 * CONSTRUTORES
	 * */
	public FilialControl(String messagem, Filial filial,
			List<Filial> listaFilial, FilialDAO filialDAO) {
		super(messagem);
		this.filial = filial;
		this.listaFilial = listaFilial;
		this.filialDAO = filialDAO;
	}

	public FilialControl(String messagem) {
		super(messagem);
	
	}

	public FilialControl() {
	
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {			
		try {
			filialDAO = new FilialDAO();
			filial.setDeleted(false);
			filialDAO.insert(filial);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Filial: " + filial.getNome() + " foi gravado!"));
			logger.info("Salvo" + filial.toString());
			filial = new Filial();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro Constraint: "+ super.mensagem,""));
			logger.error("Erro Constraint: "+ super.mensagem + "-"+ filial.getNome());			
			e.printStackTrace();
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro hibernate: " + super.mensagem + "-" + filial.getNome());			
			e.printStackTrace();
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro : " + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		}		
	}

	@Override
	public void deletar() {
		
		try {
			filialDAO = new FilialDAO();
			filial.setDeleted(true);
			filialDAO.delete(true);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Filial "+ filial.getNome() + "foi deletada"));
			logger.info("Deletada Filial " + filial.toString());
			filial = new Filial();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate" + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
			logger.error("Erro Exception" + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		}
		
	}

	@Override
	public void alterar() {
		
		try {
			filialDAO = new FilialDAO();
			filial.setDeleted(false);
			filialDAO.update(filial);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Filial:" + filial.getNome() + " foi alterada!"));
			logger.info("Alterado retenção: " + filial.toString());
        	filial = new Filial();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint:" + super.mensagem, ""));
			logger.error("Erro Constraint" + super.mensagem + "-" + filial.getNome());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate" + super.mensagem, ""));
			logger.error("Erro Hibernate" + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception" + super.mensagem, ""));
			logger.error("Erro Exception" + super.mensagem + "-" + filial.getNome());
			e.printStackTrace();
		}

	}
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("filial",filial);
		return "cadastrafilial";
	}
	public String sairAlteracao(){
		return "manutencaofilial?faces-redirect=true";
	}
	@Override
	public List<Filial> listarAtivos() {		
		try {
			listaFilial = new FilialDAO().listaAtivos();
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaFilial;
	}

	@Override
	public List<Filial> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Filial> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filial buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getListaFilialComboFilter() {
		listaFilialCombo = new ArrayList();
		listarAtivos();
		listaFilialCombo.add(new SelectItem("","Todos"));
		for (Filial filiais : listaFilial) {
             SelectItem si = new SelectItem();
             si.setValue(filiais.getId());
             si.setLabel(filiais.getNome());
             listaFilialCombo.add(si);
         }
		 return listaFilialCombo;
	}
	
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Filial getFilial() {
		if(filial == null){
			filial = new Filial();
		}
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	public FilialDAO getFilialDAO() {
		return filialDAO;
	}

	public void setFilialDAO(FilialDAO filialDAO) {
		this.filialDAO = filialDAO;
	}

	public List getListaFilialCombo() {
		listaFilialCombo = new ArrayList();
		listarAtivos();
		for (Filial filiais : listaFilial) {
             SelectItem si = new SelectItem();
             si.setValue(filiais.getId());
             si.setLabel(filiais.getNome());
             listaFilialCombo.add(si);
         }
		 return listaFilialCombo;
	}

	public void setListaFilialCombo(List listaFilialCombo) {
		this.listaFilialCombo = listaFilialCombo;
	}
	
	
	
}
