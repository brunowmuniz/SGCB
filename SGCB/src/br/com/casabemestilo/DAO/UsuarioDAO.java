package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.model.UsuarioFilial;
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
		usuario = (Usuario) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		listaUsuario = session.createQuery("from Usuario u where " +
															" u.deleted=:deletado " +
														" and" +
															" u.nome like :desc")
							 .setBoolean("deletado", usuario.getDeleted())
							 .setString("desc", "%" + usuario.getNome() + "%")
							 .list();
		session.close();
		return listaUsuario;
	}
	
	public List<UsuarioFilial> listaVendedorFilial(Filial filial) {
		session = Conexao.getInstance();
		List<UsuarioFilial> listaUsuarioFilial = new ArrayList<UsuarioFilial>();
		listaUsuarioFilial = session.createQuery("from UsuarioFilial uf " +
											"where " +
												"uf.usuario.perfil.id = 2" +
											" and" +
												" uf.deleted = false" +
											" and" +
												" uf.filial.id= :filial")
								.setInteger("filial", filial.getId())
								.list();
		session.close();
		return listaUsuarioFilial;
	}
	
	public Usuario buscaUsuarioLogin(Usuario usuario) {
		this.usuario = null;
		session = Conexao.getInstance();
		this.usuario = (Usuario) session.createQuery("From Usuario u where u.login = :login and u.senha = :senha and u.deleted = false")
						 				.setString("login", usuario.getLogin())
						 				.setString("senha", usuario.getSenha())
						 				.uniqueResult();
		session.close();
		return this.usuario;
	}
	
	public boolean verificaLoginExistente(String login) {
		session = Conexao.getInstance();
		this.usuario = new Usuario();
		this.usuario = (Usuario) session.createQuery("From Usuario u where u.login= :login")
										.setString("login", login)
										.uniqueResult();
		session.close();
		return this.usuario.getId() != null;
	}
	
	public List<Usuario> listaMontador() {
		session = Conexao.getInstance();
		listaUsuario = new ArrayList<Usuario>();
		listaUsuario = session.createQuery("from Usuario u where u.perfil.id= 5 and deleted = 0").list();
		session.close();
		return listaUsuario;
	}
	
	public List<Usuario> listaVendedor() {
		session = Conexao.getInstance();
		listaUsuario = new ArrayList<Usuario>();
		listaUsuario = session.createQuery("from Usuario u where u.perfil.id= 2").list();
		session.close();		
		return listaUsuario;		
	}
	
	public List<Usuario> listaLazy(int first, int pageSize) {
		session = Conexao.getInstance();
		listaUsuario = new ArrayList<Usuario>();
		listaUsuario = session.createQuery("from Usuario u where deleted=0")
							  .setFirstResult(first)
							  .setMaxResults(pageSize)
							  .setCacheable(true)
							  .list();
		session.close();
		return listaUsuario;
	}

	public int totalUsuario() {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		linhas = (Long) session.createQuery("select count(usuario.id) from Usuario usuario" +
										" where deleted = 0")
							   .setCacheable(true)
							   .uniqueResult();
		session.close();
		return linhas.intValue();
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
