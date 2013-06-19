package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.PedidoProdutoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;

public class PedidoProdutoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedidoproduto pedidoProduto;
	
	private List<Pedidoproduto> listaPedidoProduto;
	
	private PedidoProdutoDAO pedidoProdutoDAO;

	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoProdutoControl(String messagem, Pedidoproduto pedidoProduto,
			List<Pedidoproduto> listaPedidoProduto,
			PedidoProdutoDAO pedidoProdutoDAO) {
		super(messagem);
		this.pedidoProduto = pedidoProduto;
		this.listaPedidoProduto = listaPedidoProduto;
		this.pedidoProdutoDAO = pedidoProdutoDAO;
	}

	public PedidoProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PedidoProdutoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Pedidoproduto> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	@Override
	public Pedidoproduto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

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

	public PedidoProdutoDAO getPedidoProdutoDAO() {
		return pedidoProdutoDAO;
	}

	public void setPedidoProdutoDAO(PedidoProdutoDAO pedidoProdutoDAO) {
		this.pedidoProdutoDAO = pedidoProdutoDAO;
	}

}
