package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Cliente;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Produto;
import br.com.casabemestilo.util.Conexao;

public class ProdutoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Produto produto;
	
	private List<Produto> listaProduto;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ProdutoDAO(Produto produto, List<Produto> listaProduto) {
		super();
		this.produto = produto;
		this.listaProduto = listaProduto;
	}

	public ProdutoDAO() {
	}

	
	/*
	 * M�TODOS
	 * */
	@Override
	public void insert(Object obj) {
		produto = (Produto) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(this.produto);		
		session.getTransaction().commit();
	}
	
	public String insertProd(Object obj){
		try{
			produto = (Produto) obj;
			session = Conexao.getInstance();		
			session.beginTransaction();
			session.save(this.produto);
			session.getTransaction().commit();
			return "ok";
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
			return e.getMessage();
		}
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		produto = (Produto) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(produto);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		produto = (Produto) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.createQuery("update Produto p set p.deleted= :deleted where p.id= :id")
			   .setBoolean("deleted", true)
			   .setInteger("id", produto.getId())
			   .executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public Produto buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		produto = (Produto) session.createQuery("from Produto p where p.id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return produto;
	}

	@Override
	public List<Produto> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaProduto = session.createQuery("from Produto p where p.deleted=0").list();
		session.close();
		return listaProduto;
	}

	@Override
	public List<Produto> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Produto> listaProdutoCodigoNome(Produto produtoBusca) {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaProduto = session.createQuery("from Produto p where " +
											" p.deleted =0" +
											" and (p.codigo like :codigo" +
											" or p.descricao like :descricao)")
											.setString("codigo","%" + produtoBusca.getCodigo() + "%")
											.setString("descricao","%" + produtoBusca.getDescricao() + "%")
											.list();
		session.close();
		return listaProduto;
	}
	
	public Produto gravarProdutoAdicionarOc(Produto produto) {		
		session = Conexao.getInstance();		
		session.beginTransaction();
		produto.setId((Integer) session.save(produto));		
		session.getTransaction().commit();
		return produto;
	}
	
	public List<Produto> listaProdutoCodigoNomeFornecedor(Produto produtoBusca) {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaProduto = session.createQuery("from Produto p where " +
											" p.deleted =0" +
											" and (p.codigo like :codigo" +
											" or p.descricao like :descricao)" +
											" and p.fornecedor.id = :fornecedor")
											.setString("codigo","%" + produtoBusca.getCodigo() + "%")
											.setString("descricao","%" + produtoBusca.getDescricao() + "%")
											.setInteger("fornecedor", produtoBusca.getFornecedor().getId())
											.list();
		session.close();
		return listaProduto;
	}
	
	public List<Produto> listaLazy(int first, int pageSize,
			Map<String, String> filters) {
		session = Conexao.getInstance();
		listaProduto = new ArrayList<Produto>();
		
		String hql = "from Produto produto" +
						" where produto.deleted = false ";
		
		if(filters.containsKey("descricao")){
			hql += " and produto.descricao like '%" + filters.get("descricao") + "%'";
		}
		if(filters.containsKey("fornecedor.id")){
			hql += " and produto.fornecedor.id = " + filters.get("fornecedor.id");
		}
		
		listaProduto = session.createQuery(hql)
							  .setFirstResult(first)
							  .setMaxResults(pageSize)
							  .setCacheable(true)
							  .list();
		session.close();					  
		return listaProduto;
	}

	public int totalProduto(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		
		String hql = "select count(produto.id) from Produto produto" +
						" where produto.deleted = false ";
		
		if(filters.containsKey("descricao")){
			hql += " and produto.descricao like '%" + filters.get("descricao") + "%'";
		}
		if(filters.containsKey("fornecedor.id")){
			hql += " and produto.fornecedor.id = " + filters.get("fornecedor.id");
		}
		
		linhas = (Long) session.createQuery(hql)
							  .setCacheable(true)
							  .uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}	
	
}
