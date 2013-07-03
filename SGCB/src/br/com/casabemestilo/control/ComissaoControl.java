package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.ComissaoDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
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
	
	private List<String> listaUsuarioComissaoConjunta;
	
	private List listaUsuarioCombo;
	
	
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
		 comissaoDAO = new ComissaoDAO();
		 if(ELFlash.getFlash().get("usuarioComissao") != null){
			Usuario usuario = new Usuario();
			comissao = new Comissao();			
			usuario = (Usuario) ELFlash.getFlash().get("usuarioComissao");
			comissao.setUsuario(usuario);
			try {
				comissao = comissaoDAO.buscaObjetoId(usuario.getId());
				if(comissao == null){
					comissao = new Comissao();
					comissao.setUsuario(usuario);					
				}else{
					if(comissao.getEhComissaoConjunta()){
						String[] idUsuarioComissao = comissao.getUsuarioComissaoConjunta().split(",");
						Integer i = 0;
						while(i <= idUsuarioComissao.length - 1){
							listaUsuarioComissaoConjunta.add(idUsuarioComissao[i]); 
						}
					}
					if(comissao.getEhComissaoMontadorConjunta()){
						String[] idUsuarioComissao = comissao.getUsuarioComissaoMontadorConjunta().split(",");
						Integer i = 1;
						while(i <= idUsuarioComissao.length - 1){
							listaUsuarioComissaoConjunta.add(idUsuarioComissao[i]); 
						}
					}
				}
			} catch (ConstraintViolationException e) {
				super.mensagem = e.getMessage();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
				logger.error("Erro Constraint: " + super.mensagem + "-" + comissao.getUsuario().getNome());
			} catch (HibernateException e) {
				super.mensagem = e.getMessage();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
				logger.error("Erro Constraint: " + super.mensagem + "-" + comissao.getUsuario().getNome());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	 }
		
	    
	@PreDestroy
	public void destroy() {}	 
	 
	public void defineComissaoVendedorIndividual(ValueChangeEvent event){
		if((Boolean) event.getNewValue()){
			comissao.setEhComissaoConjunta(false);
		}		
	}
	
	public void defineComissaoVendedorConjunta(ValueChangeEvent event){
		if((Boolean) event.getNewValue()){
			comissao.setEhComissaoIndividual(false);
		}		
	}
	
	public void defineComissaMontadorIndividual(ValueChangeEvent event){
		if((Boolean) event.getNewValue()){
			comissao.setEhComissaoMontadorConjunta(false);
		}
	}
	
	public void defineComissaMontadorConjunta(ValueChangeEvent event){
		if((Boolean) event.getNewValue()){
			comissao.setEhComissaoMontadorIndividual(false);
		}
	}
	 
	@Override
	public void gravar() {
		comissaoDAO = new ComissaoDAO();
		try {			
			if(comissao.getEhComissaoConjunta() || comissao.getEhComissaoMontadorConjunta()){
				if(comissao.getEhComissaoConjunta()){
					comissao.setUsuarioComissaoConjunta("");
				}
				if(comissao.getEhComissaoMontadorConjunta()){
					comissao.setUsuarioComissaoMontadorConjunta("");
				}
				for(Iterator<String> iterUsuarioComissaoConjunta = listaUsuarioComissaoConjunta.iterator(); iterUsuarioComissaoConjunta.hasNext();){
					String idUsuario = iterUsuarioComissaoConjunta.next();
					if(comissao.getUsuario().getPerfil().getId() == 5){
						if(comissao.getUsuarioComissaoMontadorConjunta() == ""){
							comissao.setUsuarioComissaoMontadorConjunta(idUsuario);
						}else{
							comissao.setUsuarioComissaoMontadorConjunta(comissao.getUsuarioComissaoMontadorConjunta() + "," + idUsuario);
						}
					}
					if(comissao.getUsuario().getPerfil().getId() == 2){
						if(comissao.getUsuarioComissaoConjunta() == ""){
							comissao.setUsuarioComissaoConjunta(idUsuario);
						}else{
							comissao.setUsuarioComissaoConjunta(comissao.getUsuarioComissaoConjunta() + "," + idUsuario);
						}
					}					
				}
			}
			comissaoDAO.insert(comissao);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comissão usuário: " + comissao.getUsuario().getNome() + " gravado!"));				
			logger.info("Salvo comissao usuário: " + comissao.getUsuario().toString());
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		}		
	}

	@Override
	public void deletar() {
		try {
			comissao.setDeleted(true);
			comissaoDAO.delete(comissao);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comissão usuário: " + comissao.getUsuario().getNome() + " deletado!"));
			logger.info("Salvo comissao usuário: " + comissao.getUsuario().toString());
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		}		
	}

	@Override
	public void alterar() {
		try {			
			if(comissao.getEhComissaoConjunta() || comissao.getEhComissaoMontadorConjunta()){
				if(comissao.getEhComissaoConjunta()){
					comissao.setUsuarioComissaoConjunta("");
				}
				if(comissao.getEhComissaoMontadorConjunta()){
					comissao.setUsuarioComissaoMontadorConjunta("");
				}
				for(Iterator<String> iterUsuarioComissaoConjunta = listaUsuarioComissaoConjunta.iterator(); iterUsuarioComissaoConjunta.hasNext();){
					String idUsuario = iterUsuarioComissaoConjunta.next();
					if(comissao.getUsuario().getPerfil().getId() == 5){						
						if(comissao.getUsuarioComissaoMontadorConjunta() == ""){
							comissao.setUsuarioComissaoMontadorConjunta(idUsuario);
						}else{
							comissao.setUsuarioComissaoMontadorConjunta(comissao.getUsuarioComissaoMontadorConjunta() + "," + idUsuario);
						}
					}
					if(comissao.getUsuario().getPerfil().getId() == 2){
						if(comissao.getUsuarioComissaoConjunta() == ""){
							comissao.setUsuarioComissaoConjunta(idUsuario);
						}else{
							comissao.setUsuarioComissaoConjunta(comissao.getUsuarioComissaoConjunta() + "," + idUsuario);
						}
					}					
				}
			}
			comissaoDAO.update(comissao);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Comissão usuário: " + comissao.getUsuario().getNome() + " alterado!"));
			logger.info("Salvo comissao usuário: " + comissao.getUsuario().toString());
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate: " + super.mensagem + "-" + comissao.getUsuario().getNome());
		}		
	}
	
	public String sairAlteracao(){
		return "manutencaousuario?faces-redirect=true";
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

	public List<String> getListaUsuarioComissaoConjunta() {
		return listaUsuarioComissaoConjunta;
	}

	public void setListaUsuarioComissaoConjunta(
			List<String> listaUsuarioComissaoConjunta) {
		this.listaUsuarioComissaoConjunta = listaUsuarioComissaoConjunta;
	}
	
	public List getListaUsuarioCombo() {
		listaUsuarioCombo = new ArrayList();
		List<Usuario> listaUsuario;
		try {
			listaUsuario = new UsuarioDAO().listaAtivos();
			for (Usuario usuarios : listaUsuario) {
				if(usuarios.getPerfil().getId() == comissao.getUsuario().getPerfil().getId()
						&& usuarios.getId() != comissao.getUsuario().getId()){
					 SelectItem si = new SelectItem();
		             si.setValue(usuarios.getId());
		             si.setLabel(usuarios.getNome());
		             listaUsuarioCombo.add(si);
				}            
	         }
		} catch (ConstraintViolationException e) {
			mensagem = e.getMessage();
			logger.error("Erro Constraint: " + super.mensagem);
		} catch (HibernateException e) {
			mensagem = e.getMessage();
			logger.error("Erro Constraint: " + super.mensagem);
		} catch (Exception e) {
			mensagem = e.getMessage();
			logger.error("Erro Constraint: " + super.mensagem);
		}		
		 return listaUsuarioCombo;
	}

	public void setListaUsuarioCombo(List listaUsuarioCombo) {
		this.listaUsuarioCombo = listaUsuarioCombo;
	}
	
}
