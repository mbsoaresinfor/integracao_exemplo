package br.com.mbs.servicos;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.mbs.entidades.Compras;
import br.com.mbs.repositorio.EstoqueRepositorio;

public class ProcessaComprarProdutoPorFeign implements ProcessaComprarProduto {

	private EstoqueRepositorio estoqueRepositorio;
	private ProdutoServico produtoServico;
	private ComprasServico comprasServico;
	
	public ProcessaComprarProdutoPorFeign(EstoqueRepositorio estoqueRepositorio,
			ProdutoServico produtoServico,
			ComprasServico comprasServico) {
		this.estoqueRepositorio = estoqueRepositorio;
		this.produtoServico = produtoServico;
		this.comprasServico = comprasServico;
		
	}
	
	@Override
	public void processar(Float idProduto, Integer quantidade) {

		ResponseEntity<Boolean> retorno = null;
	 
		 retorno= estoqueRepositorio.verificarEstoque(idProduto, quantidade);
		 boolean temEstoque = retorno.getStatusCode() == HttpStatus.OK && retorno.getBody();
		 if(temEstoque) {
			 retorno = estoqueRepositorio.atualizarEstoque(idProduto, quantidade);
			 boolean sucessoAtualizacao = retorno.getStatusCode() == HttpStatus.OK && retorno.getBody();
			 if(sucessoAtualizacao) {
				 Compras compra = new Compras();
				 compra.setData(new Date());
				 compra.setProduto(produtoServico.getProduto(idProduto));
				 compra.setQuantidade(quantidade);		 
				 comprasServico.salvarCompras(compra);			 
			 }
		 }	 
		
	}

}
