package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Formapagamento;
import br.com.casabemestilo.util.Conexao;

public class FormaPagamentoDAO implements InterfaceDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Formapagamento formaPagamento;
	
	private List<Formapagamento> listaFormaPagamento;
	

	/*
	 * CONSTRUTORES
	 * */
	public FormaPagamentoDAO(Formapagamento formaPagamento,
			List<Formapagamento> listaFormaPagamento) {
		super();
		this.formaPagamento = formaPagamento;
		this.listaFormaPagamento = listaFormaPagamento;
	}

	public FormaPagamentoDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Formapagamento buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formapagamento> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formapagamento> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaFormaPagamento = session.createQuery("from Formapagamento where deleted=0").list();
		session.close();
		return listaFormaPagamento;
	}

	@Override
	public List<Formapagamento> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<Formapagamento> getListaFormaPagamento() {
		return listaFormaPagamento;
	}

	public void setListaFormaPagamento(List<Formapagamento> listaFormaPagamento) {
		this.listaFormaPagamento = listaFormaPagamento;
	}
}
