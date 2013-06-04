package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.LancamentoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Lancamento;

public class LancamentoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Lancamento> listaLancamento;
	
	private Lancamento lancamento;
	
	private LancamentoDAO lancamentoDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public LancamentoControl(String messagem, List<Lancamento> listaLancamento,
			Lancamento lancamento, LancamentoDAO lancamentoDAO) {
		super(messagem);
		this.listaLancamento = listaLancamento;
		this.lancamento = lancamento;
		this.lancamentoDAO = lancamentoDAO;
	}

	public LancamentoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public LancamentoControl() {
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
	public List<Lancamento> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lancamento> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
