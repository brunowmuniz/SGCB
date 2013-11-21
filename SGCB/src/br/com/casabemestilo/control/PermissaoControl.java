package br.com.casabemestilo.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.DualListModel;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.PaginaDAO;
import br.com.casabemestilo.DAO.PermissaoDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Pagina;
import br.com.casabemestilo.model.Permissao;
import br.com.casabemestilo.model.Usuario;

@ManagedBean
@ViewScoped
public class PermissaoControl extends Control implements InterfaceControl {

	private Permissao permissao;
	
	private List<Permissao> permissoes;
	
	private PermissaoDAO permissaoDAO;
	
	private DualListModel<Pagina> listaPaginasPermissao;
	
	
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
	 @PostConstruct
	 public void init(){}
		
	    
	@PreDestroy
	public void destroy() {}
	    
	    
	@Override
	public void gravar() {		
		try {
			PermissaoDAO permissaoDAO = new PermissaoDAO();
			permissoes = new ArrayList<Permissao>();
			permissaoDAO.delete(permissao.getUsuario());
			for(Pagina pagina : listaPaginasPermissao.getTarget()){
				Permissao permissaoPagina = new Permissao();
				permissaoPagina.setDeleted(false);
				permissaoPagina.setPagina(pagina);
				permissaoPagina.setUsuario(permissao.getUsuario());
				permissoes.add(permissaoPagina);				
			}
			permissaoDAO.insert(permissoes);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Permissões gravadas!"));
			permissao = new Permissao();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
		}
		
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

	public void limparUsuario(){
		permissao.setUsuario(new Usuario());
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Permissao getPermissao() {
		if(permissao == null){
			permissao = new Permissao();
		}
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
	
	public DualListModel<Pagina> getListaPaginasPermissao() {
		PaginaDAO paginaDAO = new PaginaDAO();
		permissaoDAO = new PermissaoDAO();
		List<Pagina> paginas = new ArrayList<Pagina>();
		List<Pagina> paginaComPermissao = new ArrayList<Pagina>();
		try {
						
			if(permissao.getUsuario().getId() != null){
				paginaComPermissao = permissaoDAO.listaSelecao(permissao.getUsuario());
			}
			
			paginas = paginaDAO.listaTodos();
			
			if(!paginaComPermissao.equals(paginas)){
				for(Pagina pagina : paginaComPermissao){
					if(paginas.contains(pagina)){
						paginas.remove(pagina);
					}
				}
			}else{
				paginas = new ArrayList<Pagina>();
			}
			
			listaPaginasPermissao = new DualListModel<Pagina>(paginas, paginaComPermissao);
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
		return listaPaginasPermissao;
	}

	public void setListaPaginasPermissao(DualListModel<Pagina> listaPaginasPermissao) {
		this.listaPaginasPermissao = listaPaginasPermissao;
	}
}
