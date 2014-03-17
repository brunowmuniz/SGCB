package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Frete;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.util.Conexao;

public class FreteDAO implements InterfaceDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private Frete frete;
	
	private List<Frete> listaFrete;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public FreteDAO(Frete frete, List<Frete> listaFrete) {
		super();
		this.frete = frete;
		this.listaFrete = listaFrete;
	}

	public FreteDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		frete = (Frete) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(frete);
		session.getTransaction().commit();
	}
	
	public Frete insertFrete(Object obj) throws Exception, HibernateException,
	ConstraintViolationException {
		frete = (Frete) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		frete = (Frete) session.merge(frete);
		session.getTransaction().commit();
		return frete;
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		frete = (Frete) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.merge(frete);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Frete buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		frete = (Frete) session.get(Frete.class, id);
		session.close();
		return frete;
	}

	@Override
	public List<Frete> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frete> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Frete> listaFrete(int first, int pageSize, Date dataInicial,
			Date dataFinal) {
		session = Conexao.getInstance();
		listaFrete = session.createQuery("from Frete f " +
											" inner join fetch f.ocprodutos " +
											" where f.datainicio between :periodoInicial and :periodoFinal" +
											" order by f.id desc")
							.setDate("periodoInicial", dataInicial)
							.setDate("periodoFinal", dataFinal)
							.setFirstResult(first)
							.setMaxResults(pageSize)
							.setCacheable(true)
							.list();
		session.close();
		return listaFrete;
	}

	public int totalFrete(Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		linhas = (Long) session.createQuery("select count(*) " +
												"from Frete f " +
												"where f.datainicio " +
													"between :periodoInicial " +
												"and " +
													":periodoFinal")
									.setDate("periodoInicial", dataInicial)
									.setDate("periodoFinal", dataFinal)
									.setCacheable(true)
									.uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public List<Ocproduto> buscaOcProdutoFrete(Integer idFrete) {
		List<Ocproduto> listaOcprodutosFrete = new ArrayList<Ocproduto>();
		session = Conexao.getInstance();
		listaOcprodutosFrete = session.createQuery("from Ocproduto op" +
													" where op.frete.id = :frete")
									  .setInteger("frete", idFrete)
									  .setCacheable(true)
									  .list();
		session.close();
		return listaOcprodutosFrete;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Frete getFrete() {
		return frete;
	}

	public void setFrete(Frete frete) {
		this.frete = frete;
	}

	public List<Frete> getListaFrete() {
		return listaFrete;
	}

	public void setListaFrete(List<Frete> listaFrete) {
		this.listaFrete = listaFrete;
	}
	
}
