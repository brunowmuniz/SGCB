package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Montagem;

public class MontagemDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session sesion;
	
	private Montagem montagem;
	
	private List<Montagem> listaMontagem;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public MontagemDAO(Montagem montagem, List<Montagem> listaMontagem) {
		super();
		this.montagem = montagem;
		this.listaMontagem = listaMontagem;
	}

	
	public MontagemDAO() {
		super();
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
	public Montagem buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Montagem> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Montagem> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Montagem> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Montagem getMontagem() {
		return montagem;
	}


	public void setMontagem(Montagem montagem) {
		this.montagem = montagem;
	}


	public List<Montagem> getListaMontagem() {
		return listaMontagem;
	}


	public void setListaMontagem(List<Montagem> listaMontagem) {
		this.listaMontagem = listaMontagem;
	}
	

}
