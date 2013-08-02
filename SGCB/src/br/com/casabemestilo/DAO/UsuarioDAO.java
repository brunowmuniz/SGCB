package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.Conexao;

public class UsuarioDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public UsuarioDAO(Usuario usuario, List<Usuario> listaUsuario) {
		super();
		this.usuario = usuario;
		this.listaUsuario = listaUsuario;
	}

	public UsuarioDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		
		
	}
	
	public Usuario insert(Usuario usuario){
		session = Conexao.getInstance();		
		session.beginTransaction();
		usuario.setId((Integer) session.save(usuario));
		session.getTransaction().commit();
		return usuario;
	} 

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		usuario = (Usuario) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(usuario);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		usuario = (Usuario) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(usuario);
		session.getTransaction().commit();
	}

	@Override
	public Usuario buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		usuario = (Usuario) session.createQuery("from Usuario where id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return usuario;
	}

	@Override
	public List<Usuario> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaUsuario = session.createQuery("from Usuario u where deleted=0").list();
		session.close();
		return listaUsuario;
	}

	@Override
	public List<Usuario> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Usuario> listaVendedorFilial() {
		session = Conexao.getInstance();
		listaUsuario = new ArrayList<Usuario>();		
		listaUsuario = session.createQuery("from Usuario u where u.perfil.id = 2").list();
		session.close();
		return listaUsuario;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	

	
}
