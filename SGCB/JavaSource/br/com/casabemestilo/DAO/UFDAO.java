package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.UF;
import br.com.casabemestilo.util.Conexao;

public class UFDAO implements Serializable,InterfaceDAO{

	
	Session session;
	
	private UF uf;
	
	private List<UF> ufs = new ArrayList<UF>();	
	
	
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
	public Object buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UF> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		ufs = session.createQuery("from UF order by sigla").list();
		session.close();
		return ufs;
	}

	@Override
	public <T> List<T> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

}
