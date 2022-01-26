package br.com.mbs.servicos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.mbs.repositorio.EstoqueRepositorio;

public class ProcessaComprarProdutoPorFeign implements ProcessaComprarProduto {

	private EstoqueRepositorio estoqueRepositorio;
	
	public ProcessaComprarProdutoPorFeign(EstoqueRepositorio estoqueRepositorio) {
		this.estoqueRepositorio = estoqueRepositorio;
	}
	
	@Override
	public ResponseEntity<Boolean> processar(Float idProduto, Integer quantidade) {

		ResponseEntity<Boolean> retorno = null;
	 
		 retorno= estoqueRepositorio.verificarEstoque(idProduto, quantidade);
		 boolean temEstoque = retorno.getStatusCode() == HttpStatus.OK && retorno.getBody();
		 if(temEstoque) {
			 retorno = estoqueRepositorio.atualizarEstoque(idProduto, quantidade);
		 }	
		 
		 return retorno;
		
	}

}
