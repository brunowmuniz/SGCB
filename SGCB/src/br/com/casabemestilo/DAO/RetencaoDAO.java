package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.Retencao;
import br.com.casabemestilo.util.Conexao;

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
		this.retencao = (Retencao) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(this.retencao);
		session.getTransaction().commit();

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		retencao = (Retencao) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(retencao);
		session.getTransaction().commit();

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		retencao = (Retencao) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update Retencao r set r.deleted= :deleted where r.id= :id")
			   .setBoolean("deleted", true)
			   .setInteger("id", retencao.getId())
			   .executeUpdate();
		session.getTransaction().commit();

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
		session = Conexao.getInstance();
		session.beginTransaction();
		listaRetencao = session.createQuery("from Retencao r where r.deleted=0").list();
		session.close();
		return listaRetencao;
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
