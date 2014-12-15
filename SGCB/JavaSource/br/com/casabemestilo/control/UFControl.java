package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.DAO.UFDAO;
import br.com.casabemestilo.DAO.UsuarioFilialDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.UF;

@ViewScoped
@ManagedBean
public class UFControl extends Control implements InterfaceControl, Serializable{
	
	
	private static final long serialVersionUID = -4553830286551535883L;

	private List<UF> ufs = new ArrayList<UF>();
	
	private UFDAO ufDAO;
	
	private UF uf;
	
	

	public UFControl(String messagem, List<UF> ufs, UFDAO ufDAO, UF uf) {
		super(messagem);
		this.ufs = ufs;
		this.ufDAO = ufDAO;
		this.uf = uf;
	}
	
	public UFControl() {
		super();
	}
	
	public UFControl(String messagem) {
		super(messagem);
	}



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
	public List<UF> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UF> listarTodos() {
		try{
			ufDAO = new UFDAO();
	    	ufs = ufDAO.listaTodos();
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
		return ufs;
	}

	@Override
	public List<UF> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UF buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List listarUfCombo(){
		List listaUfCombo = new ArrayList();
		listarTodos();
		for(UF uf : ufs){
			SelectItem item = new SelectItem(uf.getId(), uf.getSigla());
			listaUfCombo.add(item);
		}
		return listaUfCombo;
	}

	public List<UF> getUfs() {
		return ufs;
	}

	public void setUfs(List<UF> ufs) {
		this.ufs = ufs;
	}

	public UF getUf() {
		if(uf == null){
			uf = new UF();
		}
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}
	
}
