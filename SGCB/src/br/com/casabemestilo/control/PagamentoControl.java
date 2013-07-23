package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.casabemestilo.DAO.PagamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Pagamento;

@ManagedBean
@ViewScoped
public class PagamentoControl extends Control implements InterfaceControl,
		Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Pagamento pagamento;
	
	private List<Pagamento> listaPagamento;
	
	private PagamentoDAO pagamentoDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PagamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PagamentoControl() {
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
	public <T> List<T> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
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

	public PagamentoDAO getPagamentoDAO() {
		return pagamentoDAO;
	}

	public void setPagamentoDAO(PagamentoDAO pagamentoDAO) {
		this.pagamentoDAO = pagamentoDAO;
	}

}
