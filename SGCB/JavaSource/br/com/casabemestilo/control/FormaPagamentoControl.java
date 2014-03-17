package br.com.casabemestilo.control;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import antlr.collections.impl.LList;
import br.com.casabemestilo.DAO.FilialDAO;
import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Formapagamento;

@ManagedBean
@ViewScoped
public class FormaPagamentoControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;
	
	private Formapagamento formapagamento;
	
	private List<Formapagamento> listaFormaPagamento;
	
	private FormaPagamentoDAO formaPagamentoDAO;
	
	private List listaFormaPagamentoCombo;

	
	/*
	 * CONSTRUTORES
	 * */	
	public FormaPagamentoControl(String messagem,
			Formapagamento formapagamento,
			List<Formapagamento> listaFormaPagamento,
			FormaPagamentoDAO formaPagamentoDAO) {
		super(messagem);
		this.formapagamento = formapagamento;
		this.listaFormaPagamento = listaFormaPagamento;
		this.formaPagamentoDAO = formaPagamentoDAO;
	}

	public FormaPagamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public FormaPagamentoControl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Formapagamento> listarAtivos() {
		try {
			listaFormaPagamento = new FormaPagamentoDAO().listaAtivos();
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
		return listaFormaPagamento;
	}

	@Override
	public List<Formapagamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formapagamento> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Formapagamento buscaObjetoId(Integer id) {
		formapagamento =  new Formapagamento();
		try {
			formapagamento = new FormaPagamentoDAO().buscaObjetoId(id);
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
		return formapagamento;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Formapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public List<Formapagamento> getListaFormaPagamento() {
		return listaFormaPagamento;
	}

	public void setListaFormaPagamento(List<Formapagamento> listaFormaPagamento) {
		this.listaFormaPagamento = listaFormaPagamento;
	}

	public FormaPagamentoDAO getFormaPagamentoDAO() {
		return formaPagamentoDAO;
	}

	public void setFormaPagamentoDAO(FormaPagamentoDAO formaPagamentoDAO) {
		this.formaPagamentoDAO = formaPagamentoDAO;
	}

	
	public List getListaFormaPagamentoCombo(String filter) {
		listaFormaPagamentoCombo = new ArrayList();
		listarAtivos();
		if(filter.equalsIgnoreCase("filter")){
			listaFormaPagamentoCombo.add(new SelectItem("","Todos"));
		}
		for(Formapagamento formapagamentos : listaFormaPagamento){
			SelectItem si = new SelectItem();
			si.setValue(formapagamentos.getId());
			si.setLabel(formapagamentos.getNome());
			listaFormaPagamentoCombo.add(si);
		}
		return listaFormaPagamentoCombo;
	}

	public void setListaFormaPagamentoCombo(List listaFormaPagamentoCombo) {
		this.listaFormaPagamentoCombo = listaFormaPagamentoCombo;
	}

}
