package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.util.Conexao;


public class OcDAO implements InterfaceDAO, Serializable {


	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private List<Oc> listaOc;
	
	private Oc oc;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public OcDAO(List<Oc> listaOc, Oc oc) {
		super();
		
		this.listaOc = listaOc;
		this.oc = oc;
	}

	public OcDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(oc);
		session.getTransaction().commit();
	}
	
	public Oc insertOc(Object obj) throws Exception, HibernateException,
		ConstraintViolationException{
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		oc.setId((Integer) session.merge(oc));
		session.getTransaction().commit();
		return oc;
	}
	

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(oc);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(oc);
		session.getTransaction().commit();
	}

	@Override
	public Oc buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		oc = (Oc) session.get(Oc.class, id);
		session.close();
		return oc;
	}

	@Override
	public List<Oc> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Oc> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();		
		listaOc = session.createQuery("from Oc o where o.deleted=0 order by o.id desc").setCacheable(true).list();
		session.close();
		return listaOc;
	}

	@Override
	public List<Oc> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Oc> getListaOc() {
		return listaOc;
	}

	public void setListaOc(List<Oc> listaOc) {
		this.listaOc = listaOc;
	}

	public Oc getOc() {
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}
	

}
