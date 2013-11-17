package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.util.Conexao;

public class ParcelaDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Parcela parcela;
	
	private List<Parcela> listaParcela;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ParcelaDAO(Parcela parcela, List<Parcela> listaParcela) {
		super();
		this.parcela = parcela;
		this.listaParcela = listaParcela;
	}

	public ParcelaDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		parcela = (Parcela) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(parcela);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcela buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcela> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int totalParcelasAVencer(Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		linhas = (Long) session.createQuery("select count(*) from Parcela p " +
												"where" +
													" p.dataentrada between :dataInicial and :dataFinal" +
												" and " +
													" p.pagamento.condicoesPagamento.formapagamento.id <> 4" +
												" and" +
													" p.deleted = false")
							   .setDate("dataInicial", dataInicial)
							   .setDate("dataFinal", dataFinal)
							   .setCacheable(true)
							   .uniqueResult();
		
		session.close();
		return linhas.intValue();
	}

	public List<Parcela> listaParcelasAVencer(int first, int pageSize, Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		session.beginTransaction();
		
		listaParcela = session.createQuery("from Parcela p" +
											" where" +
												" p.dataentrada between :dataInicial and :dataFinal " +
											" and " +
												" p.pagamento.condicoesPagamento.formapagamento.id <> 4" +
											" and" +
												" p.deleted = false")
							  .setDate("dataInicial", dataInicial)
							  .setDate("dataFinal", dataFinal)
							  .setFirstResult(first)
							  .setMaxResults(pageSize)
							  .setCacheable(true)
							  .list();
		session.close();
		return listaParcela;
	}
	
	public int totalParcelasAVencerCheque(Date dataInicial, Date dataFinal, Map<String, String> filter) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		String hql = "select count(*) " +
							"from Parcela p " +
							"where " +
								"p.dataentrada between :dataInicial and :dataFinal" +
							" and " +
								"p.pagamento.condicoesPagamento.formapagamento.id = 4" +
							" and" +
								" p.deleted = false";
		
		if(filter.containsKey("situacaoCheque")){
			hql += " and p.situacaoCheque = '" + filter.get("situacaoCheque") + "'"; 
		}
		
		if(filter.containsKey("pagamento.banco.id")){
			hql += " and p.pagamento.banco.id = " + filter.get("pagamento.banco.id"); 
		}
		
		if(filter.containsKey("pagamento.oc.id")){
			hql += " and p.pagamento.oc.id = " + filter.get("pagamento.oc.id");
		}
		
		if(filter.containsKey("numeroCheque")){
			hql += " and p.numeroCheque like '%" + filter.get("numeroCheque") + "%'"; 
		}
		
		if(filter.containsKey("pagamento.cliente.nome")){
			hql += " and p.pagamento.cliente.nome like '%" + filter.get("pagamento.cliente.nome") + "%'"; 
		}
		
		linhas = (Long) session.createQuery(hql)
								.setDate("dataInicial", dataInicial)
								.setDate("dataFinal", dataFinal)								
								.setCacheable(true)
								.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}

	public List<Parcela> listaParcelasAVencerCheque(int first, int pageSize, Date dataInicial, Date dataFinal, Map<String, String> filter) {
		session = Conexao.getInstance();
		session.beginTransaction();
		String hql = "from Parcela p " +
							"join fetch p.pagamento.banco " +
						"where " +
							"p.dataentrada between :dataInicial and :dataFinal " +
						"and " +
							"p.pagamento.condicoesPagamento.formapagamento.id = 4" +
						" and" +
							" p.deleted = false";
		
		if(filter.containsKey("situacaoCheque")){
			hql += " and p.situacaoCheque = '" + filter.get("situacaoCheque") + "'"; 
		}
		
		if(filter.containsKey("pagamento.banco.id")){
			hql += " and p.pagamento.banco.id = " + filter.get("pagamento.banco.id"); 
		}
		
		if(filter.containsKey("pagamento.oc.id")){
			hql += " and p.pagamento.oc.id = " + filter.get("pagamento.oc.id");
		}
		
		if(filter.containsKey("numeroCheque")){
			hql += " and p.numeroCheque like '%" + filter.get("numeroCheque") + "%'"; 
		}
		
		if(filter.containsKey("pagamento.cliente.nome")){
			hql += " and p.pagamento.cliente.nome like '%" + filter.get("pagamento.cliente.nome") + "%'"; 
		}
		
		listaParcela = session.createQuery(hql)
							  .setDate("dataInicial", dataInicial)
							  .setDate("dataFinal", dataFinal)							  
							  .setFirstResult(first)
							  .setMaxResults(pageSize)
							  .setCacheable(true)
							  .list();
		session.close();
		return listaParcela;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

	public List<Parcela> getListaParcela() {
		return listaParcela;
	}

	public void setListaParcela(List<Parcela> listaParcela) {
		this.listaParcela = listaParcela;
	}
	
}
