package br.com.casabemestilo.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Banco;

@ManagedBean
@ViewScoped
public class BancoControl extends Control implements InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Banco banco;
	
	private List<Banco> bancos = new ArrayList<Banco>();
	
	private BancoDAO bancoDAO;
	
	
	
	/*
	 * CONSTRUTORES
	 * */
	public BancoControl() {
		super();
		
	}

	public BancoControl(String messagem) {
		super(messagem);
		
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void gravar(){	
	
	}
	
	

	@Override
	public void deletar() {
		

	}

	@Override
	public void alterar() {
		

	}

	@Override
	public List<Banco> listarAtivos() {
		
		return null;
	}

	@Override
	public List<Banco> listarTodos() {
		
		return null;
	}

	@Override
	public List<Banco> listaSelecao(Object obj) {
		
		return null;
	}

	@Override
	public Banco buscaObjetoId(Integer id) {		
		return null;
	}
	
	public List listaBancoCombo(String filter){
		List listaBancoCombo = new ArrayList();		
		try {
			bancoDAO = new BancoDAO();
			bancos = bancoDAO.listaAtivos();
			if(!filter.equals("")){
				listaBancoCombo.add(new SelectItem("","Todos"));
			}
			for(Banco banco : bancos){
				SelectItem si = new SelectItem();
				si.setValue(banco.getId());
				si.setLabel(banco.getNumero() + "-" + banco.getDescricao());
				listaBancoCombo.add(si);
			}
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaBancoCombo;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	public BancoDAO getBancoDAO() {
		return bancoDAO;
	}

	public void setBancoDAO(BancoDAO bancoDAO) {
		this.bancoDAO = bancoDAO;
	}
	
	

}
