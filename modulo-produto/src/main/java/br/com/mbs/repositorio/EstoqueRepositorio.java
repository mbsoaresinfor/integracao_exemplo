package br.com.mbs.repositorio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


// url: endereco de onde esta o servico.
@FeignClient(value = "modulo-estoque", url = "http://localhost:9091")
public interface EstoqueRepositorio {

	 @RequestMapping(value = "/verificar-estoque", method = RequestMethod.GET, produces="application/json")	 
	  public ResponseEntity<Boolean> verificarEstoque(@RequestParam("idProduto")  Float idProduto,@RequestParam("quantidade") Integer quantidade) ;
	  
	
	 @RequestMapping(value = "/atualizar-estoque", method = RequestMethod.GET, produces="application/json")	 
	  public ResponseEntity<Boolean>  atualizarEstoque(@RequestParam("idProduto")  Float idProduto,@RequestParam("quantidade") Integer quantidade) ;		 
		 
}
