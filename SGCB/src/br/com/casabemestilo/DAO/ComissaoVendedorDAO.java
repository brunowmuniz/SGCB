package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.ComissaoVendedor;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.util.Conexao;

public class ComissaoVendedorDAO implements InterfaceDAO, Serializable {

	Session session;
	
	private List<ComissaoVendedor> listaComissaoVendedor;
	
	private ComissaoVendedor comissaoVendedor;
	
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
	public Object buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ComissaoVendedor> listaLazyVendedorAnalitico(int first, int pageSize,Integer idUsuario, Date dataInicial, Date dataFinal) {
		session = Conexao.getInstance();
		listaComissaoVendedor = new ArrayList<ComissaoVendedor>();
		listaComissaoVendedor = session.createQuery("from ComissaoVendedor comissaoVendedor" +
														" where" +											
															" comissaoVendedor.oc.datalancamento between :dataInicial and :dataFinal" +
														" and" +
															" comissaoVendedor.vendedor.id = :usuario" +
														" and" +
															" comissaoVendedor.oc.deleted = 0" +
														" and" +
															" comissaoVendedor.oc.status.id not in(1,2,10)")
								 .setDate("dataInicial", dataInicial)
								 .setDate("dataFinal", dataFinal)
								 .setInteger("usuario", idUsuario)
								 .setFirstResult(first)
								 .setMaxResults(pageSize)
								 .setCacheable(true)
								 .list();
		
		session.close();
		return listaComissaoVendedor;
	}

	public int totalVendedorAnalitico(Integer idUsuario, Date dataInicial, Date dataFinal) {
		Long linhas = new Long(0);
		session = Conexao.getInstance();
		
		linhas = (Long) session.createQuery("select count(comissaoVendedor.id) from ComissaoVendedor comissaoVendedor" +
												" where" +													
													" comissaoVendedor.oc.datalancamento between :dataInicial and :dataFinal" +
												" and" +
													" comissaoVendedor.vendedor.id = :usuario"+
												" and" +
													" comissaoVendedor.oc.deleted = 0" +
												" and" +
													" comissaoVendedor.oc.status.id not in(1,2,10)")
								.setDate("dataInicial", dataInicial)
								.setDate("dataFinal", dataFinal)
								.setInteger("usuario", idUsuario)
								.uniqueResult();
		session.close();
		return linhas.intValue();
	}
	
	public Float calculaComissaoVendedor(Integer idUsuario, Date dataInicial, Date dataFinal) {
		Double totalComissao = new Double(0);
		session = Conexao.getInstance();
		session.beginTransaction();
		
		totalComissao = (Double) session.createQuery("select sum(comissaoVendedor.valor) from ComissaoVendedor comissaoVendedor" +
														" where" +
															" comissaoVendedor.oc.status.id not in (1,2,10)" +
														" and" +
															" comissaoVendedor.oc.datalancamento between :dataInicial and :dataFinal" +
														" and" +
															" comissaoVendedor.vendedor.id = :usuario"+
														" and" +
															" comissaoVendedor.oc.deleted = 0")
										.setDate("dataInicial", dataInicial)
										.setDate("dataFinal", dataFinal)
										.setInteger("usuario", idUsuario)
										.uniqueResult();
		session.close();
		totalComissao = totalComissao == null ? new Double("0") : totalComissao;
		return totalComissao.floatValue();
	}

}
