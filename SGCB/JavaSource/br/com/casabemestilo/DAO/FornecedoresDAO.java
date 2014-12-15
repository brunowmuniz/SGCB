package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.control.FornecedoresControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.util.Conexao;

public class FornecedoresDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private FornecedoresControl fornecedoresControl;
	
	private List<Fornecedor> listaFornecedores;
	
	private Fornecedor fornecedor;
	
	/*
	 * CONSTRUTORES
	 * */
	public FornecedoresDAO(FornecedoresControl fornecedoresControl,
			List<Fornecedor> listaFornecedores, Fornecedor fornecedor) {
		super();
		this.fornecedoresControl = fornecedoresControl;
		this.listaFornecedores = listaFornecedores;
		this.fornecedor = fornecedor;
	}
	
	
	public FornecedoresDAO() {
		super();
		// TODO Auto-generated constructor stub
	}


	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		this.fornecedor = (Fornecedor) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(this.fornecedor);
		session.getTransaction().commit();

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		fornecedor = (Fornecedor) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(fornecedor);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		fornecedor = (Fornecedor) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update Fornecedor f set f.deleted = :deleted where f.id=:id")
			   .setBoolean("deleted", true)
			   .setInteger("id", fornecedor.getId())
			   .executeUpdate();
		session.getTransaction().commit();

	}

	@Override
	public Fornecedor buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		fornecedor = (Fornecedor) session.createQuery("from Fornecedor f where f.id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return fornecedor;
	}

	@Override
	public List<Fornecedor> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fornecedor> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaFornecedores = session.createQuery("from Fornecedor f where f.deleted=0 order by f.nome").list();
		session.close();
		return listaFornecedores;
	}

	@Override
	public List<Fornecedor> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stubs
		return null;
	}
	
	public List<Fornecedor> listaLazy(int first, int pageSize,
			Map<String, String> filters) {
		session = Conexao.getInstance();
		listaFornecedores = new ArrayList<Fornecedor>();
		
		String hql = "from Fornecedor fornecedor" +
			" where fornecedor.deleted = false ";
		
		if(filters.containsKey("nome")){
			hql += " and (fornecedor.nome like '%" + filters.get("nome") + "%' or" +
						  " fornecedor.razaosocial like '%" + filters.get("nome") + "%')";
		}
		
		listaFornecedores = session.createQuery(hql)
				  					.setFirstResult(first)
				  					.setMaxResults(pageSize)
				  					.setCacheable(true)
				  					.list();
		session.close();
		
		return listaFornecedores;
	}


	public int totalFornecedores(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		
		String hql = "select count(fornecedor.id) from Fornecedor fornecedor" +
					 " where fornecedor.deleted = false ";
		
		if(filters.containsKey("nome")){
			hql += " and (fornecedor.nome like '%" + filters.get("nome") + "%' or" +
						  " fornecedor.razaosocial like '%" + filters.get("nome") + "%')";
		}
		
		linhas = (Long) session.createQuery(hql)
							   .setCacheable(true)
							   .uniqueResult();
		
		return linhas.intValue();
	}


	/*
	 * GETTERS & SETTERS
	 * */
	public FornecedoresControl getFornecedores() {
		return fornecedoresControl;
	}


	public void setFornecedores(FornecedoresControl fornecedoresControl) {
		this.fornecedoresControl = fornecedoresControl;
	}


	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}


	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	
}
