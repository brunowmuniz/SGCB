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
import br.com.casabemestilo.util.Conexao;

public class ClienteDAO implements Serializable,InterfaceDAO{	
	

	private static final long serialVersionUID = 1L;

	Session session;
	
	private Cliente cliente;
	
	private List<Cliente> listaClientes;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public ClienteDAO(Cliente cliente,
			List<Cliente> listaClientes) {
		super();
		this.cliente = cliente;
		this.listaClientes = listaClientes;
	}
	
	public ClienteDAO() {
		super();
	}
	
	
	
	/*
	 * MÉTODOS
	 * */
	@Override
	public void insert(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		cliente = (Cliente) obj;
		session = Conexao.getInstance();		
		session.beginTransaction();
		session.save(cliente);
		session.getTransaction().commit();		
	}

	@Override
	public void update(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		cliente = (Cliente) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(cliente);
		session.getTransaction().commit();
		
	}

	@Override
	public void delete(Object obj) throws Exception, HibernateException,
			ConstraintViolationException {
		cliente = (Cliente) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		session.update(cliente);
		session.getTransaction().commit();
		
	}

	@Override
	public Cliente buscaObjetoId(Integer id) throws Exception,
			HibernateException, ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		cliente = (Cliente) session.createQuery("from Cliente c where c.id= :id")
							 .setInteger("id", id)
							 .uniqueResult();
		session.close();
		return cliente;
	}

	@Override
	public List<Cliente> listaTodos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaClientes = session.createQuery("from Cliente c order by c.nome").setCacheable(true).list();
		session.close();
		return listaClientes;
	}

	@Override
	public List<Cliente> listaAtivos() throws Exception, HibernateException,
			ConstraintViolationException {
		session = Conexao.getInstance();
		session.beginTransaction();
		listaClientes = session.createQuery("from Cliente c where c.deleted=0 order by c.nome").setCacheable(true).list();
		session.close();
		return listaClientes;
	}

	@Override
	public List<Cliente> listaSelecao(Object obj) throws Exception,
			HibernateException, ConstraintViolationException {
		cliente = (Cliente) obj;
		session = Conexao.getInstance();
		session.beginTransaction();
		listaClientes = session.createQuery("from Cliente c where " +
															" c.deleted=:deletado " +
														" and" +
															" c.nome like :desc" +
															" order by c.nome")
							 .setBoolean("deletado", cliente.getDeleted())
							 .setString("desc", "%" + cliente.getNome() + "%")
							 .setCacheable(true)
							 .list();
		session.close();
		return listaClientes;
	}
	
	public List<Cliente> listaLazy(int first, int pageSize, Map<String, String> filters) {
		session = Conexao.getInstance();
		listaClientes = new ArrayList<Cliente>();
		String hql = "from Cliente cliente" +
							" where" +
								" cliente.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and cliente.nome like '%" + filters.get("nome") + "%'";
		}
		
		hql +=" order by cliente.nome";
		
		listaClientes = session.createQuery(hql)
								.setFirstResult(first)
								.setMaxResults(pageSize)
								.setCacheable(true)
								.list();
		
		session.close();
		return listaClientes;
	}

	public int totalUsuario(Map<String, String> filters) {
		session = Conexao.getInstance();
		Long linhas = new Long("0");
		String hql ="select count(cliente.id) from Cliente cliente" +
						" where cliente.deleted = false";
		
		if(filters.containsKey("nome")){
			hql += " and cliente.nome like '%" + filters.get("nome") + "%'";
		}
		
		linhas = (Long) session.createQuery(hql)
							   .setCacheable(true)
							   .uniqueResult();
		
		session.close();
		return linhas.intValue();
	}
	
	public void insertCliente(List<Cliente> listaCliente) {
		session = Conexao.getInstance();
		session.beginTransaction();
		for(Cliente clientes : listaCliente){
			System.out.println(clientes);
			session.save(clientes);			
		}
		session.getTransaction().commit();		
	}
	
	public void updateCliente(List<Cliente> clientes) {
		session = Conexao.getInstance();
		session.beginTransaction();
		int i = 0;
		for(Cliente cliente : clientes){
			session.createQuery("update Cliente cliente set cliente.ie = :ie," +
										  " cliente.cidade = :cidade," +
										  " cliente.bairro = :bairro," +
										  " cliente.endereco = :endereco," +
										  " cliente.cep = :cep," +
										  " cliente.telefone = :telefone," +													  
										  " cliente.telefoneadicional = :telefoneadicional," +
										  " cliente.tipoPessoa = :tipoPessoa," +
										  " cliente.cnpj = :cnpj," +
										  " cliente.cpf = :cpf" +
								" where " +
										  " cliente.id=:id")
					.setString("ie", cliente.getIe())
					.setString("cidade", cliente.getCidade())
					.setString("bairro", cliente.getBairro())
					.setString("endereco", cliente.getEndereco())
					.setString("cep", cliente.getCep())
					.setString("telefone", cliente.getTelefone())
					.setString("telefoneadicional", cliente.getTelefoneadicional())
					.setString("tipoPessoa", cliente.getTipoPessoa())
					.setString("cnpj", cliente.getCnpj())
					.setString("cpf", cliente.getCpf())
					.setInteger("id",cliente.getId())
					.executeUpdate();
			i++;
			if(i == 100){
				i= 0;
				session.getTransaction().commit();
				session = Conexao.getInstance();
				session.beginTransaction();
			}
		}
		session.getTransaction().commit();
	}
	
	public List<Cliente> listaAniversariante(){
		session = Conexao.getInstance();
		listaClientes = session.createQuery("select new Cliente(nome, telefone, telefoneadicional, datadenascimento, email) " +
											"from " +
												"Cliente c " +
											"where " +
												"date_format(c.datadenascimento,'%m-%d') = date_format(now(),'%m-%d')" +
											"order by c.nome")
								.setCacheable(true).list();
		session.close();
		return listaClientes;
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
		
}