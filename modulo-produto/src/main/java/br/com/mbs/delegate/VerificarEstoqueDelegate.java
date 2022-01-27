package br.com.mbs.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.mbs.repositorio.EstoqueRepositorio;
import br.com.mbs.servicos.BpmConstantes;

@Component("VerificarEstoqueDelegate")
public class VerificarEstoqueDelegate implements JavaDelegate {

	@Autowired
	private EstoqueRepositorio estoqueRepositorio;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("Processando "+ this.getClass().getSimpleName());
		
		Float idProduto = (Float) execution.getVariable(BpmConstantes.ID_PRODUTO);
		Integer quantidade = (Integer) execution.getVariable(BpmConstantes.QUANTIDADE_PRODUTO);
		
		ResponseEntity<Boolean> retorno = estoqueRepositorio.verificarEstoque(idProduto, quantidade);
		boolean temEstoque = retorno.getStatusCode() == HttpStatus.OK && retorno.getBody();
		execution.setVariable(BpmConstantes.TEM_ESTOQUE,temEstoque);
	}

}
