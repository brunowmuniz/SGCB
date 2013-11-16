package br.com.casabemestilo.control;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.casabemestilo.DAO.PermissaoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Permissao;

@ManagedBean
@ViewScoped
public class PermissaoControl extends Control implements InterfaceControl {

	private Permissao permissao;
	
	private List<Permissao> permissoes;
	
	private PermissaoDAO permissaoDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PermissaoControl(String messagem, Permissao permissao,
			List<Permissao> permissoes, PermissaoDAO permissaoDAO) {
		super(messagem);
		this.permissao = permissao;
		this.permissoes = permissoes;
		this.permissaoDAO = permissaoDAO;
	}
	
	public PermissaoControl() {
		super();
	}

	public PermissaoControl(String messagem) {
		super(messagem);
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
	public List<Permissao> listarTodos() {
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
	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public PermissaoDAO getPermissaoDAO() {
		return permissaoDAO;
	}

	public void setPermissaoDAO(PermissaoDAO permissaoDAO) {
		this.permissaoDAO = permissaoDAO;
	}
	
	
}
