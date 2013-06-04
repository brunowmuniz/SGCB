package br.com.casabemestilo.control.Impl;

import java.util.List;

public interface InterfaceControl {

	
	public abstract void gravar();	
	public abstract void deletar();	
	public abstract void alterar();
	public abstract <T> List<T> listarAtivos();
	public abstract <T> List<T> listarTodos();
	public abstract <T> List<T> listaSelecao(Object obj);
	public abstract Object buscaObjetoId(Integer id);
}

