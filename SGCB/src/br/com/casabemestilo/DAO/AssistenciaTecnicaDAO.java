package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Assistenciatecnica;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Perfil;
import br.com.casabemestilo.util.Conexao;

public class AssistenciaTecnicaDAO implements Serializable, InterfaceDAO{

	
	private static final long serialVersionUID = -1456368539598737653L;

	Session session;
	
	private Assistenciatecnica assistenciatecnica;
	
	private List<Assistenciatecnica> listaAssistenciatecnicas;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public AssistenciaTecnicaDAO(Assistenciatecnica assistenciatecnica,
			List<Assistenciatecnica> listaAssistenciatecnicas) {
		super();
		this.assistenciatecnica = assistenciatecnica;
		this.listaAssistenciatecnicas = listaAssistenciatecnicas;
	}
	
	public AssistenciaTecnicaDAO() {
		super();
	}



	/*
	 * M�TODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException, ConstraintViolationException {
		Assistenciatecnica assistenciatecnica = (Assistenciatecnica) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.save(assistenciatecnica);
		session.getTransaction().commit();		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException, ConstraintViolationException{
		assistenciatecnica = (Assistenciatecnica) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(assistenciatecnica);
		session.getTransaction().commit();		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException, ConstraintViolationException{
		assistenciatecnica = (Assistenciatecnica) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update Assistenciatecnica at set at.deleted = :deleted where at.id=:id")
			   .setBoolean("deleted", true)
			   .setInteger("id", assistenciatecnica.getId())
			   .executeUpdate();
		session.getTransaction().commit();
		
	}

	@Override
	public Assistenciatecnica buscaObjetoId(Integer id) throws Exception, HibernateException, ConstraintViolationException{
		session = Conexao.getInstance();
		session.beginTransaction();
		assistenciatecnica = (Assistenciatecnica) session.createQuery("from Assistenciatecnica at where at.id= :id")
							 				 			 .setInteger("id", id)
							 				 			 .uniqueResult();
		session.close();
		return assistenciatecnica;
	}

	@Override
	public List<Assistenciatecnica> listaTodos() throws Exception, HibernateException, ConstraintViolationException{
		session = Conexao.getInstance();
		session.beginTransaction();
		listaAssistenciatecnicas = session.createQuery("from Assistenciatecnica").list();
		session.close();
		return listaAssistenciatecnicas;		
	}

	@Override
	public List<Assistenciatecnica> listaAtivos() throws Exception, HibernateException, ConstraintViolationException{
		session = Conexao.getInstance();
		session.beginTransaction();
		listaAssistenciatecnicas = session.createQuery( "from Assistenciatecnica at where at.deleted=0").list();
		session.close();
		return listaAssistenciatecnicas;
	}

	@Override
	public List<Assistenciatecnica> listaSelecao(Object obj) throws Exception, HibernateException, ConstraintViolationException{
		return null;
	}

	public Assistenciatecnica insertAssistenciaTecnica(Assistenciatecnica assistenciaTecnica) {
		session = Conexao.getInstance();
		session.beginTransaction();
		assistenciatecnica = (Assistenciatecnica) session.merge(assistenciaTecnica);
		session.getTransaction().commit();
		return assistenciatecnica;
	}
	
	public List<Assistenciatecnica> listaAssistenciaTecnica(int first,
			int pageSize, Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		listaAssistenciatecnicas = new ArrayList<Assistenciatecnica>();
		listaAssistenciatecnicas = session.createQuery("from Assistenciatecnica at" +
															" inner join fetch at.ocprodutos" +
														" where " +
															" at.datainicio between :periodoInicial and :periodoFinal" +
														" order by at.id desc")
										.setDate("periodoInicial", dataInicial)
										.setDate("periodoFinal", dataFinal)
										.setFirstResult(first)
										.setMaxResults(pageSize)
										.setCacheable(true)
										.list();
		
		session.close();
		return listaAssistenciatecnicas;
	}

	public int totalAsssitenciaTecnica(Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		linhas = (Long) session.createQuery("select count(*) " +
												" from Assistenciatecnica at " +
												" where at.datainicio " +
													" between :periodoInicial " +
												" and " +
													":periodoFinal")
									.setDate("periodoInicial", dataInicial)
									.setDate("periodoFinal", dataFinal)
									.setCacheable(true)
									.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Assistenciatecnica getAssistenciatecnica() {
		return assistenciatecnica;
	}

	public void setAssistenciatecnica(Assistenciatecnica assistenciatecnica) {
		this.assistenciatecnica = assistenciatecnica;
	}

	public List<Assistenciatecnica> getListaAssistenciatecnicas() {
		return listaAssistenciatecnicas;
	}

	public void setListaAssistenciatecnicas(
			List<Assistenciatecnica> listaAssistenciatecnicas) {
		this.listaAssistenciatecnicas = listaAssistenciatecnicas;
	}

	
}
