package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.util.Conexao;

public class PedidoDAO implements InterfaceDAO, Serializable {
	
	

	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Pedido pedido;
	
	private List<Pedido> listaPedido;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoDAO(Pedido pedido, List<Pedido> listaPedido) {
		super();
		this.pedido = pedido;
		this.listaPedido = listaPedido;
	}

	public PedidoDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pedido = (Pedido) obj; 
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(pedido);
		session.getTransaction().commit();
	}
	
	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		pedido = (Pedido) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(pedido);		
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Pedido buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		pedido = (Pedido) session.get(Pedido.class, id);
		session.close();
		return pedido;		
	}

	@Override
	public List<Pedido> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Pedido> listaPedidos(int first, int pageSize) {
		listaPedido = new ArrayList<Pedido>();  
		session = Conexao.getInstance();
		session.beginTransaction();
		listaPedido = session.createQuery("from Pedido p " +	
											"order by p.id desc")
							 .setFirstResult(first)
							 .setMaxResults(pageSize)
							 .setCacheable(true)
							 .list();
		session.close();
		return listaPedido;
	}

	public int totalPedidos() {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		linhas = (Long) session.createQuery("select count(*) from Pedido p")
						.setCacheable(true)
						.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}

}
