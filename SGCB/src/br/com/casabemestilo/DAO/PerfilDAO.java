package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.util.Conexao;


public class PerfilDAO implements Serializable,InterfaceDAO {
	
	
	private static final long serialVersionUID = 1196487452811104598L;
	
	Session session;
	
	private Perfil perfil;
	
	private List<Perfil> listaPerfil;	
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PerfilDAO(Perfil perfil, List<Perfil> listaPerfil) {
		super();
		this.perfil = perfil;
		this.listaPerfil = listaPerfil;
	}
	
	public PerfilDAO() {
		super();
	}


	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException, ConstraintViolationException {
		perfil = (Perfil) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(perfil);
		session.getTransaction().commit();
		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException, ConstraintViolationException {
		perfil = (Perfil) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(perfil);
		session.getTransaction().commit();
		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException, ConstraintViolationException{
		perfil = (Perfil) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update Perfil p set p.deleted = :deleted where id=:id")
			   .setBoolean("deleted", true)
			   .setInteger("id", perfil.getId())
			   .executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public List<Perfil> listaTodos() throws Exception, HibernateException, ConstraintViolationException{
		session = Conexao.getInstance();
		session.beginTransaction();
		listaPerfil = session.createQuery("from Perfil").list();
		session.close();
		return listaPerfil;
	}

	@Override
	public List<Perfil> listaAtivos() throws HibernateException{
		session = Conexao.getInstance();
		session.beginTransaction();
		listaPerfil = session.createQuery( "from Perfil where deleted=0").list();
		session.close();
		return listaPerfil;
	}

	@Override
	public List<Perfil> listaSelecao(Object obj) throws Exception, HibernateException, ConstraintViolationException{		
		perfil = (Perfil) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		listaPerfil = session.createQuery("from Perfil where " +
															" deleted=:deletado " +
														" and" +
															" descricao like :desc")
							 .setBoolean("deletado", perfil.getDeleted())
							 .setString("desc", "%" + perfil.getDescricao() + "%")
							 .list();
		session.close();
		return listaPerfil;
	}

	@Override
	public Perfil buscaObjetoId(Integer id) throws Exception, HibernateException, ConstraintViolationException{		
		session = Conexao.getInstance();
		session.beginTransaction();
		perfil = (Perfil) session.createQuery("from Perfil where id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return perfil;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

}
