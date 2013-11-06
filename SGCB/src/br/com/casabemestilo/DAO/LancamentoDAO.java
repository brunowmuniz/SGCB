package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.Type;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Lancamento;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.util.Conexao;

public class LancamentoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private List<Lancamento> listaLancamento;
	
	private Lancamento lancamento;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public LancamentoDAO(List<Lancamento> listaLancamento, Lancamento lancamento) {
		super();
		this.listaLancamento = listaLancamento;
		this.lancamento = lancamento;
	}

	public LancamentoDAO() {
		
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		lancamento = (Lancamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.save(lancamento);
		session.getTransaction().commit();
	}
	
	public Lancamento insertLista(Lancamento lancamento) {
		session = Conexao.getInstance();
		session.beginTransaction();
		lancamento.setId((Integer) session.save(lancamento));
		session.getTransaction().commit();
		return lancamento;
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		lancamento = (Lancamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(lancamento);
		session.getTransaction().commit();

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		lancamento = (Lancamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(lancamento);
		session.getTransaction().commit();
	}

	@Override
	public Lancamento buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Lancamento> listaLazyLancamento(int first, int pageSize,
			Map<String, String> filters, Date dataInicial, Date dataFinal, Boolean ehVale) {
		session = Conexao.getInstance();
		listaLancamento = new ArrayList<Lancamento>();
		String hql = "from Lancamento l" +
						" left join fetch l.lancamentoPai" +
						" inner join fetch l.usuario"+
						" where " +
							"l.dataLancamento between :dataInicial" +
												" and :dataFinal" +
							" and" +
								" l.deleted = 0" +
							" and" +
								" l.ehVale = :ehVale";
		
		if(filters.containsKey("contacontabil.id")){
			hql += " and l.contacontabil.id = " + filters.get("contacontabil.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and l.usuario.id = " + filters.get("usuario.id");
		}
		
		listaLancamento = session.createQuery(hql)
								.setDate("dataInicial", dataInicial)
								.setDate("dataFinal", dataFinal)
								.setBoolean("ehVale", ehVale)
								.setFirstResult(first)
								.setMaxResults(pageSize)
								.setCacheable(true)
								.list();
		session.close();
		return listaLancamento;
	}

	public int totalLancamento(Map<String, String> filters,  Date dataInicial, Date dataFinal, Boolean ehVale) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		String hql = "select " +
						" count(*)" +
						" from Lancamento l" +
						" where " +
							"l.dataLancamento between :dataInicial" +
												" and :dataFinal" +
							" and" +
								" l.deleted = 0" +
							" and" +
								" l.ehVale = :ehVale";
		
		if(filters.containsKey("contacontabil.id")){
			hql += " and l.contacontabil.id = " + filters.get("contacontabil.id");
		}
		
		if(filters.containsKey("usuario.id")){
			hql += " and l.usuario.id = " + filters.get("usuario.id");
		}
		
		linhas = (Long) session.createQuery(hql)
							 .setDate("dataInicial", dataInicial)
							 .setDate("dataFinal", dataFinal)
							 .setBoolean("ehVale", ehVale)
							 .setCacheable(true)
							 .uniqueResult();
		session.close();
		return linhas.intValue(); 
	}
	
	public void deletaLancamentoApartir(Lancamento lancamento) {
		session = Conexao.getInstance();
		session.beginTransaction();
		if(lancamento.getLancamentoPai() != null){
			session.createQuery("update Lancamento l" +
									" set" +
										" l.deleted = 1" +
									" where " +
										" l.lancamentoPai.id = :idLancamentoPai" +
									" and" +
										" l.id >= :idLancamento")
					.setInteger("idLancamentoPai", lancamento.getLancamentoPai().getId())
					.setInteger("idLancamento", lancamento.getId())
					.executeUpdate();
		}else{
			session.createQuery("update Lancamento l" +
									" set" +
										" l.deleted = 1" +
									" where " +
											" (l.lancamentoPai.id is null" +
											" and" +
												" l.id = :idLancamento)" +
										" or (l.lancamentoPai.id = :idLancamento" +
											" and" +
												" l.id >= :idLancamento)")			
			.setInteger("idLancamento", lancamento.getId())
			.executeUpdate();
		}
		
		session.getTransaction().commit();
	}

	public Lancamento buscaLancamentoPai(Integer id) {
		session = Conexao.getInstance();
		lancamento = (Lancamento) session.createQuery("from Lancamento l" +
											" left join fetch l.lancamentoPai" +
											" where" +
												" l.id = :idLancamento")
										 .setInteger("idLancamento", id)
										 .uniqueResult();
		session.close();
		return lancamento;		
	}
	
	public List<Lancamento> listaControleGeral(Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		listaLancamento = new ArrayList<Lancamento>();
		listaLancamento = session.createQuery("select" +
														" new Lancamento(l.id, l.contacontabil, sum(l.valor))"+	
													" from" +
														" Lancamento l" +
													" where" +
														" l.dataLancamento between :dataInicial and :dataFinal"+
													" group by l.contacontabil")
								 .setDate("dataInicial", dataInicial)
								 .setDate("dataFinal", dataFinal)
								 .list();
		session.close();
		return listaLancamento;
	}
	
	public List<Lancamento> buscaTotalValeFuncionarios(Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		listaLancamento = new ArrayList<Lancamento>();
		listaLancamento = session.createQuery(" from" +
													" Lancamento l" +
												" inner join fetch l.usuario" +
												" where" +														
													" l.dataLancamento between" +
														" :dataInicial" +
													" and" +
														" :dataFinal")
									.setDate("dataInicial", dataInicial)
									.setDate("dataFinal", dataFinal)
									.list();
		session.close();
		return listaLancamento;
	}
	
	public List<Lancamento> lancamentoDia(Date dataLancamento) {
		session = Conexao.getInstance();
		listaLancamento = new ArrayList<Lancamento>();
		listaLancamento = session.createQuery("from Lancamento lancamento" +
													" where " +
														" lancamento.dataLancamento = :dataLancamento" +
													" and" +
												 		" (lancamento.ehVale = false or" +
												 			" (lancamento.ehVale = true and lancamento.formapagamento = 3))")
								 .setDate("dataLancamento", dataLancamento)
								 .setCacheable(true)
								 .list();
		session.close();
		return listaLancamento;
	}
	
	public List<Lancamento> calculaSaldoAnterior(Date dataLancamento) {
		session = Conexao.getInstance();
		listaLancamento = new ArrayList<Lancamento>();
		listaLancamento = session.createQuery("select"+
													" new Lancamento(lancamento.contacontabil, lancamento.formapagamento, sum(lancamento.valor))"+
												" from"+
											 		" Lancamento lancamento"+
											 	" where" +
											 		 " lancamento.dataLancamento < :dataLancamento" +
											 	" and" +
											 		" (lancamento.ehVale = false or" +
											 			" (lancamento.ehVale = true and lancamento.formapagamento = 3))"+
												" group by lancamento.formapagamento.id")
								.setDate("dataLancamento", dataLancamento)
								.setCacheable(true)
								.list();
		session.close();
		return listaLancamento;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	@Override
	public List<Lancamento> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	
}
