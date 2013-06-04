package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Usuario;

public class UsuarioControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public UsuarioControl(String messagem, Usuario usuario,
			List<Usuario> listaUsuario,
			br.com.casabemestilo.DAO.UsuarioDAO usuarioDAO) {
		super(messagem);
		this.usuario = usuario;
		this.listaUsuario = listaUsuario;
		this.usuarioDAO = usuarioDAO;
	}

	public UsuarioControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public UsuarioControl() {
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
	public List<Usuario> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * GETTERS & SETTERS
	 * */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
