package br.com.mbs.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.mbs.entidades.Produto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController(value="API para manipulacao de estoques")
@Api(description="Api de estoques")
public class EstoqueController {

	
	private Map<Float,Integer> mapaEstoque = new HashMap();// chaveProduto,quantidade
	 
	 
	 @ApiOperation(value = "Verifica se existe um produto no estoque",response=Produto.class)
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso na verificacao do estoque"),
			    @ApiResponse(code = 204, message = "Produto nao encontrado"),
			})
	 @RequestMapping(value = "/verificar-estoque", method = RequestMethod.GET, produces="application/json")	 
	  public ResponseEntity<Boolean> verificarEstoque(Float  idProduto,Integer quantidade) throws Exception {		 
		 System.out.println("Processando verificarEstoque");
		 if(mapaEstoque.containsKey(idProduto)) { 
			 return  new ResponseEntity<>(processaVerificarEstoque(idProduto,quantidade),HttpStatus.OK);	 
		 }else {
			 return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 
	  }
	 	 
	 
	 @ApiOperation(value = "Atualiza um produto no estoque",response=Produto.class)
	 @ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Sucesso na atualizacao do estoque")			    
			})
	 @RequestMapping(value = "/atualizar-estoque", method = RequestMethod.GET, produces="application/json")	 
	  public ResponseEntity<Boolean> atualizarEstoque(Float  idProduto,Integer quantidade) throws Exception {		 
		 System.out.println("Processando atualizarEstoque");
		 if(mapaEstoque.containsKey(idProduto)) {
			 Integer quantidadeAtual = mapaEstoque.get(idProduto);
			 Integer novaQuantidade = quantidadeAtual - quantidade;
			 mapaEstoque.put(idProduto, novaQuantidade < 0 ? quantidadeAtual : novaQuantidade);
		 }else {
			 mapaEstoque.put(idProduto, quantidade);
		 }
		 return  new ResponseEntity<>(true,HttpStatus.OK);	 
		 
	  }
	 
	 private boolean processaVerificarEstoque(Float  idProduto,Integer quantidade) {		 		 
		 boolean retorno = false;
		 if(quantidade != 0) {
			 Integer quantidadeAtual = mapaEstoque.get(idProduto);
			retorno = (quantidade - quantidadeAtual) <= 0;	  
		 }
		 return retorno;
		 
	 }
	 
	


}
