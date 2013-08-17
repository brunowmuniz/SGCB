package br.com.casabemestilo.util;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



public class Conexao {
	
	private static SessionFactory sessionFactory = configureSessionFactory();
	private static ServiceRegistry serviceRegistry;

	
	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
	 
	public static Session getInstance(){	   
	   Session session = sessionFactory.openSession();
	   session.setFlushMode(FlushMode.COMMIT);
	   session.setCacheMode(CacheMode.NORMAL);
	   return session;
	}
	
}
