package br.com.casabemestilo.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.control.PagamentoAvulsoControl;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.PagamentoAvulso;
import br.com.casabemestilo.util.Conexao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PagamentoAvulsoDAO implements InterfaceDAO, Serializable {

	private Session session;
	
	private PagamentoAvulso pagamentoAvulso;
	
	private List<PagamentoAvulso> pagamentoAvulsos;

	
	
	public PagamentoAvulsoDAO() {
		super();
	}

	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagamentoAvulso = (PagamentoAvulso) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(pagamentoAvulso);
		session.getTransaction().commit();		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagamentoAvulso = (PagamentoAvulso) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(pagamentoAvulso);
		session.getTransaction().commit();		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagamentoAvulso = (PagamentoAvulso) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.delete(pagamentoAvulso);
		session.getTransaction().commit();
	}

	@Override
	public PagamentoAvulso buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		pagamentoAvulso = (PagamentoAvulso) session.get(PagamentoAvulso.class, id);
		session.close();
		return pagamentoAvulso;
	}

	@Override
	public <T> List<T> listaTodos() throws Exception, HibernateException,
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

	public List<Object> buscaPagamentosAvulsosDia(
			Date dataLancamento) {
		session = Conexao.getInstance();
		List<Object> pagamentoAvulsos = new ArrayList<Object>();
		pagamentoAvulsos = session.createQuery("from PagamentoAvulso pagamentoAvulso " +
												" inner join pagamentoAvulso.pagamentos pagamento " +
												" where " +
													" pagamentoAvulso.dataLancamento = :dataLancamento" +
												" and" +
													" pagamentoAvulso.deleted = false")
									.setDate("dataLancamento", dataLancamento)
									.setCacheable(true)
									.list();
		session.close();
		return pagamentoAvulsos;
	}
	
	public List<PagamentoAvulso> listaLazyPagamentoAvulso(int first,
			int pageSize, Map<String, String> filters, Date dataInicial,
			Date dataFinal) {
		session = Conexao.getInstance();
		pagamentoAvulsos = new ArrayList<PagamentoAvulso>();
		pagamentoAvulsos = session.createQuery("from PagamentoAvulso pagamentoAvulso " +
												/*" join fetch pagamentoAvulso.pagamentos pagamento" +
												" left join fetch pagamento.cliente cliente" +*/
												" where " +
													" pagamentoAvulso.deleted= false " +
												" and " +
													" pagamentoAvulso.dataLancamento between" +
														" :dataInicial and :dataFinal")
								 .setDate("dataInicial", dataInicial)
								 .setDate("dataFinal", dataFinal)
								 .setCacheable(true)
								 .list();
		
		session.close();
		return pagamentoAvulsos;	}

	public int totalPagamentoAvulso(Map<String, String> filters,
			Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");		
		linhas = (Long) session.createQuery("select count(pagamentoAvulso.id)" +
												" from " +
													" PagamentoAvulso pagamentoAvulso" +
												" where " +
													" pagamentoAvulso.deleted= false " +
												" and " +
													" pagamentoAvulso.dataLancamento between" +
														" :dataInicial and :dataFinal")
							  .setDate("dataInicial", dataInicial)
							  .setDate("dataFinal", dataFinal)
							  .uniqueResult();
		
		session.close();
		return linhas.intValue();		
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public PagamentoAvulso getPagamentoAvulso() {
		return pagamentoAvulso;
	}

	public void setPagamentoAvulso(PagamentoAvulso pagamentoAvulso) {
		this.pagamentoAvulso = pagamentoAvulso;
	}

	public List<PagamentoAvulso> getPagamentoAvulsos() {
		return pagamentoAvulsos;
	}

	public void setPagamentoAvulsos(List<PagamentoAvulso> pagamentoAvulsos) {
		this.pagamentoAvulsos = pagamentoAvulsos;
	}
	
	
	
	
}
