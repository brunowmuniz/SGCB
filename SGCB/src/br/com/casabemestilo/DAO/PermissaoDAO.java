package br.com.casabemestilo.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Permissao;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.Conexao;

public class PermissaoDAO implements InterfaceDAO{
	
	private Session session;
	
	private Permissao permissao;
	
	private List<Permissao> permissoes;
	
	
	
	public PermissaoDAO(Session session, Permissao permissao,
			List<Permissao> permissoes) {
		super();
		this.session = session;
		this.permissao = permissao;
		this.permissoes = permissoes;
	}
	
	
	public PermissaoDAO() {
		super();
	}


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
	public List<Permissao> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
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

	public List<Permissao> buscaPermissaoUsuario(Usuario usuario) {
		session = Conexao.getInstance();
		permissoes = new ArrayList<Permissao>();
		permissoes = session.createQuery("from Permissao permissao " +
											"where " +
												" permissao.usuario.id = :usuario")
							.setCacheable(true)
							.list();
		session.close();
		return permissoes;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
		

}
