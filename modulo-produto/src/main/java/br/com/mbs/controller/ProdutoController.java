package br.com.mbs.controller;



import java.util.Date;
import java.util.List;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.mbs.entidades.Compras;
import br.com.mbs.entidades.Produto;
import br.com.mbs.repositorio.EstoqueRepositorio;
import br.com.mbs.servicos.ComprasServico;
import br.com.mbs.servicos.ProcessaComprarProduto;
import br.com.mbs.servicos.ProcessaComprarProdutoPorCamundaBPM;
import br.com.mbs.servicos.ProcessaComprarProdutoPorFeign;
import br.com.mbs.servicos.ProdutoServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController(value="API para manipulacao de produtos")
@Api(description="Api de produtos")
public class ProdutoController {

	@Autowired
	private EstoqueRepositorio estoqueRepositorio;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired
	private ComprasServico comprasServico;
	
	
	 @ApiOperation(value = "Salva um produto")
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso ao salvar um produto")
			})
	 @RequestMapping(value = "/produto", method = RequestMethod.POST,  produces="text/plain")	 
	 public ResponseEntity<String> salvarProduto(String nome, float preco) throws Exception{		
		System.out.println("Processando salvarProduto");		 
		Produto produto = new Produto();
		produto.setDataCadastro(new Date());		
		produto.setNome(nome);
		produto.setPreco(preco);
		produtoServico.salvarProduto(produto);
		return  new ResponseEntity<>( produto.getId().toString(),HttpStatus.OK);		

	 }
	 
	 
	 @ApiOperation(value = "Retorna um produto",response=Produto.class)
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso no retorno do produto"),
			    @ApiResponse(code = 204, message = "Produto nao encontrado"),
			})
	 @RequestMapping(value = "/produto/{id}", method = RequestMethod.GET, produces="application/json")	 
	  public ResponseEntity<Produto> getUsuario(@PathVariable Float id) throws Exception {		 
		 System.out.println("Processando getUsuario");
		 if(produtoServico.existeChave(id)) {
			 return  new ResponseEntity<>(produtoServico.getProduto(id),HttpStatus.OK);	 
		 }else {
			 return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 
	  }
	 
	 @ApiOperation(value = "Comprar um produto",response=Boolean.class)
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Processada a compra.")			    
			})
	 @RequestMapping(value = "/comprar-produto/", method = RequestMethod.GET)	 
	  public ResponseEntity<Boolean> comprarProduto(Float idProduto,Integer quantidade) throws Exception {		 
		 System.out.println("Processando comprarProduto");
		 
		 
		 if(produtoServico.existeChave(idProduto)) {
			 // *** PROCESSAMENTO POR FEIGN ***
			 ProcessaComprarProduto processaComprarProduto = 
					 new ProcessaComprarProdutoPorFeign(estoqueRepositorio,
							 produtoServico,comprasServico); 
			 
			 // *** PROCESSAMENTO POR CAMUNDA (BPM) ***
//			 ProcessaComprarProduto processaComprarProduto = 
//					 new ProcessaComprarProdutoPorCamundaBPM(runtimeService); 
			 
			 processaComprarProduto.processar(idProduto, quantidade);
			
		 }
		 
		 return ResponseEntity.ok(true) ;		 
		 
	  }
	 
	 @ApiOperation(value = "Processa as compras de um produto",response=Boolean.class)
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso no processamento")			  
			})
	 @RequestMapping(value = "/processar-compras-produtos/", method = RequestMethod.GET)	 
	  public ResponseEntity<Void> processarComprasProdutos() throws Exception {		 
		 System.out.println("Processando processarComprasProdutos");
		 // fazer a integracao
		 return null;
		 
	  }
	 
	 @ApiOperation(value = "Retorna as compras",response=Compras.class,responseContainer="List")
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso no retorno da lista")			   
			})	 
	 @RequestMapping(value = "/compras", method = RequestMethod.GET, produces="application/json")	 
	  public List<Compras> listarCompras() {
		 System.out.println("Processando listarCompras");
		 return comprasServico.getLista();
	  }
	 
	 @ApiOperation(value = "Retorna os produtos",response=Produto.class,responseContainer="List")
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso no retorno da lista")			   
			})	 
	 @RequestMapping(value = "/produto", method = RequestMethod.GET, produces="application/json")	 
	  public List<Produto> listarProdutos() {
		 System.out.println("Processando listarProdutos");
	    return produtoServico.getLista();
	  }


}
