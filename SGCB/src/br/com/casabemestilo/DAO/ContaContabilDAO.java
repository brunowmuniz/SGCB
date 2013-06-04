package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Contacontabil;

public class ContaContabilDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Contacontabil contacontabil;
	
	private List<Contacontabil> listaContacontabil;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ContaContabilDAO(Contacontabil contacontabil,
			List<Contacontabil> listaContacontabil) {
		super();
		this.contacontabil = contacontabil;
		this.listaContacontabil = listaContacontabil;
	}

	public ContaContabilDAO() {
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
	public Contacontabil buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacontabil> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacontabil> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacontabil> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Contacontabil getContacontabil() {
		return contacontabil;
	}

	public void setContacontabil(Contacontabil contacontabil) {
		this.contacontabil = contacontabil;
	}

	public List<Contacontabil> getListaContacontabil() {
		return listaContacontabil;
	}

	public void setListaContacontabil(List<Contacontabil> listaContacontabil) {
		this.listaContacontabil = listaContacontabil;
	}
	
	
}
