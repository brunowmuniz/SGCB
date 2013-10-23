package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.ComissaoMontador;
import br.com.casabemestilo.util.Conexao;

public class ComissaoMontadorDAO implements InterfaceDAO, Serializable {
	
	Session session;
	
	private List<ComissaoMontador> listaComissaoMontadores = new ArrayList<ComissaoMontador>();
	
	private ComissaoMontador comissaoMontador;
			

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
	
	public List<ComissaoMontador> listaTotalComissaoMontador(Date dataInicial, Date dataFinal){
		session = Conexao.getInstance();		
		listaComissaoMontadores = session.createQuery("select new ComissaoMontador(comissao.id, sum(comissao.valor), comissao.montador) " +
															" from ComissaoMontador comissao" +
														" where " +
																" comissao.freteMontagem.datainicio between" +
																	" :dataInicial" +
																" and" +
																	" :dataFinal" +
														" group by comissao.montador.id")
										 .setDate("dataInicial", dataInicial)
										 .setDate("dataFinal", dataFinal)
										 .list();
		
		session.close();
		return listaComissaoMontadores;
	} 

}
