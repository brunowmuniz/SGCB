package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.model.CondicoesPagamento;
import br.com.casabemestilo.util.Conexao;

public class CondicoesPagamentoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private CondicoesPagamento condicoesPagamento;
	
	private List<CondicoesPagamento> listaRetencao;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public CondicoesPagamentoDAO(CondicoesPagamento condicoesPagamento, List<CondicoesPagamento> listaRetencao) {
		super();
		this.condicoesPagamento = condicoesPagamento;
		this.listaRetencao = listaRetencao;
	}

	public CondicoesPagamentoDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		this.condicoesPagamento = (CondicoesPagamento) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(this.condicoesPagamento);
		session.getTransaction().commit();

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		condicoesPagamento = (CondicoesPagamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(condicoesPagamento);
		session.getTransaction().commit();

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		condicoesPagamento = (CondicoesPagamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update CondicoesPagamento r set r.deleted= :deleted where r.id= :id")
			   .setBoolean("deleted", true)
			   .setInteger("id", condicoesPagamento.getId())
			   .executeUpdate();
		session.getTransaction().commit();

	}

	@Override
	public CondicoesPagamento buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		condicoesPagamento = (CondicoesPagamento) session.createQuery("from CondicoesPagamento cp where cp.id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return condicoesPagamento;
	}

	@Override
	public List<CondicoesPagamento> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CondicoesPagamento> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaRetencao = session.createQuery("from CondicoesPagamento r where r.deleted=0").list();
		session.close();
		return listaRetencao;
	}

	@Override
	public List<CondicoesPagamento> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CondicoesPagamento> listaLazy(int first, int pageSize,
			Map<String, String> filters) {
		session = Conexao.getInstance();
		listaRetencao = new ArrayList<CondicoesPagamento>();
		String hql = "from CondicoesPagamento condicoesPagamento" +
						" where condicoesPagamento.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and condicoesPagamento.nome like '%" + filters.get("nome") + "%'";
		}
		if(filters.containsKey("formapagamento.id")){
			hql += " and condicoesPagamento.formapagamento.id =" + filters.get("formapagamento.id");
		}
		
		listaRetencao = session.createQuery(hql)
							   .setFirstResult(first)
							   .setMaxResults(pageSize)
							   .setCacheable(true) 
							   .list();
		
		session.close();
		return listaRetencao;
	}

	public int totalOc(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		String hql = "select count(condicoesPagamento.id) from CondicoesPagamento condicoesPagamento" +
						" where condicoesPagamento.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and condicoesPagamento.nome like '%" + filters.get("nome") + "%'";
		}
		if(filters.containsKey("formapagamento.id")){
			hql += " and condicoesPagamento.formapagamento.id =" + filters.get("formapagamento.id");
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
	public CondicoesPagamento getRetencao() {
		return condicoesPagamento;
	}

	public void setRetencao(CondicoesPagamento condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}

	public List<CondicoesPagamento> getListaRetencao() {
		return listaRetencao;
	}

	public void setListaRetencao(List<CondicoesPagamento> listaRetencao) {
		this.listaRetencao = listaRetencao;
	}

	
	
}
