package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.model.UsuarioFilial;
import br.com.casabemestilo.util.Conexao;

public class UsuarioFilialDAO implements InterfaceDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;

	Session session;
	
	private UsuarioFilial usuarioFilial;
	
	private List<UsuarioFilial> listaUsuarioFilial;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public UsuarioFilialDAO(UsuarioFilial usuarioFilial,
			List<UsuarioFilial> listaUsuarioFilial) {
		super();
		this.usuarioFilial = usuarioFilial;
		this.listaUsuarioFilial = listaUsuarioFilial;
	}

	public UsuarioFilialDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		usuarioFilial = (UsuarioFilial) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(usuarioFilial);
		session.getTransaction().commit();
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		usuarioFilial = (UsuarioFilial) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.update(usuarioFilial);
		session.getTransaction().commit();

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		usuarioFilial = (UsuarioFilial) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update UsuarioFilial uf set uf.deleted = :deleted where uf.id=:id")
			   .setBoolean("deleted", true)
			   .setInteger("id", usuarioFilial.getId())
			   .executeUpdate();
		session.getTransaction().commit();

	}

	@Override
	public UsuarioFilial buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioFilial> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioFilial> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioFilial> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<UsuarioFilial> listaFiliaisDoUsuario(Usuario usuario){
		session = Conexao.getInstance();
		session.beginTransaction();
		listaUsuarioFilial = session.createQuery("from UsuarioFilial uf where uf.usuario= :id and uf.deleted <> 1")
							 .setInteger("id", usuario.getId())
							 .list();
		session.close();
		return listaUsuarioFilial;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public UsuarioFilial getUsuarioFilial() {
		return usuarioFilial;
	}

	public void setUsuarioFilial(UsuarioFilial usuarioFilial) {
		this.usuarioFilial = usuarioFilial;
	}

	public List<UsuarioFilial> getListaUsuarioFilial() {
		return listaUsuarioFilial;
	}

	public void setListaUsuarioFilial(List<UsuarioFilial> listaUsuarioFilial) {
		this.listaUsuarioFilial = listaUsuarioFilial;
	}
	

}
