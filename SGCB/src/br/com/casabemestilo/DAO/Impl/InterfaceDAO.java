package br.com.casabemestilo.DAO.Impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import br.com.casabemestilo.model.Perfil;

public interface InterfaceDAO {

	public abstract void insert(Object obj) throws Exception, HibernateException, ConstraintViolationException;
	public abstract void update(Object obj) throws Exception, HibernateException, ConstraintViolationException;
	public abstract void delete(Object obj) throws Exception, HibernateException, ConstraintViolationException;
	public abstract Object buscaObjetoId(Integer id) throws Exception, HibernateException, ConstraintViolationException;
	public abstract <T> List<T> listaTodos() throws Exception, HibernateException, ConstraintViolationException;
	public abstract <T> List<T> listaAtivos() throws Exception, HibernateException, ConstraintViolationException;
	public abstract <T> List<T> listaSelecao(Object obj) throws Exception, HibernateException, ConstraintViolationException;
	
}
