package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.control.FornecedoresControl;

public class FornecedoresDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private FornecedoresControl fornecedoresControl;
	
	private List<FornecedoresControl> listaFornecedores;
	
	/*
	 * CONSTRUTORES
	 * */
	public FornecedoresDAO(FornecedoresControl fornecedoresControl,
			List<FornecedoresControl> listaFornecedores) {
		super();
		this.fornecedoresControl = fornecedoresControl;
		this.listaFornecedores = listaFornecedores;
	}
	
	
	public FornecedoresDAO() {
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
	public FornecedoresControl buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FornecedoresControl> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}


	/*
	 * GETTERS & SETTERS
	 * */
	public FornecedoresControl getFornecedores() {
		return fornecedoresControl;
	}


	public void setFornecedores(FornecedoresControl fornecedoresControl) {
		this.fornecedoresControl = fornecedoresControl;
	}


	public List<FornecedoresControl> getListaFornecedores() {
		return listaFornecedores;
	}


	public void setListaFornecedores(List<FornecedoresControl> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}
	
}
