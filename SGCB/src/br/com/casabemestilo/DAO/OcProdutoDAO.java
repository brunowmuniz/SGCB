package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.util.Conexao;

public class OcProdutoDAO implements InterfaceDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Ocproduto ocproduto;
	
	private List<Ocproduto> listaOcproduto;

	
	/*
	 * CONSTRUTORES
	 * */
	public OcProdutoDAO(Ocproduto ocproduto, List<Ocproduto> listaOcproduto) {
		super();
		this.ocproduto = ocproduto;
		this.listaOcproduto = listaOcproduto;
	}

	public OcProdutoDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		ocproduto = (Ocproduto) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.save(ocproduto);		
		session.getTransaction().commit();

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		ocproduto = (Ocproduto) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(ocproduto);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		ocproduto = (Ocproduto) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.delete(ocproduto);
		session.getTransaction().commit();
	}

	@Override
	public Ocproduto buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ocproduto> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Ocproduto> listaProdutosAEncomendarFornecedor(int first,
			int pageSize, Fornecedor fornecedor) {
		listaOcproduto = new ArrayList<Ocproduto>();
		session = Conexao.getInstance();
		session.beginTransaction();
		listaOcproduto = session.createQuery("from Ocproduto op" +
												" where " +
													" op.status.id = :encomenda" +
												" and" +
													" op.produto.fornecedor.id = :fornecedor")
								.setInteger("encomenda", 3)
								.setInteger("fornecedor", fornecedor.getId())
								.setFirstResult(first)
								.setMaxResults(pageSize)
								.setCacheable(true)
								.list();
		session.close();
		return listaOcproduto;
		
	}

	public int totalProdutosAEncomendarFornecedor(Fornecedor fornecedor) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		linhas = (Long) session.createQuery("select count(*) from Ocproduto op " +
														"where op.status.id>= :encomenda " +
														"and op.produto.fornecedor.id = :fornecedor")
						.setInteger("encomenda", 3)
						.setInteger("fornecedor", fornecedor.getId())
						.setCacheable(true)
						.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}

	public List<Ocproduto> listaProdutosAEncomendarTodos(int first, int pageSize) {
		listaOcproduto = new ArrayList<Ocproduto>();
		session = Conexao.getInstance();
		session.beginTransaction();
		listaOcproduto = session.createQuery("from Ocproduto op" +
												" where " +
													" op.status.id = :encomenda")
								.setInteger("encomenda", 3)
								.setFirstResult(first)
								.setMaxResults(pageSize)
								.setCacheable(true)
								.list();
		session.close();
		return listaOcproduto;
	}

	public int totalProdutosAEncomendarTodos() {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		linhas = (Long) session.createQuery("select count(*) from Ocproduto op " +
														"where op.status.id = :encomenda")
						.setInteger("encomenda", 3)
						.setCacheable(true)
						.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public List<Ocproduto> buscaOcProdutoOc(Ocproduto ocproduto) {
		listaOcproduto = new ArrayList<Ocproduto>();
		session = Conexao.getInstance();
		session.beginTransaction();
		listaOcproduto = session.createQuery("from Ocproduto op where op.oc.id= :oc" +
											 " and op.id <> :ocprod")
								.setInteger("oc", ocproduto.getOc().getId())
								.setInteger("ocprod", ocproduto.getId())
								.setCacheable(true)
								.list();
		session.close();
		return listaOcproduto;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Ocproduto getOcproduto() {
		return ocproduto;
	}

	public void setOcproduto(Ocproduto ocproduto) {
		this.ocproduto = ocproduto;
	}

	public List<Ocproduto> getListaOcproduto() {
		return listaOcproduto;
	}

	public void setListaOcproduto(List<Ocproduto> listaOcproduto) {
		this.listaOcproduto = listaOcproduto;
	}


	
}
