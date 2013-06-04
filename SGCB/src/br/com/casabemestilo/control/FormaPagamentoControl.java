package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.FormaPagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Formapagamento;

public class FormaPagamentoControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;
	
	private Formapagamento formapagamento;
	
	private List<Formapagamento> listaFormaPagamento;
	
	private FormaPagamentoDAO formaPagamentoDAO;

	
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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

}
