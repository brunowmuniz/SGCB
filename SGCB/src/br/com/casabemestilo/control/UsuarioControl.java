package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import com.sun.faces.context.flash.ELFlash;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.DAO.UsuarioFilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.model.UsuarioFilial;
import br.com.casabemestilo.util.Encrypt;

@ManagedBean
@ViewScoped
public class UsuarioControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	private List<String> listaUsuarioFilial;
	
	private String novaSenha =  "";
	
	
	
	/*
	 * CONSTRUTORES
	 * */
	public UsuarioControl(String messagem, Usuario usuario, List<Usuario> listaUsuario, UsuarioDAO usuarioDAO) {
		super(messagem);
		this.usuario = usuario;
		this.listaUsuario = listaUsuario;
		this.usuarioDAO = usuarioDAO;
	}

	public UsuarioControl(String messagem) {
		super(messagem);
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
    	if(ELFlash.getFlash().get("usuarioEdicao") != null){
    		usuario = (Usuario) ELFlash.getFlash().get("usuarioEdicao");
    		listaUsuarioFilial = (List<String>) ELFlash.getFlash().get("listaUsuarioFilial");
    	}
    }
	
    
    @PreDestroy
    public void destroy() {}
    
    public void alteracaoSenha(ValueChangeEvent event){
    	try {
    		Encrypt encrypt = new Encrypt();
    		novaSenha = encrypt.md5(event.getNewValue().toString());    		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na definição: " + e.getMessage(), ""));
		}
    }
    
	@Override
	public void gravar() {
		usuarioDAO = new UsuarioDAO();
		try {
			usuario.setDeleted(false);
			usuario.setSenha(novaSenha);
			usuario = usuarioDAO.insert(usuario);			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário: " + usuario.getNome() + " gravado!"));
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
		try{		
			usuarioDAO = new UsuarioDAO();
			if(novaSenha != ""){
				usuario.setSenha(novaSenha);
			}
    		usuarioDAO.update(usuario);    		
    		if(listaUsuarioFilial.size() != usuario.getUsuarioFiliais().size()){
    			for(Iterator<String> iterFilial = listaUsuarioFilial.iterator(); iterFilial.hasNext();){
    				Boolean ehAdicionar = true;
    				Integer idFilial = Integer.parseInt(iterFilial.next());
    				for(Iterator<UsuarioFilial> iterUsuarioFilial = usuario.getUsuarioFiliais().iterator(); iterUsuarioFilial.hasNext() && ehAdicionar;){
    					UsuarioFilial usuarioFilial = iterUsuarioFilial.next();
    					if(usuarioFilial.getFilial().getId() == idFilial){
    						ehAdicionar = false;
    					}
    				}
    				if(ehAdicionar){
    					UsuarioFilial usuarioFilial = new UsuarioFilial();
    					UsuarioFilialDAO usuarioFilialDAO = new UsuarioFilialDAO();
    					usuarioFilial.getUsuario().setId(usuario.getId());
    					usuarioFilial.getFilial().setId(idFilial);
    					usuario.getUsuarioFiliais().add(usuarioFilial);
    					usuarioFilialDAO.insert(usuarioFilial);
    				}
    			}
    			
    			for(Iterator<UsuarioFilial> iterUsuarioFilial = usuario.getUsuarioFiliais().iterator(); iterUsuarioFilial.hasNext();){
    				Boolean ehDeletar = true;
    				UsuarioFilial usuarioFilial = iterUsuarioFilial.next();
    				for(Iterator<String> iterFilial = listaUsuarioFilial.iterator(); iterFilial.hasNext() && ehDeletar;){
    					Integer idFilial = Integer.parseInt(iterFilial.next());
    					if(idFilial == usuarioFilial.getFilial().getId()){
    						ehDeletar = false;
    					}
    				}
    				if(ehDeletar){
    					UsuarioFilialDAO usuarioFilialDAO = new UsuarioFilialDAO();
    					usuarioFilial.setDeleted(true);
    					usuarioFilialDAO.delete(usuarioFilial);
    				}
    			}
    		}    		
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário:" + usuario.getNome() + " alterado!"));
    	}catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		}catch(HibernateException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
    	}catch (Exception e) {
    		e.printStackTrace();
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
	}
	
	
	public String alterarCadastro(){
		UsuarioFilialControl usuarioFilialControl = new UsuarioFilialControl();
		usuario.setUsuarioFiliais(usuarioFilialControl.listaFiliaisDoUsuario(usuario));
		listaUsuarioFilial = new ArrayList<String>();
		for(Iterator<UsuarioFilial> iterUsuarioFilial = usuario.getUsuarioFiliais().iterator(); iterUsuarioFilial.hasNext();){
			UsuarioFilial usuarioFilial = iterUsuarioFilial.next();    			
			listaUsuarioFilial.add(usuarioFilial.getFilial().getId().toString());
		}
		ELFlash.getFlash().put("listaUsuarioFilial", listaUsuarioFilial);
		ELFlash.getFlash().put("usuarioEdicao", usuario);
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
	
	public String alterarComissao(){
		ELFlash.getFlash().put("usuarioComissao", usuario);
		return "cadastracomissao?faces-redirect=true";
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
