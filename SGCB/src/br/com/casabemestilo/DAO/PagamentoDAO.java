package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagamento;
import br.com.casabemestilo.util.Conexao;

public class PagamentoDAO implements InterfaceDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	Session session;

	private Pagamento pagamento;
	
	private List<Pagamento> listaPagamento;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PagamentoDAO(Pagamento pagamento, List<Pagamento> listaPagamento) {
		super();
		this.pagamento = pagamento;
		this.listaPagamento = listaPagamento;
	}
	
	
	public PagamentoDAO() {
		super();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pagamento = (Pagamento) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.delete(pagamento);
		session.getTransaction().commit();
	}

	@Override
	public Pagamento buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pagamento> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pagamento> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pagamento> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Pagamento> calculaSaldoAnterior(Date dataLancamento){
		session = Conexao.getInstance();
		listaPagamento = new ArrayList<Pagamento>();
		listaPagamento = session.createQuery("select "+ 
													"new Pagamento(pagamento.condicoesPagamento, sum(pagamento.valor)) "+
												"from "+
													"Pagamento pagamento "+		
												" where "+
													"pagamento.oc.status.id not in (1,2,10) " +
												" and " +
													"pagamento.datalancamento < :dataLancamento"+
												" group by pagamento.condicoesPagamento.formapagamento.id")
										.setDate("dataLancamento",dataLancamento)
										.setCacheable(true)
										.list();
		session.close();
		return listaPagamento;
	}

	/*
	 * GETTERS & SETTERS
	 * */
	public Pagamento getPagamento() {
		return pagamento;
	}


	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}


	public List<Pagamento> getListaPagamento() {
		return listaPagamento;
	}


	public void setListaPagamento(List<Pagamento> listaPagamento) {
		this.listaPagamento = listaPagamento;
	}


	public List<Pagamento> listaPagamentosAVencer(int first, int pageSize) {
		
		return null;
	}


	public int totalPagamentosAVencer() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
