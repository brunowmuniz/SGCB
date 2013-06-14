package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.UsuarioFilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.model.UsuarioFilial;

@ViewScoped
@ManagedBean
public class UsuarioFilialControl extends Control implements InterfaceControl,
		Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioFilial usuarioFilial;
	
	private List<UsuarioFilial> listaUsuarioFilial;
	
	private UsuarioFilialDAO usuarioFilialDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public UsuarioFilialControl(String messagem, UsuarioFilial usuarioFilial,
			List<UsuarioFilial> listaUsuarioFilial,
			UsuarioFilialDAO usuarioFilialDAO) {
		super(messagem);
		this.usuarioFilial = usuarioFilial;
		this.listaUsuarioFilial = listaUsuarioFilial;
		this.usuarioFilialDAO = usuarioFilialDAO;
	}

	public UsuarioFilialControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public UsuarioFilialControl() {
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
	public List<UsuarioFilial> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioFilial> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioFilial> listaSelecao(Object obj) {
		return null;
	}

	@Override
	public UsuarioFilial buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<UsuarioFilial> listaFiliaisDoUsuario(Usuario usuario){
		usuarioFilialDAO = new UsuarioFilialDAO();
		try {
			listaUsuarioFilial = usuarioFilialDAO.listaFiliaisDoUsuario(usuario);
		} catch (ConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarioFilial;
	}

	

	
	/*
	 * GETTERS & SETTERS
	 * */
	public UsuarioFilial getUsuarioFilial() {
		return usuarioFilial;
	}

	public void setUsuarioFilial(UsuarioFilial usuarioFilial) {
		this.usuarioFilial = usuarioFilial;
	}

	public List<UsuarioFilial> getListaUsuarioFilial() {
		return listaUsuarioFilial;
	}

	public void setListaUsuarioFilial(List<UsuarioFilial> listaUsuarioFilial) {
		this.listaUsuarioFilial = listaUsuarioFilial;
	}

	public UsuarioFilialDAO getUsuarioFilialDAO() {
		return usuarioFilialDAO;
	}

	public void setUsuarioFilialDAO(UsuarioFilialDAO usuarioFilialDAO) {
		this.usuarioFilialDAO = usuarioFilialDAO;
	}
}
