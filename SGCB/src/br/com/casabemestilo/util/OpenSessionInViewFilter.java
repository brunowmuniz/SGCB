package br.com.casabemestilo.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.casabemestilo.control.Control;

@WebFilter(urlPatterns = "/*")
public class OpenSessionInViewFilter implements Filter  {

	public OpenSessionInViewFilter() {}
	protected Logger logger = Logger.getLogger(OpenSessionInViewFilter.class);

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		Transaction transaction = null;
		try {
			Session session = Conexao.getInstance();
			transaction = session.beginTransaction();
			chain.doFilter(req, res);
			transaction.commit();
			System.out.println("Ok Filter");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro filter");
			logger.error(e.getMessage());
		}finally{
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
