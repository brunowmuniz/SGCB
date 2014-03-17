package br.com.casabemestilo.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.PaginaDAO;
import br.com.casabemestilo.DAO.PedidoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Pagina;
import br.com.casabemestilo.model.Pedido;

@ManagedBean
@ViewScoped
public class PaginaControl extends Control implements InterfaceControl {

	private Pagina pagina;
	
	private PaginaDAO paginaDAO;
	
	private List<Pagina> paginas;
	
	private LazyDataModel<Pagina> listaPaginaGeral;


	public PaginaControl(String messagem, Pagina pagina, PaginaDAO paginaDAO,
			List<Pagina> paginas) {
		super(messagem);
		this.pagina = pagina;
		this.paginaDAO = paginaDAO;
		this.paginas = paginas;
	}

	public PaginaControl() {
		super();
	}


	public PaginaControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS 
	 * */
	
	@PostConstruct
	public void init(){		
		if(ELFlash.getFlash().get("pagina") != null){
			pagina = (Pagina) ELFlash.getFlash().get("pagina");			
			ELFlash.getFlash().clear();
		}else{
			pagina = new Pagina();
		}
	}
	    
	@PreDestroy
	public void destroy() {}
	
	
	@Override
	public void gravar() {
		paginaDAO = new PaginaDAO();
		try {
			if(pagina.getNomePagina().indexOf(".xhtml") == -1){
				pagina.setNomePagina(pagina.getNomePagina() + ".xhtml");
			}
			paginaDAO.insert(pagina);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Página:" + pagina.getNomePagina() + " foi gravada!"));
			pagina = new Pagina();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint[pagina-gravar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate[pagina-gravar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("Erro Genérico[pagina-gravar]: " + super.mensagem + "-" + pagina.getNomePagina());
		}
	}

	@Override
	public void deletar() {
		paginaDAO = new PaginaDAO();
		try {
			pagina.setDeleted(true);
			paginaDAO.delete(pagina);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Página:" + pagina.getNomePagina() + " foi deletada!"));
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint[pagina-deletar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate[pagina-deletar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("Erro Genérico[pagina-deletar]: " + super.mensagem + "-" + pagina.getNomePagina());
		}		
	}

	@Override
	public void alterar() {
		paginaDAO = new PaginaDAO();
		try {
			if(pagina.getNomePagina().indexOf(".xhtml") == -1){
				pagina.setNomePagina(pagina.getNomePagina() + ".xhtml");
			}
			paginaDAO.update(pagina);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Página:" + pagina.getNomePagina() + " foi alterada!"));
			pagina = new Pagina();
		} catch (ConstraintViolationException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("Erro Constraint[pagina-alterar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (HibernateException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("Erro Hibernate[pagina-alterar]: " + super.mensagem + "-" + pagina.getNomePagina());
		} catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			logger.error("Erro Genérico[pagina-alterar]: " + super.mensagem + "-" + pagina.getNomePagina());
		}		
	}

	@Override
	public List<Pagina> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pagina> listarTodos() {
		paginaDAO = new PaginaDAO();
		try {
			paginas = paginaDAO.listaTodos();
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
		return paginas;
	}

	@Override
	public List<Pagina> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LazyDataModel<Pagina> getListaPaginaGeralAll(){
		if(listaPaginaGeral == null){
			listaPaginaGeral = new LazyDataModel<Pagina>() {
				 private List<Pagina> listaLazyPagina;
				 
				 public Pagina getRowData(String idPagina) {
				    	Integer id = Integer.valueOf(idPagina);
				    	
				        for(Pagina pagina : listaLazyPagina) {
				            if(pagina.getId().equals(id))
				                return pagina;
				        }				        
				        return null;
				    }

				    @Override
				    public Object getRowKey(Pagina pagina) {
				        return pagina.getId();
				    }

				    @Override
				    public List<Pagina> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
				    	PaginaDAO paginaDAO = new PaginaDAO();
				    	listaLazyPagina = paginaDAO.listaPaginas(first, pageSize, filters);
				    	if (getRowCount() <= 0) {  
				            setRowCount(paginaDAO.totalPaginas(filters));  
				        }  
				        // set the page size  
				        setPageSize(pageSize);  
				        return listaLazyPagina;  
				    }
			};
		}
		return listaPaginaGeral;
	}

	public String detalharPagina(){
		ELFlash.getFlash().put("pagina", getPagina());
		return "cadastrapagina?faces-redirect=true";
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Pagina getPagina() {
		if(pagina == null){
			pagina = new Pagina();
		}
		return pagina;
	}


	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}


	public PaginaDAO getPaginaDAO() {
		return paginaDAO;
	}


	public void setPaginaDAO(PaginaDAO paginaDAO) {
		this.paginaDAO = paginaDAO;
	}


	public List<Pagina> getPaginas() {
		return paginas;
	}


	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	public LazyDataModel<Pagina> getListaPaginaGeral() {
		return listaPaginaGeral;
	}

	public void setListaPaginaGeral(LazyDataModel<Pagina> listaPaginaGeral) {
		this.listaPaginaGeral = listaPaginaGeral;
	}

	
	
}
