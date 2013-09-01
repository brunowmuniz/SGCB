package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.util.Conexao;


public class OcDAO implements InterfaceDAO, Serializable {


	private static final long serialVersionUID = 1L;
	
	Session session;
	
	StatelessSession sessionStateless;
	
	private List<Oc> listaOc;
	
	private Oc oc;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public OcDAO(List<Oc> listaOc, Oc oc) {
		super();
		
		this.listaOc = listaOc;
		this.oc = oc;
	}

	public OcDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(oc);
		session.getTransaction().commit();
	}
	
	public Oc insertOc(Object obj) throws Exception, HibernateException,
		ConstraintViolationException{
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		oc.setId((Integer) session.merge(oc));
		session.getTransaction().commit();
		return oc;
	}
	

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(oc);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		oc = (Oc) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(oc);
		session.getTransaction().commit();
	}

	@Override
	public Oc buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		oc = (Oc) session.get(Oc.class, id);
		session.close();
		return oc;
	}

	@Override
	public List<Oc> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();		
		listaOc = session.createQuery("from Oc o " +
									 " where " +
									 	" o.deleted=0" +
									 " and" +
									 	" o.status.id < 9 " +
									 " order by o.id desc")						
						.setFetchSize(20)
						.setCacheable(true).list();
		System.out.println("buscar");
		session.close();
		return listaOc;
	}

	@Override
	public List<Oc> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Oc> listaLazy(int startingAt, int maxPerPage, Map<String, String> filters){
		session = Conexao.getInstance();
		session.beginTransaction();
		
		String hql = "from Oc o " +
						 " where " +
						 	" o.deleted=0";
		
		if(filters.containsKey("cliente.nome")){
			hql += " and o.cliente.nome like '%" + filters.get("cliente.nome") + "%'";
		}
		if(filters.containsKey("status.id")){
			hql += " and o.status.id=" + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and o.usuario.id=" + filters.get("usuario.id");
		}else{
			hql += " and o.status.id < 9 ";
		}
		
		hql += " order by o.id desc";
		
		listaOc = session.createQuery(hql)
						.setFirstResult(startingAt)
						.setMaxResults(maxPerPage)
						.setCacheable(true)
						.list();
						 
		session.close();
		return listaOc;
	}
	
	public int totalOc(Map<String, String> filters) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		String hql = "select count(*) " +
							" from Oc o " +
						 " where " +
						 	" o.deleted=0";
		
		if(filters.containsKey("cliente.nome")){
			hql += " and o.cliente.nome like '%" + filters.get("cliente.nome") + "%'";
		}
		
		if(filters.containsKey("status.id")){
			hql += " and o.status.id=" + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and o.usuario.id=" + filters.get("usuario.id");
		}else{
			hql += " and o.status.id < 9 ";
		}
		
		linhas = (Long) session.createQuery(hql)
						.setCacheable(true).uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public List<Oc> listaLazyMontFrete(int first, int pageSize) {
		listaOc = new ArrayList<Oc>();
		session = Conexao.getInstance();
		listaOc = session.createQuery("select oc from Oc oc" +
									" right join oc.ocprodutos ocproduto with ocproduto.status.id = 5" +
									" where " +
										" oc.status.id < 9"+
									" group by oc.id")
						.setFirstResult(first)
						.setMaxResults(pageSize)
						.setCacheable(true)
						.list();
		session.close();
		return listaOc;
	}

	public int totalOcMontFrete() {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		linhas = (Long) session.createQuery("select count(oc.id) from Oc oc" +
												" right join oc.ocprodutos ocproduto with ocproduto.status.id = 5" +
												" where " +
													" oc.status.id < 9")
								.setCacheable(true)
								.uniqueResult();
		session.close();
		return linhas.intValue();
	}
	
	public List<Oc> listaLazyStatusProduto(int first, int pageSize,  Map<String, String> filters) {
		listaOc = new ArrayList<Oc>();
		session = Conexao.getInstance();
		String hql = "select oc from Oc oc" +
						" left join oc.ocprodutos ocproduto ";
		
		if(filters.containsKey("status.id")){
			hql += "with ocproduto.status.id= " + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql +=  " and oc.usuario.id = " + filters.get("usuario.id"); 
		}
		
		hql += " group by oc.id";
		
		listaOc = session.createQuery(hql)
						.setFirstResult(first)
						.setMaxResults(pageSize)
						.setCacheable(true)
						.list();
		session.close();
		return listaOc;
	}

	public int totalOcStatusProduto(Map<String, String> filters) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		String hql = "select count(oc.id) from Oc oc" +
						" left join oc.ocprodutos ocproduto ";
		
		if(filters.containsKey("status.id")){
			hql += "with ocproduto.status.id= " + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql +=  " and oc.usuario.id = " + filters.get("usuario.id"); 
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
	public List<Oc> getListaOc() {
		return listaOc;
	}

	public void setListaOc(List<Oc> listaOc) {
		this.listaOc = listaOc;
	}

	public Oc getOc() {
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}
	
	

}
