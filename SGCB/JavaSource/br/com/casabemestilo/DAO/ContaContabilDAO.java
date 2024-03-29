package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Contacontabil;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.util.Conexao;

public class ContaContabilDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Contacontabil contacontabil;
	
	private List<Contacontabil> listaContacontabil;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ContaContabilDAO(Contacontabil contacontabil,
			List<Contacontabil> listaContacontabil) {
		super();
		this.contacontabil = contacontabil;
		this.listaContacontabil = listaContacontabil;
	}

	public ContaContabilDAO() {
		// TODO Auto-generated constructor stub
	}
	

	/*
	 * M�TODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		contacontabil = (Contacontabil) obj; 
		session = Conexao.getInstance();
		session.beginTransaction();
		session.save(contacontabil);
		session.getTransaction().commit();		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		contacontabil = (Contacontabil) obj; 
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(contacontabil);
		session.getTransaction().commit();		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contacontabil buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		contacontabil = (Contacontabil) session.get(Contacontabil.class, id);
		session.close();
		return contacontabil;
	}

	@Override
	public List<Contacontabil> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		listaContacontabil = new ArrayList<Contacontabil>();
		session = Conexao.getInstance();
		listaContacontabil = session.createQuery("from Contacontabil cc").setCacheable(true).list();
		session.close();
		return listaContacontabil;
	}

	@Override
	public List<Contacontabil> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacontabil> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contacontabil> listaLazy(int first, int pageSize,
			Map<String, String> filters) {
		session = Conexao.getInstance();
		listaContacontabil = new ArrayList<Contacontabil>();
		String hql = "from Contacontabil conta" +
						" where conta.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and conta.nome like '%" + filters.get("nome") + "%'";
		}
		if(filters.containsKey("tipo")){
			hql += " and conta.tipo= '" + filters.get("tipo") + "'";
		}
		
		listaContacontabil = session.createQuery(hql)
									.setFirstResult(first)
									.setMaxResults(pageSize)
									.setCacheable(true)
									.list();
		session.close();		
		return listaContacontabil;
	}

	public int totalContacontabil(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		String hql = "select count(conta.id) from Contacontabil conta" +
						" where conta.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and conta.nome like '%" + filters.get("nome") + "%'";
		}
		if(filters.containsKey("tipo")){
			hql += " and conta.tipo= '" + filters.get("tipo") + "'";
		}
		
		linhas = (Long) session.createQuery(hql)
							   .setCacheable(true)
							   .uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Contacontabil getContacontabil() {
		return contacontabil;
	}

	public void setContacontabil(Contacontabil contacontabil) {
		this.contacontabil = contacontabil;
	}

	public List<Contacontabil> getListaContacontabil() {
		return listaContacontabil;
	}

	public void setListaContacontabil(List<Contacontabil> listaContacontabil) {
		this.listaContacontabil = listaContacontabil;
	}
	
	
}
