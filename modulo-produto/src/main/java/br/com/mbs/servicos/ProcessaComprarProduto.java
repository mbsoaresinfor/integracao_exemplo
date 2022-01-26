package br.com.mbs.servicos;

import org.springframework.http.ResponseEntity;

public interface ProcessaComprarProduto {

	ResponseEntity<Boolean> processar(Float idProduto,Integer quantidade);
}
