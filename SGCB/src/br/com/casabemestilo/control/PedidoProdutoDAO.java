package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Pedidoproduto;

public class PedidoProdutoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedidoproduto pedidoProduto;
	
	private List<Pedidoproduto> listaPedidoProduto;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoProdutoDAO(Pedidoproduto pedidoProduto,
			List<Pedidoproduto> listaPedidoProduto) {
		super();
		this.pedidoProduto = pedidoProduto;
		this.listaPedidoProduto = listaPedidoProduto;
	}

	public PedidoProdutoDAO() {
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
		// TODO Auto-generated method stub

	}

	@Override
	public Pedidoproduto buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Pedidoproduto getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(Pedidoproduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public List<Pedidoproduto> getListaPedidoProduto() {
		return listaPedidoProduto;
	}

	public void setListaPedidoProduto(List<Pedidoproduto> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}
	
}
