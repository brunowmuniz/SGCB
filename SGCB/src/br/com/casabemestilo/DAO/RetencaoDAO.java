package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Retencao;

public class RetencaoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Retencao retencao;
	
	private List<Retencao> listaRetencao;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public RetencaoDAO(Retencao retencao, List<Retencao> listaRetencao) {
		super();
		this.retencao = retencao;
		this.listaRetencao = listaRetencao;
	}

	public RetencaoDAO() {
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
	public Retencao buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retencao> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Retencao getRetencao() {
		return retencao;
	}

	public void setRetencao(Retencao retencao) {
		this.retencao = retencao;
	}

	public List<Retencao> getListaRetencao() {
		return listaRetencao;
	}

	public void setListaRetencao(List<Retencao> listaRetencao) {
		this.listaRetencao = listaRetencao;
	}
	
}
