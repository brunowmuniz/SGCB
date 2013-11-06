package br.com.casabemestilo.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.AssistenciaTecnicaDAO;
import br.com.casabemestilo.DAO.FreteDAO;
import br.com.casabemestilo.DAO.PerfilDAO;
import br.com.casabemestilo.DAO.UsuarioDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.ConnectionFactory;

@ManagedBean
@ViewScoped
public class AssistenciaTecnicaControl extends Control implements Serializable,InterfaceControl {

	
	private static final long serialVersionUID = 1L;

	private Assistenciatecnica assistenciaTecnica;
	
	private List<Assistenciatecnica> listaAssitenciaTecnica;
	
	private AssistenciaTecnicaDAO assistenciaTecnicaDAO;
	
	private List<Integer> listaMontadores = new ArrayList<Integer>();
	
	private LazyDataModel<Assistenciatecnica> listaAssistenciaTecnicaGeral;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	/*
	 * CONSTRUTORES
	 */
	public AssistenciaTecnicaControl(String messagem,
			Assistenciatecnica assistenciaTecnica,
			List<Assistenciatecnica> listaAssitenciaTecnica,
			AssistenciaTecnicaDAO assistenciaTecnicaDAO) {
		super(messagem);
		this.assistenciaTecnica = assistenciaTecnica;
		this.listaAssitenciaTecnica = listaAssitenciaTecnica;
		this.assistenciaTecnicaDAO = assistenciaTecnicaDAO;
	}

	public AssistenciaTecnicaControl() {
		super();
	}

	public AssistenciaTecnicaControl(String messagem) {
		super(messagem);
	}

	
	/*
	 * MÉTODOS
	 */
	 @PostConstruct
	 public void init(){}
		
	    
	 @PreDestroy
	 public void destroy() {}
	
	
	@Override
	public void gravar() {
		try{
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
        	//assistenciaTecnica.setDeleted(false);        	
        	assistenciaTecnicaDAO.insert(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica do produto: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " - da OC:" + assistenciaTecnica.getOcproduto().getOc().getId() +  " foi gravada!"));
        	assistenciaTecnica = new Assistenciatecnica();
    	}catch(TransactionException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Conexão: " + super.mensagem, ""));
    	}
    	catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));		
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}
		
	}

	@Override
	public void deletar() {
		try{
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
    		assistenciaTecnica = this.buscaObjetoId(assistenciaTecnica.getId());
    		//assistenciaTecnica.setDeleted(true);
        	assistenciaTecnicaDAO.delete(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica da OC: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " deletado!"));
        	assistenciaTecnica = new Assistenciatecnica();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));		
    	}catch (HibernateException e) {
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
    		assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
        	assistenciaTecnicaDAO.update(assistenciaTecnica);
        	//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica da OC: " + assistenciaTecnica.getOcproduto().getProduto().getDescricao() + " alterado!"));
        	assistenciaTecnica = new Assistenciatecnica();
    	}catch(ConstraintViolationException e){
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));		
    	}catch (HibernateException e) {
    		super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Exception: " + super.mensagem, ""));
		}		
	}

	@Override
	public List<Assistenciatecnica> listarAtivos() {
		try{
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaAtivos();
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
		return listaAssitenciaTecnica;
	}

	@Override
	public List<Assistenciatecnica> listarTodos() {
		try{
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaTodos();
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
		return listaAssitenciaTecnica;
	}

	@Override
	public List<Assistenciatecnica> listaSelecao(Object obj) {
		try{
			assistenciaTecnica = (Assistenciatecnica) obj;
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
	    	listaAssitenciaTecnica = assistenciaTecnicaDAO.listaSelecao(assistenciaTecnica);
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
		return listaAssitenciaTecnica;
	}

	@Override
	public Assistenciatecnica buscaObjetoId(Integer id) {
		try{
			assistenciaTecnica.setId(id);
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
			assistenciaTecnica = assistenciaTecnicaDAO.buscaObjetoId(assistenciaTecnica.getId());
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
		return assistenciaTecnica;
	}

	public Assistenciatecnica gravarAssistTecnicaProduto(List<Ocproduto> listaOcProdutos, List<Integer> listaMontadores, String observacoes) {
		
		try {
			assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
			assistenciaTecnica = new Assistenciatecnica();
			assistenciaTecnica.setDatainicio(new Date());
			assistenciaTecnica.setDatafim(new Date());
			assistenciaTecnica.setOcprodutos(listaOcProdutos);
			assistenciaTecnica.setObservacoes(observacoes);			
			for (int i = 0; i < listaMontadores.size(); i++) {				
				assistenciaTecnica.setMontador(assistenciaTecnica.getMontador().concat((String) (i==0 ? listaMontadores.get(i) : "," + listaMontadores.get(i))));
			}
			assistenciaTecnica = assistenciaTecnicaDAO.insertAssistenciaTecnica(assistenciaTecnica);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Assistência Técnica gerada para os produtos!"));
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return assistenciaTecnica;
	}
	
	public LazyDataModel<Assistenciatecnica> getListaATGeralAll(){
		if(listaAssistenciaTecnicaGeral == null){
			listaAssistenciaTecnicaGeral = new LazyDataModel<Assistenciatecnica>() {
				private List<Assistenciatecnica> listaLazyAssistenciaTecnica;
				 
				 public Assistenciatecnica getRowData(String idAssistenciaTecnica) {
				    	Integer id = Integer.valueOf(idAssistenciaTecnica);
				    	
				        for(Assistenciatecnica assistenciatecnica : listaLazyAssistenciaTecnica) {
				            if(assistenciatecnica.getId().equals(id))
				                return assistenciatecnica;
				        }
				        
				        return null;
				    }

				    @Override
				    public Object getRowKey(Assistenciatecnica assistenciatecnica) {
				        return assistenciatecnica.getId();
				    }

				    @Override
				    public List<Assistenciatecnica> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
				    	assistenciaTecnicaDAO = new AssistenciaTecnicaDAO();
				    	listaLazyAssistenciaTecnica = assistenciaTecnicaDAO.listaAssistenciaTecnica(first, pageSize, getDataInicial(),getDataFinal());
				    	if (getRowCount() <= 0) {  
				            setRowCount(assistenciaTecnicaDAO.totalAsssitenciaTecnica(getDataInicial(),getDataFinal()));
				        }
				        setPageSize(pageSize);  
				        return listaLazyAssistenciaTecnica;  
				    }
			};
		}
		return listaAssistenciaTecnicaGeral;
	}
	
	public void impressaoAT(Assistenciatecnica assistenciatecnica){
		Connection conexaoFactory = null;		
		try {
			conexaoFactory = new ConnectionFactory().getConexao();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			Map<String, Object> parametros = new HashMap<String, Object>();
			InputStream caminho = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("UsuarioLogado");
			String montadores = "";
			String caminhoRelatorio = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/br/com/casabemestilo/relatorio/");
			
			for(int i = 0; i < assistenciatecnica.getMontador().split(",").length; i++){
				Usuario usuario = new UsuarioDAO().buscaObjetoId(Integer.parseInt(assistenciatecnica.getMontador().split(",")[i]));
				if (i != 0){
					montadores += "; ";
				}
				montadores += usuario.getNome();
			}
			
			if(caminhoRelatorio.indexOf("home") > -1)
				caminhoRelatorio += "/";
			else
				caminhoRelatorio += "\\";
					
			caminho = getClass().getResourceAsStream("../relatorio/solicitacaoat.jrxml");
			response.setContentType("application/pdf");			
			parametros.put("IDAT", assistenciatecnica.getId());
			parametros.put("DATAHORA_IMPRESSAO", new Date());
			parametros.put("USUARIO_IMPRESSAO", usuarioLogado.getNome());
			parametros.put("MONTADOR", montadores);
			parametros.put("SUBREPORT_DIR", caminhoRelatorio);
			response.setHeader("Content-Disposition","attachment; filename=\"Solicitação_Assistencia_Tecnica-" + assistenciatecnica.getId() +".pdf\"");
			JasperReport pathReport = JasperCompileManager.compileReport(caminho);
			
			
			JasperPrint preencher = JasperFillManager.fillReport(pathReport,parametros,conexaoFactory);			
			JasperExportManager.exportReportToPdfStream(preencher, response.getOutputStream());
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.renderResponse();
			context.responseComplete();
		} catch (IOException e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro IO: " + super.mensagem, ""));
			e.printStackTrace();
		} catch(JRException e){
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro JasperReport: " + super.mensagem, ""));
			e.printStackTrace();
		}catch (Exception e) {
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Genérico: " + super.mensagem, ""));
			e.printStackTrace();
		}finally{
			try {
				conexaoFactory.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Assistenciatecnica getAssistenciaTecnica() {
		if(assistenciaTecnica == null){
			assistenciaTecnica = new Assistenciatecnica();
		}
		return assistenciaTecnica;
	}

	public void setAssistenciaTecnica(Assistenciatecnica assistenciaTecnica) {
		this.assistenciaTecnica = assistenciaTecnica;
	}

	
	public List<Assistenciatecnica> getListaAssitenciaTecnica() {
		return listaAssitenciaTecnica;
	}

	public void setListaAssitenciaTecnica(
			List<Assistenciatecnica> listaAssitenciaTecnica) {
		this.listaAssitenciaTecnica = listaAssitenciaTecnica;
	}

	
	public AssistenciaTecnicaDAO getAssistenciaTecnicaDAO() {
		return assistenciaTecnicaDAO;
	}

	public void setAssistenciaTecnicaDAO(AssistenciaTecnicaDAO assistenciaTecnicaDAO) {
		this.assistenciaTecnicaDAO = assistenciaTecnicaDAO;
	}

	public List<Integer> getListaMontadores() {
		return listaMontadores;
	}

	public void setListaMontadores(List<Integer> listaMontadores) {
		this.listaMontadores = listaMontadores;
	}

	public Date getDataInicial() {
		if(dataInicial == null){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -1);
			dataInicial = c.getTime();
		}
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		if(dataFinal == null){
			dataFinal = new Date();
		}
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LazyDataModel<Assistenciatecnica> getListaAssistenciaTecnicaGeral() {
		return listaAssistenciaTecnicaGeral;
	}

	public void setListaAssistenciaTecnicaGeral(
			LazyDataModel<Assistenciatecnica> listaAssistenciaTecnicaGeral) {
		this.listaAssistenciaTecnicaGeral = listaAssistenciaTecnicaGeral;
	}
	
	

}
