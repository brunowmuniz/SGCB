package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

	public List<Oc> listaLazy(int startingAt, int maxPerPage, Map<String, String> filters, Date dataInicial, Date dataFinal){
		session = Conexao.getInstance();
		session.beginTransaction();
		
		String hql = "from Oc o " +
						 " where " /*+
						 	" o.deleted=0"*/+
						 	" o.datalancamento between :dataInicial and :dataFinal";
		
		if(filters.containsKey("cliente.nome")){
			hql += " and o.cliente.nome like '%" + filters.get("cliente.nome") + "%'";
		}
		if(filters.containsKey("status.id")){
			hql += " and o.status.id=" + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and o.usuario.id=" + filters.get("usuario.id");
		}/*else{
			hql += " and o.status.id < 9 ";
		}*/
		
				
		hql += " order by o.id desc";
		
		listaOc = session.createQuery(hql)
						 .setDate("dataInicial",dataInicial)
						 .setDate("dataFinal", dataFinal)
						 .setFirstResult(startingAt)
						 .setMaxResults(maxPerPage)
						 .setCacheable(true)
						 .list();
						 
		session.close();
		return listaOc;
	}
	
	public int totalOc(Map<String, String> filters, Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		String hql = "select count(*) " +
							" from Oc o " +
						 " where " /*+
						 	" o.deleted=0"*/+ 
						 	"o.datalancamento between :dataInicial and :dataFinal";
		
		if(filters.containsKey("cliente.nome")){
			hql += " and o.cliente.nome like '%" + filters.get("cliente.nome") + "%'";
		}
		
		if(filters.containsKey("status.id")){
			hql += " and o.status.id=" + filters.get("status.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and o.usuario.id=" + filters.get("usuario.id");
		}/*else{
			hql += " and o.status.id < 9 ";
		}*/
				
		linhas = (Long) session.createQuery(hql)
							   .setDate("dataInicial",dataInicial)
				 			   .setDate("dataFinal", dataFinal)
							   .setCacheable(true)
							   .uniqueResult();
		
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
	
	public List<Oc> calculaVendasPorVendedor(Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		listaOc = new ArrayList<Oc>();
		List<Integer> listaStatus = new ArrayList<Integer>();		
		listaStatus.add(1);
		listaStatus.add(2);
		listaStatus.add(10);		
		listaOc = session.createQuery(" select new Oc(o.id, o.usuario, sum(o.valorliquido), sum(o.valorcomissao)) " +
											"from Oc o " +										
										"where "+
											"o.deleted = :ocDeleted " +
										"and " +
											"o.status.id not in(:listaStatus) " +
										"and " +
											"o.datalancamento between :dataInicial and :dataFinal "+
										"group by o.usuario")
							.setBoolean("ocDeleted", false)
							.setParameterList("listaStatus", listaStatus)
							.setDate("dataInicial", dataInicial)
							.setDate("dataFinal", dataFinal)
							.setCacheable(true)
							.list();
		
		session.close();
		return listaOc;
	}
	
	public List calcultaTotalVendasMesAno(Date dataInicial, Date dataFinal) {
		List listaVendasMesAno = new ArrayList();
		session = Conexao.getInstance();
		listaVendasMesAno = session.createSQLQuery("select oc.datalancamento, " +
											" sum(oc.valorliquido)," +
											" sum(oc.valorfinal)" +
										" from oc" +
										" where datalancamento between :dataInicial and :dataFinal" +
										" group by date_format(oc.datalancamento,'%Y-%m')")
						 .setDate("dataInicial", dataInicial)
						 .setDate("dataFinal", dataFinal)				
						 .list();
		
		session.close();
		return listaVendasMesAno;
	}
	
	public List<Oc> listaLazyVendedorAnalitico(int first, int pageSize,Integer idUsuario, Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaOc = new ArrayList<Oc>();
		listaOc = session.createQuery("from Oc oc" +
										" where" +											
											" oc.datalancamento between :dataInicial and :dataFinal" +
										" and" +
											" oc.usuario.id = :usuario" +
										" and" +
											" oc.deleted = 0" +
										" and" +
											" oc.status.id not in(1,2,10)")
						 .setDate("dataInicial", dataInicial)
						 .setDate("dataFinal", dataFinal)
						 .setInteger("usuario", idUsuario)
						 .setFirstResult(first)
						 .setMaxResults(pageSize)
						 .setCacheable(true)
						 .list();
		
		session.close();
		return listaOc;
	}

	public int totalVendedorAnalitico(Integer idUsuario, Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		
		linhas = (Long) session.createQuery("select count(oc.id) from Oc oc" +
												" where" +													
													" oc.datalancamento between :dataInicial and :dataFinal" +
												" and" +
													" oc.usuario.id = :usuario"+
												" and" +
													" oc.deleted = 0" +
												" and" +
													" oc.status.id not in(1,2,10)")
								.setDate("dataInicial", dataInicial)
								.setDate("dataFinal", dataFinal)
								.setInteger("usuario", idUsuario)
								.uniqueResult();
		session.close();
		return linhas.intValue();
	}
	
	public Float calculaComissaoVendedor(Integer idUsuario, Date dataInicial, Date dataFinal) {
		Double totalComissao = new Double(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		
		totalComissao = (Double) session.createQuery("select sum(oc.valorcomissao) from Oc oc" +
														" where" +
															" oc.status.id > 2" +
														" and" +
															" oc.datalancamento between :dataInicial and :dataFinal" +
														" and" +
															" oc.usuario.id = :usuario"+
														" and" +
															" oc.deleted = 0")
										.setDate("dataInicial", dataInicial)
										.setDate("dataFinal", dataFinal)
										.setInteger("usuario", idUsuario)
										.uniqueResult();
		session.close();
		return totalComissao.floatValue();
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

	public Double buscaVendasBruto(Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		Double totalVendasBruto = (Double) session.createQuery("select" +
																	" sum(o.valorfinal - o.valorfrete)"+
																" from" +
																	" Oc o" +
																" where" +
																	" o.deleted = false" +
																" and" +
																	" o.status.id not in(1,2,10)" +
																" and" +
																	" o.datalancamento between :dataInicial and :dataFinal")
												 .setDate("dataInicial", dataInicial)
												 .setDate("dataFinal", dataFinal)
												 .uniqueResult();
		session.close();
		return totalVendasBruto;
	}

	public Double buscaFretePago(Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		Double totalFrete = (Double) session.createQuery("select" +
														" sum(o.valorfrete)"+
													" from" +
														" Oc o" +
													" where" +
														" o.tipoFrete <> 'brinde'" +
													" and" +
														" o.datalancamento between :dataInicial and :dataFinal")
											.setDate("dataInicial", dataInicial)
											.setDate("dataFinal", dataFinal)
										    .uniqueResult();
		session.close();
		return totalFrete;
	}

	
	

}
