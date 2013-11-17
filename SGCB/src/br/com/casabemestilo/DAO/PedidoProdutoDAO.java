package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;
import br.com.casabemestilo.util.Conexao;

public class PedidoProdutoDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedidoproduto pedidoProduto;
	
	private List<Pedidoproduto> listaPedidoProduto;
	
	private Session session;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoProdutoDAO(Pedidoproduto pedidoProduto,
			List<Pedidoproduto> listaPedidoProduto) {
		super();
		this.pedidoProduto = pedidoProduto;
		this.listaPedidoProduto = listaPedidoProduto;
	}

	public PedidoProdutoDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Pedidoproduto buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedidoproduto> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Pedidoproduto> listaPedidoProdutoRede(Date dataFinal,
			Date dataInicial, int first, int pageSize) {
		session = Conexao.getInstance();
		listaPedidoProduto = session.createQuery("From Pedidoproduto pp " +
													"where " +
														"pp.pedido.fornecedor.ehrede = 1 " +
													"and " +
														"pp.pedido.datasolicitacao between :dataInicial and :dataFinal" +
													" order by pp.pedido.datasolicitacao desc")
									.setDate("dataInicial", dataInicial)
									.setDate("dataFinal", dataFinal)
									.setFirstResult(first)
									.setMaxResults(pageSize)
									.setCacheable(true)
									.list();
		
		session.close();
		return listaPedidoProduto;
	}

	public int totalPedidoProdutoRede(Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		linhas = (Long) session.createQuery("select count(*) " +
												"from Pedidoproduto pp " +
													"where " +
														"pp.pedido.fornecedor.ehrede = 1 " +
													"and " +
														"pp.pedido.datasolicitacao between :dataInicial and :dataFinal")
									.setDate("dataInicial", dataInicial)
									.setDate("dataFinal", dataFinal)
									.setCacheable(true)
									.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public List<Pedidoproduto> listaPedidoProdutoPorPedido(Integer idPedido){
		session = Conexao.getInstance();
		listaPedidoProduto = session.createQuery("From Pedidoproduto pp" +
													" where pp.pedido.id= :pedido")
									.setInteger("pedido", idPedido)
									.setCacheable(true)
									.list();
		
		session.close();
		return listaPedidoProduto;
	}
	
	public List<Pedidoproduto> listaPedidoFornecedores(Date dataFinal,
			Date dataInicial, int first, int pageSize) {
		session = Conexao.getInstance();
		listaPedidoProduto = session.createQuery("From Pedidoproduto pp " +
													"where " +														
														"pp.pedido.datasolicitacao between :dataInicial and :dataFinal" +
													" order by pp.pedido.datasolicitacao desc")
									.setDate("dataInicial", dataInicial)
									.setDate("dataFinal", dataFinal)
									.setFirstResult(first)
									.setMaxResults(pageSize)
									.setCacheable(true)
									.list();
		
		session.close();
		return listaPedidoProduto;
	}

	public int totalPedidoFornecedores(Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		linhas = (Long) session.createQuery("select count(*) " +
												"from Pedidoproduto pp " +
													"where " +														
														"pp.pedido.datasolicitacao between :dataInicial and :dataFinal")
									.setDate("dataInicial", dataInicial)
									.setDate("dataFinal", dataFinal)
									.setCacheable(true)
									.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public Pedidoproduto buscaPedidoOcProduto(Ocproduto ocproduto){
		session = Conexao.getInstance();
		pedidoProduto = (Pedidoproduto) session.createQuery("from Pedidoproduto pedidoProduto" +
												" where pedidoProduto.ocproduto.id = :ocproduto")
											   .setInteger("ocproduto",ocproduto.getId())
											   .uniqueResult();
		session.close();
		return pedidoProduto;
	}
	
	public void desvicunlarOcProdutoPedido(Pedidoproduto pedidoproduto) {
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(pedidoproduto);
		session.getTransaction().commit();		
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Pedidoproduto getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(Pedidoproduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public List<Pedidoproduto> getListaPedidoProduto() {
		return listaPedidoProduto;
	}

	public void setListaPedidoProduto(List<Pedidoproduto> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}

	
	
}
