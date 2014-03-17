package br.com.casabemestilo.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagina;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.util.Conexao;

public class PaginaDAO implements InterfaceDAO {
	
	private Session session;
	
	private Pagina pagina;
	
	private List<Pagina> paginas;

	public PaginaDAO(Session session, Pagina pagina, List<Pagina> paginas) {
		super();
		this.session = session;
		this.pagina = pagina;
		this.paginas = paginas;
	}

	public PaginaDAO() {
	
	}

	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagina = (Pagina) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(pagina);
		session.getTransaction().commit();		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagina = (Pagina) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.update(pagina);
		session.getTransaction().commit();
		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagina = (Pagina) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.update(pagina);
		session.getTransaction().commit();		
	}

	@Override
	public Pagina buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		pagina = (Pagina) session.get(Pagina.class, id);
		session.close();
		return pagina;
	}

	@Override
	public List<Pagina> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		paginas = new ArrayList<Pagina>();
		paginas = session.createQuery("from Pagina").setCacheable(true).list();
		session.close();
		return paginas;
	}

	@Override
	public <T> List<T> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pagina> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		List<Pagina> paginasComPermissao = (List<Pagina>) obj;
		paginas = session.createQuery("from Pagina pagina where" +
											" pagina not in(:paginas)")
						 .setParameter("paginas", paginasComPermissao)
						 .list();
		session.close();
		return paginas;
	}

	public List<Pagina> listaPaginas(int first, int pageSize,  Map<String, String> filters) {
		session = Conexao.getInstance();
		paginas = new ArrayList<Pagina>();
		String hql = "from Pagina pagina";
		
		if(filters.containsKey("nomePagina")){
			hql += " where pagina.nomePagina like '%" + filters.get("nomePagina") + "%'";
		}
		
		paginas = session.createQuery(hql)
						 .setFirstResult(first)
						 .setMaxResults(pageSize)
						 .setCacheable(true)
						 .list();
		
		session.close();
		return paginas;
	}

	public int totalPaginas(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		String hql = "select count(pagina.id) from Pagina pagina";
		
		if(filters.containsKey("nomePagina")){
			hql += " pagina.nomePagina like '%" + filters.get("nomePagina") + "%'";
		}
		
		linhas = (Long) session.createQuery(hql)
							   .setCacheable(true)
							   .uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Pagina getPagina() {
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}

	public List<Pagina> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	
	
}
