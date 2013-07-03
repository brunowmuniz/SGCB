package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.sql.Select;
import org.primefaces.event.SelectEvent;

import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Oc;

@ManagedBean
@ViewScoped
public class OcControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private List<Oc> listaOc;
	
	private Oc oc = new Oc();
	
	private OcDAO ocDAO;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public OcControl(String messagem, List<Oc> listaOc, Oc oc, OcDAO ocDAO) {
		super(messagem);
		this.listaOc = listaOc;
		this.oc = oc;
		this.ocDAO = ocDAO;
	}

	public OcControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public OcControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * M�TODOS
	 * */
	public void defineClienteBuscaOC(SelectEvent event){
		oc.setCliente((Cliente) event.getObject());
	}
	
	public void limparCliente(){
		oc.setCliente(new Cliente());
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
	public List<Oc> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Oc> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Oc> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Oc buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Oc> getListaOc() {
		return listaOc;
	}

	public void setListaOc(List<Oc> listaOc) {
		this.listaOc = listaOc;
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public Oc getOc() {
		return oc;
	}

	public void setOc(Oc oc) {
		this.oc = oc;
	}

	public OcDAO getOcDAO() {
		return ocDAO;
	}

	public void setOcDAO(OcDAO ocDAO) {
		this.ocDAO = ocDAO;
	}

}
