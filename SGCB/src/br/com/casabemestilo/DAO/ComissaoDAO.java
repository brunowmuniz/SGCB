package br.com.casabemestilo.DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.DAO.Impl.InterfaceDAO;
import br.com.casabemestilo.model.Comissao;
import br.com.casabemestilo.model.Usuario;
import br.com.casabemestilo.util.Conexao;

public class ComissaoDAO implements Serializable, InterfaceDAO {

	
	private static final long serialVersionUID = 1L;
	
	Session session;
	
	private List<Comissao> listaComissao;
	
	private Comissao comissao;	
	
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ComissaoDAO(List<Comissao> listaComissao,
			Comissao comissao) {
		super();
		this.listaComissao = listaComissao;
		this.comissao = comissao;
	}
	
	
	public ComissaoDAO() {
		super();
	}


	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws HibernateException,
			ConstraintViolationException {
		comissao = (Comissao) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(comissao);
		session.getTransaction().commit();
	}

	@Override
	public void update(Object obj) throws HibernateException,
			ConstraintViolationException {
		comissao = (Comissao) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(comissao);
		session.getTransaction().commit();

	}

	@Override
	public void delete(Object obj) throws HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Comissao buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		comissao = (Comissao) session.createQuery("from Comissao c where c.usuario.id= :id")
							 		 .setInteger("id", id)
							 		 .uniqueResult();
		session.close();
		return comissao;
	}

	@Override
	public List<Comissao> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comissao> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Comissao buscaComissaoUsuario(Usuario usuario){		
		session = Conexao.getInstance();
		session.beginTransaction();
		comissao = null;
		listaComissao = session.createQuery("from Comissao c where deleted = 0").list();
		for(Comissao comissaoUsuario: listaComissao){
			if(comissaoUsuario.getUsuario().getId() == usuario.getId()){
				comissao = comissaoUsuario;
			}
		}
		
		if(comissao == null){
			for(Comissao comissaoUsuario: listaComissao){
				String[] idUsuarioComissaoConjunta = comissaoUsuario.getUsuarioComissaoConjunta().split(",");
				for(int i = 0; i < idUsuarioComissaoConjunta.length || comissao != null; i++){
					if (Integer.parseInt(idUsuarioComissaoConjunta[i]) == usuario.getId()){
						comissao = comissaoUsuario;
					}
				}
			}
		}
		
		session.close();
		return comissao;
		
	}

	
	/*
	 * GETTERS & SETTERS
	 * */
	public List<Comissao> getListaComissao() {
		return listaComissao;
	}


	public void setListaComissao(List<Comissao> listaComissao) {
		this.listaComissao = listaComissao;
	}


	public Comissao getComissao() {
		return comissao;
	}


	public void setComissao(Comissao comissao) {
		this.comissao = comissao;
	}
	
}
