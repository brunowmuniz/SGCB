package br.com.casabemestilo.control;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.w3c.dom.ls.LSInput;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ClienteDAO;
import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.DAO.PermissaoDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.DAO.UsuarioFilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Filial;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Pagina;
import br.com.casabemestilo.model.Permissao;
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
	
	private List listaVendedorFilial;
	
	private LazyDataModel<Usuario> listaLazyUsuario;
	
	private String novaSenha =  "";
	
	private List listaPermissoesUsuario;
	
	private List listaUsuarioVendedor = new ArrayList();
	
	
	
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
	 * M�TODOS
	 * */
	/*
     * M�TODOS
     */
    @PostConstruct
    public void init(){    	 
    	if(ELFlash.getFlash().get("usuarioEdicao") != null){
    		usuario = (Usuario) ELFlash.getFlash().get("usuarioEdicao");
    		listaUsuarioFilial = (List<String>) ELFlash.getFlash().get("listaUsuarioFilial");
    	}
    	if(ELFlash.getFlash().get("usuarioPermissao") != null){
    		usuario = (Usuario) ELFlash.getFlash().get("usuarioPermissao");
    	}
    }
	
    
    @PreDestroy
    public void destroy() {}
    
    public String consultarUsuarioLogin(){
    	usuarioDAO = new UsuarioDAO();
    	PermissaoDAO permissaoDAO= new PermissaoDAO();
    	List<Pagina> listaPaginasPermissao = new ArrayList<Pagina>();
    	List<String> nomePaginasPermissao = new ArrayList<String>(); 
    	try {
			usuario.setSenha(new Encrypt().md5(usuario.getSenha()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	usuario = usuarioDAO.buscaUsuarioLogin(usuario);
    	if(usuario != null){
    		try {
				listaPaginasPermissao = permissaoDAO.listaSelecao(usuario);
				for(Pagina pagina : listaPaginasPermissao){
					nomePaginasPermissao.add(pagina.getNomePagina());
				}
				FacesContext facesContext = FacesContext.getCurrentInstance(); 
				HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
	    		HttpSession session = request.getSession(); 
				session.setAttribute("UsuarioLogado", usuario);
				session.setAttribute("nomePaginasPermissao", nomePaginasPermissao);				
			} catch (ConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				return "/content/home.xhtml?faces-redirect=true";
			}    		
    	}else{
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usu�rio e/ou Senha inv�lidos, favor verificar!", ""));
    		return "erro";
    	}
    }
    
    public String sairAplicacao(){
    	 FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
         HttpSession session = request.getSession();
         session.invalidate();
         return "/index.jsf?faces-redirect=true";
    }
    
    public void alteracaoSenha(ValueChangeEvent event){
    	try {
    		Encrypt encrypt = new Encrypt();
    		novaSenha = encrypt.md5(event.getNewValue().toString());    		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na defini��o: " + e.getMessage(), ""));
		}
    }
    
    public void alterarSenha(){
    	Encrypt encrypt = new Encrypt();
    	try {
    		usuarioDAO = new UsuarioDAO();
    		FacesContext facesContext = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Usuario user = (Usuario) session.getAttribute("UsuarioLogado");
            user.setSenha(new Encrypt().md5(usuario.getSenha()));
            usuarioDAO.update(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha Alterada!"));
		} catch (NoSuchAlgorithmException e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha n�o reiniciada. Erro de Algor�timo: " + e.getMessage(), ""));
		} catch (UnsupportedEncodingException e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha n�o reiniciada. Erro de Encoding: " + e.getMessage(), ""));
		} catch (ConstraintViolationException e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha n�o reiniciada. Erro de Constraint: " + e.getMessage(), ""));
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha n�o reiniciada. Erro Hibernate: " + e.getMessage(), ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha n�o reiniciada. Erro Gen�rico: " + e.getMessage(), ""));
		}
    }
    
	@Override
	public void gravar() {
		usuarioDAO = new UsuarioDAO();
		try {
			usuario.setDeleted(false);
			usuario.setSenha(novaSenha);
			usuario = usuarioDAO.insert(usuario);			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usu�rio: " + usuario.getNome() + " gravado!"));
			logger.info("Salvo perfil: " + usuario.toString());			
			UsuarioFilial usuarioFilial =  new UsuarioFilial();			
			usuarioFilial.getFilial().setId(1);
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
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usu�rio:" + usuario.getNome() + " deletado!"));
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
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usu�rio:" + usuario.getNome() + " alterado!"));
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
	
	public void verificaLoginExistente(){
		boolean isLoginExiste;
		usuarioDAO = new UsuarioDAO();
		isLoginExiste = usuarioDAO.verificaLoginExistente(usuario);
		if(isLoginExiste){
			usuario.setLogin("");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login j� existe, favor informar outro!", ""));
		}
	}
	
	public List getListaUsuarioMontador(){
		usuarioDAO = new UsuarioDAO();
		List listaUsuarioMontador = new ArrayList();
		listaUsuario = usuarioDAO.listaMontador();
		for(Usuario usuario: listaUsuario){
			SelectItem si = new SelectItem(usuario.getId(),usuario.getNome());
			listaUsuarioMontador.add(si);
		}
		return listaUsuarioMontador;
	}
	
	public List listaUsuarioVendedor(String filter){
		usuarioDAO = new UsuarioDAO();
		listaUsuario = usuarioDAO.listaVendedor();
		if(filter.equals("filter")){
			listaUsuarioVendedor.add(new SelectItem("","Todos"));
		}		
		for(Usuario usuario: listaUsuario){
			SelectItem si = new SelectItem(usuario.getId(),usuario.getNome());
			listaUsuarioVendedor.add(si);
		}
		return listaUsuarioVendedor;
	}
	
	public List listaTodosUsuariosAtivosCombo(String filter){
		List listaUsuariosAtivos = new ArrayList();
		listarAtivos();
		if(filter.equals("filter")){
			listaUsuariosAtivos.add(new SelectItem("","Todos"));
		}
		for(Usuario usuarioAtivo : listaUsuario){
			SelectItem item = new SelectItem(usuarioAtivo.getId(), usuarioAtivo.getNome());
			listaUsuariosAtivos.add(item);
		}
		return listaUsuariosAtivos;
	}
	
	public LazyDataModel<Usuario> listaLazyUsuarioGeral(){
		if(listaLazyUsuario == null){
			listaLazyUsuario = new LazyDataModel<Usuario>() {
				private List<Usuario> listaLazy;
		
				@Override
			    public Usuario getRowData(String idUsuario) {
			    	Integer id = Integer.valueOf(idUsuario);
			    	
			        for(Usuario usuario : listaLazy) {
			            if(usuario.getId().equals(id))
			                return usuario;
			        }
			        
			        return null;
			    }

			    @Override
			    public Object getRowKey(Usuario usuario) {
			        return usuario.getId();
			    }

			    @Override
			    public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
			    	usuarioDAO = new UsuarioDAO();  
			    	
			    	listaLazy = usuarioDAO.listaLazy(first, pageSize);
			    	
			    	if (getRowCount() <= 0) {  
			            setRowCount(usuarioDAO.totalUsuario());  
			        } 			       
			        setPageSize(pageSize);  
			        return listaLazy;  
			    }
			};
		}
		return listaLazyUsuario;
	}
	
	public String alterarPermissao(){
		ELFlash.getFlash().put("usuarioPermissao", usuario);
		return "cadastropermissao?faces-redirect=true";
	}

	
	public List<Usuario> listaComplete(String nomeUsuario){
		listaUsuario = new ArrayList<Usuario>();
		if(nomeUsuario.indexOf(" ") > -1){
			usuarioDAO = new UsuarioDAO();
			usuario.setNome(nomeUsuario);
			usuario.setDeleted(false);
			try {
				listaUsuario = usuarioDAO.listaSelecao(usuario);
				if(listaUsuario.isEmpty()){
					usuario.setNome(nomeUsuario);
					listaUsuario.add(usuario);
				}
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
		}		
		return listaUsuario;
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

	public List getListaVendedorFilial(Filial filial) {
		List<UsuarioFilial> listaVendedor = new ArrayList<UsuarioFilial>();		
		listaVendedorFilial = new ArrayList();
		if(filial.getId() == null){
			filial = new Filial();
			filial.setId(1);
		}
		listaVendedor = new UsuarioDAO().listaVendedorFilial(filial);
		for(UsuarioFilial usuarioFilial : listaVendedor){					
			SelectItem si = new SelectItem();
			si.setValue(usuarioFilial.getUsuario().getId());
			si.setLabel(usuarioFilial.getUsuario().getNome());
			listaVendedorFilial.add(si);
		}
		return listaVendedorFilial;
	}

	public void setListaVendedorFilial(List listaVendedorFilial) {
		this.listaVendedorFilial = listaVendedorFilial;
	}

	public LazyDataModel<Usuario> getListaLazyUsuario() {
		return listaLazyUsuario;
	}

	public void setListaLazyUsuario(LazyDataModel<Usuario> listaLazyUsuario) {
		this.listaLazyUsuario = listaLazyUsuario;
	}

	public List getListaPermissoesUsuario() {
		if(listaUsuarioVendedor.isEmpty()){
			usuarioDAO = new UsuarioDAO();
			usuario.setPermissoes(new PermissaoDAO().buscaPermissaoUsuario(usuario));
			listaPermissoesUsuario = new ArrayList();
			for(Permissao permissao : usuario.getPermissoes()){
				SelectItem si = new SelectItem();
				si.setValue(permissao.getPagina().getId());
				si.setLabel(permissao.getPagina().getNomePagina());
				listaPermissoesUsuario.add(si);
			}
		}		
		return listaPermissoesUsuario;
	}

	public void setListaPermissoesUsuario(List listaPermissoesUsuario) {
		this.listaPermissoesUsuario = listaPermissoesUsuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	
}
