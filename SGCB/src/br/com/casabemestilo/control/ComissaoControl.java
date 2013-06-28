package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.Usuario;

@ManagedBean
@ViewScoped
public class ComissaoControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Comissao comissao;
	
	private ComissaoDAO comissaoDAO;
	
	private List<Comissao> listaComissao;
	
	
	/*
	 * CONSTRUTORES
	 * */	
	public ComissaoControl(String messagem, Comissao comissao,
			ComissaoDAO comissaoDAO, List<Comissao> listaComissao) {
		super(messagem);
		this.comissao = comissao;
		this.comissaoDAO = comissaoDAO;
		this.listaComissao = listaComissao;
	}	
	
	public ComissaoControl() {
		super();		
	}

	public ComissaoControl(String messagem) {
		super(messagem);
	}
	

	/*
	 * MÉTODOS
	 * */
	 @PostConstruct
	 public void init(){    	 
		 if(ELFlash.getFlash().get("usuarioComissao") != null){
			Usuario usuario = new Usuario();
			comissao = new Comissao();
			usuario = (Usuario) ELFlash.getFlash().get("usuarioComissao");
			comissao.setUsuario(usuario);
			
			
		}
	 }
		
	    
	 @PreDestroy
	 public void destroy() {}
	    
	@Override
	public void gravar() {
		comissaoDAO = new ComissaoDAO();
		try {
			comissaoDAO.insert(comissao);
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

	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comissao> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comissao buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Comissao getComissao() {
		if(comissao == null){
			comissao = new Comissao();
		}
		return comissao;
	}

	public void setComissao(Comissao comissao) {
		this.comissao = comissao;
	}

	public ComissaoDAO getComissaoDAO() {
		return comissaoDAO;
	}

	public void setComissaoDAO(ComissaoDAO comissaoDAO) {
		this.comissaoDAO = comissaoDAO;
	}

	public List<Comissao> getListaComissao() {
		return listaComissao;
	}

	public void setListaComissao(List<Comissao> listaComissao) {
		this.listaComissao = listaComissao;
	}
	
}
