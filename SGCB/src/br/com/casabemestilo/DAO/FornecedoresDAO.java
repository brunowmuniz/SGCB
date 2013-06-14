package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.control.FornecedoresControl;
import br.com.casabemestilo.model.Cliente;
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
		// TODO Auto-generated method stub
		return null;
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
		listaFornecedores = session.createQuery("from Fornecedor f where f.deleted=0").list();
		session.close();
		return listaFornecedores;
	}

	@Override
	public List<Fornecedor> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
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
