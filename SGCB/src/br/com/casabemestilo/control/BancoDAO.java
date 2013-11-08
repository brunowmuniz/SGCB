package br.com.casabemestilo.control;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Banco;
import br.com.casabemestilo.util.Conexao;

public class BancoDAO implements InterfaceDAO {
	
	private Session session;
	
	private List<Banco> bancos;
	
	private Banco banco;

	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		banco = (Banco) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(banco);
		session.getTransaction().commit();
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
	public Object buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Banco> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		bancos = session.createQuery("from Banco b where b.deleted = false")
						.setCacheable(true)
						.list();
		session.close();
		return bancos;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

}
