package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Ocproduto;

public class OcProdutoControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Ocproduto ocproduto;
	
	private List<Ocproduto> listaOcproduto;
	
	private OcProdutoDAO ocProdutoDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public OcProdutoControl(String messagem, Ocproduto ocproduto,
			List<Ocproduto> listaOcproduto, OcProdutoDAO ocProdutoDAO) {
		super(messagem);
		this.ocproduto = ocproduto;
		this.listaOcproduto = listaOcproduto;
		this.ocProdutoDAO = ocProdutoDAO;
	}

	public OcProdutoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public OcProdutoControl() {
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
	public List<Ocproduto> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ocproduto buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * GETTERS & SETTERS
	 * */
	public Ocproduto getOcproduto() {
		return ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}

	public List<Ocproduto> getListaOcproduto() {
		return listaOcproduto;
	}

	public void setListaOcproduto(List<Ocproduto> listaOcproduto) {
		this.listaOcproduto = listaOcproduto;
	}

	public OcProdutoDAO getOcProdutoDAO() {
		return ocProdutoDAO;
	}

	public void setOcProdutoDAO(OcProdutoDAO ocProdutoDAO) {
		this.ocProdutoDAO = ocProdutoDAO;
	}
	
}
