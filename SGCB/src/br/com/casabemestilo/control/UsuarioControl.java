package br.com.casabemestilo.control;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.DAO.UsuarioFilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.model.UsuarioFilial;

@ManagedBean
@ViewScoped
public class UsuarioControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	private List<String> listaUsuarioFilial;
	
	
	
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
		usuario = new Usuario();
	}

	
	/*
	 * MÉTODOS
	 * */
	/*
     * MÉTODOS
     */
    @PostConstruct
    public void init(){    	 
    	if(ELFlash.getFlash().get("id") != null){
    		usuario.setId((Integer) ELFlash.getFlash().get("id"));
    		buscaObjetoId(usuario.getId());
    		System.out.println(usuario);
    		
    	}
    }
	
    
    @PreDestroy
    public void destroy() {    	
    	 if(usuario == null){
    		 usuario = new Usuario();
    	 }
    	 System.out.println(usuario.getId());
    }
    
	@Override
	public void gravar() {
		usuarioDAO = new UsuarioDAO();
		try {
			usuario.setSenha(md5(usuario.getSenha()));
			usuario.setDeleted(false);
			usuario = usuarioDAO.insert(usuario);			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário: " + usuario.getId() + "-" + usuario.getNome() + " gravado!"));
			logger.info("Salvo perfil: " + usuario.toString());			
			for (Iterator<String> iterUsuarioFilial = listaUsuarioFilial.iterator(); iterUsuarioFilial.hasNext();) {
				UsuarioFilial usuarioFilial =  new UsuarioFilial();
				usuarioFilial.getFilial().setId(Integer.parseInt(iterUsuarioFilial.next()));
				usuarioFilial.setUsuario(usuario);
				UsuarioFilialDAO usuarioFilialDAO = new UsuarioFilialDAO();
				try {
					usuarioFilialDAO.insert(usuarioFilial);
					logger.info("Salvo filialusuario: " + usuarioFilial.getFilial().getNome());
				} catch (Exception e) {
					super.mensagem = e.getMessage();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
					logger.error("Erro - filialusuario: " + super.mensagem + "-" + usuarioFilial.getFilial().getNome());
				}
			}
        	usuario = new Usuario();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + usuario.getNome());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "-" + usuario.getNome());
		}catch (NoSuchAlgorithmException e) {
			logger.error("Erro Não Encontrou algorítimo: " + super.mensagem + "-" + usuario.getNome());
		}catch(UnsupportedEncodingException e){
			logger.error("Erro Enconding: " + super.mensagem + "-" + usuario.getNome());
		}
	}

	@Override
	public void deletar() {		
		try{    		
    		usuario.setDeleted(true);
        	usuarioDAO.delete(usuario);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário:" + usuario.getNome() + " deletado!"));
    	}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub

	}
	
	
	public String alterarCadastro(){
		ELFlash.getFlash().put("id", usuario.getId());
		return "cadastrausuario";
	}

	@Override
	public List<Usuario> listarAtivos() {
		try{
			usuarioDAO = new UsuarioDAO();
			listaUsuario = usuarioDAO.listaAtivos();
		}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
		return listaUsuario;		
	}

	@Override
	public List<Usuario> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listaSelecao(Object obj) {
		// TODO Auto-generated method stuba
		return null;
	}

	@Override
	public Usuario buscaObjetoId(Integer id) {
		try{
			usuarioDAO = new UsuarioDAO();
			usuario = usuarioDAO.buscaObjetoId(usuario.getId());
		}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}		
		return usuario;
	}
	
	public String sairAlteracao(){
		return "manutencaousuario?faces-redirect=true";
	}
	
	public String md5(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException{		
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    BigInteger hash = new BigInteger(1, md.digest(senha.getBytes("ISO-8859-1")));
	    String senhaMD5 = hash.toString(16);
	    return senhaMD5;
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

	public List<String> getListaUsuarioFilial() {
		return listaUsuarioFilial;
	}

	public void setListaUsuarioFilial(List<String> listaUsuarioFilial) {
		this.listaUsuarioFilial = listaUsuarioFilial;
	}

	
	
}
