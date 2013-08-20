package br.com.casabemestilo.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.faces.context.flash.ELFlash;

import br.com.casabemestilo.DAO.FornecedoresDAO;
import br.com.casabemestilo.DAO.OcProdutoDAO;
import br.com.casabemestilo.DAO.ParcelaDAO;
import br.com.casabemestilo.DAO.PedidoDAO;
import br.com.casabemestilo.DAO.ProdutoDAO;
import br.com.casabemestilo.control.Impl.InterfaceControl;
import br.com.casabemestilo.model.Fornecedor;
import br.com.casabemestilo.model.Oc;
import br.com.casabemestilo.model.Ocproduto;
import br.com.casabemestilo.model.Parcela;
import br.com.casabemestilo.model.Pedido;
import br.com.casabemestilo.model.Pedidoproduto;
import br.com.casabemestilo.model.Produto;

@ManagedBean
@ViewScoped
public class PedidoControl extends Control implements InterfaceControl,
		Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	
	private List<Pedido> listaPedido;
	
	private PedidoDAO pedidoDAO;
	
	private LazyDataModel<Pedido> listaPedidoGeral;
	
	private Pedidoproduto pedidoproduto;
	
	
	/*
	 * CONSTRUTORES
	 * */
	public PedidoControl(String messagem, Pedido pedido,
			List<Pedido> listaPedido, PedidoDAO pedidoDAO) {
		super(messagem);
		this.pedido = pedido;
		this.listaPedido = listaPedido;
		this.pedidoDAO = pedidoDAO;
	}

	public PedidoControl(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}

	public PedidoControl() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * MÉTODOS
	 * */
	
	@PostConstruct
	public void init(){		
		if(ELFlash.getFlash().get("pedido") != null){
			pedido = (Pedido) ELFlash.getFlash().get("pedido");			
			ELFlash.getFlash().clear();
		}else{
			pedido = new Pedido();
		}
	}
	    
	@PreDestroy
	public void destroy() {}
	
	@Override
	public void gravar() {
		pedidoDAO = new PedidoDAO();
		try {
			pedidoDAO.insert(pedido);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido avulso foi criado!"));
			logger.info("Pedido: " + pedido.getId() + " foi gravado");
		} catch (ConstraintViolationException e) {			
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[gravar_pedido_avulso] Erro Constraint: " + super.mensagem + "-" + "Pedido não foi gravado!");
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[gravar_pedido_avulso] Erro Hibernate: " + super.mensagem + "-" + "Pedido não foi gravado!");
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[gravar_pedido_avulso] Erro Genérico: " + super.mensagem + "-" + "Pedido não foi gravado!");
		}

	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar() {
		pedidoDAO = new PedidoDAO();
		try {
			pedidoDAO.update(pedido);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido " + pedido.getId() + " foi alterado!"));
			logger.info("Pedido: " + pedido.getId() + " foi gravado");
		} catch (ConstraintViolationException e) {			
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem, ""));
			logger.error("[alterar_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " não foi gravado!");
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem, ""));
			logger.error("[alterar_pedido] Erro Hibernate: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " não foi gravado!");
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem, ""));
			logger.error("[alterar_pedido] Erro Genérico: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " não foi gravado!");
		}

	}

	@Override
	public List<Pedido> listarAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pedido> listaSelecao(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pedido buscaObjetoId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyDataModel<Pedido> getListaPedidoGeralAll(){
		if(listaPedidoGeral == null){
			listaPedidoGeral = new LazyDataModel<Pedido>() {
				 private List<Pedido> listaLazyPedido;
				 
				 public Pedido getRowData(String idPedido) {
				    	Integer id = Integer.valueOf(idPedido);
				    	
				        for(Pedido pedido : listaLazyPedido) {
				            if(pedido.getId().equals(id))
				                return pedido;
				        }
				        
				        return null;
				    }

				    @Override
				    public Object getRowKey(Pedido pedido) {
				        return pedido.getId();
				    }

				    @Override
				    public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
				    	PedidoDAO pedidoDAO = new PedidoDAO();
				    	listaLazyPedido = pedidoDAO.listaPedidos(first, pageSize);
				    	if (getRowCount() <= 0) {  
				            setRowCount(pedidoDAO.totalPedidos());  
				        }  
				        // set the page dize  
				        setPageSize(pageSize);  
				        return listaLazyPedido;  
				    }
			};
		}
		return listaPedidoGeral;
	}
	
	public String detalharPedido(){
		ELFlash.getFlash().put("pedido", getPedido());
		return "edicaopedido?faces-redirect=true";
	}
	
	public void limparPedidoProduto(){
		pedidoproduto = new Pedidoproduto();
		pedidoproduto.setProduto(new Produto());
	}
	
	public void limparProduto(){
		pedidoproduto.setProduto(new Produto());
	}
	
	public void adicionarProdutoPedido(){
		pedidoproduto.setPedido(this.getPedido());
		pedidoproduto.setOcproduto(null);
		getPedido().getPedidoprodutos().add(pedidoproduto);
	}
	
	public void gravarAdicionarProduto() throws ConstraintViolationException, HibernateException, Exception{		
		Produto produto = new Produto();
		produto = pedidoproduto.getProduto();
		produto.setDeleted(false);
		produto.setFornecedor(this.getPedido().getFornecedor());
		produto = new ProdutoDAO().gravarProdutoAdicionarOc(produto);
		pedidoproduto.setProduto(produto);
		pedidoproduto.setPedido(pedido);
		pedidoproduto.setOcproduto(null);
		getPedido().getPedidoprodutos().add(pedidoproduto);
		pedidoproduto = new Pedidoproduto();
	}
	
	public List<Produto> buscaProdutoCodigoNomeFornecedor(String busca){
		List<Produto> listaProduto = new ArrayList<Produto>();
		Produto produtoBusca = new Produto();
		produtoBusca.setDescricao(busca);
		produtoBusca.setCodigo(busca);
		produtoBusca.setFornecedor(pedido.getFornecedor());
		listaProduto = new ProdutoDAO().listaProdutoCodigoNomeFornecedor(produtoBusca);
		if(listaProduto.isEmpty()){
			produtoBusca.getFornecedor().setNome("Cadastrar ");
			listaProduto.add(produtoBusca);
		}
		return listaProduto;
	}
	
	public void informaEntregaPedido(){
		pedidoDAO = new PedidoDAO();
		OcProdutoDAO ocProdutoDAO = new OcProdutoDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		try {
			pedido = pedidoDAO.buscaObjetoId(pedido.getId());
			for(Pedidoproduto pedidoproduto : pedido.getPedidoprodutos()){
				if(pedidoproduto.getOcproduto() != null){
					pedidoproduto.getOcproduto().getStatus().setId(5);
					ocProdutoDAO.update(pedidoproduto.getOcproduto());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oc " + pedidoproduto.getOcproduto().getOc().getId() + " teve o produto " + pedidoproduto.getProduto().getDescricao() + " separado com sucesso!"));
				}else{
					Produto produto = new Produto();
					produto = pedidoproduto.getProduto();
					produto.setEstoque(produto.getEstoque() + pedidoproduto.getQuantidade());
					produtoDAO.update(produto);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto " + produto.getDescricao() + " teve " + pedidoproduto.getQuantidade() + " unidades adicionado ao estoque!"));
				}
			}
			pedido.setDatachegada(new Date());
			pedidoDAO.update(pedido);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido " + pedido.getId() + " fechado!"));
			logger.info("Pedido: " + pedido.getId() + " teve a sua chegada informada e os produto separados");
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Constraint: " + super.mensagem + " - produtos não separados!", ""));
			logger.error("[informa_entrega_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " teve erro na separação dos produtos");
		} catch (HibernateException e) {
			e.printStackTrace();
			super.mensagem = e.getMessage();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Hibernate: " + super.mensagem + " - produtos não separados!", ""));
			logger.error("[informa_entrega_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " teve erro na separação dos produtos");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Geral: " + super.mensagem + " - produtos não separados!", ""));
			logger.error("[informa_entrega_pedido] Erro Constraint: " + super.mensagem + "-" + "Pedido: " + pedido.getId() + " teve erro na separação dos produtos");
		}
	}
	
	public String gerarPedidoAvulso(){
		return "edicaopedido?faces-redirect=true";
	}
	
	/*
	 * GETTERS & SETTERS
	 * */
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public PedidoDAO getPedidoDAO() {
		return pedidoDAO;
	}

	public void setPedidoDAO(PedidoDAO pedidoDAO) {
		this.pedidoDAO = pedidoDAO;
	}

	public LazyDataModel<Pedido> getListaPedidoGeral() {
		return listaPedidoGeral;
	}

	public void setListaPedidoGeral(LazyDataModel<Pedido> listaPedidoGeral) {
		this.listaPedidoGeral = listaPedidoGeral;
	}

	public Pedidoproduto getPedidoproduto() {
		if(pedidoproduto == null){
			pedidoproduto = new Pedidoproduto();
		}
		return pedidoproduto;
	}

	public void setPedidoproduto(Pedidoproduto pedidoproduto) {
		this.pedidoproduto = pedidoproduto;
	}

}
