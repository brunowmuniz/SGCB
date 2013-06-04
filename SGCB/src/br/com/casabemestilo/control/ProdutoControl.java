package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Produto;

public class ProdutoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	
	private List<Produto> listaProduto;
	
	private ProdutoDAO produtoDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ProdutoControl(String messagem, Produto produto,
			List<Produto> listaProduto, ProdutoDAO produtoDAO) {
		super(messagem);
		this.produto = produto;
		this.listaProduto = listaProduto;
		this.produtoDAO = produtoDAO;
	}

	public ProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public ProdutoControl() {
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
	public List<Produto> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

}
