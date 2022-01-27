package br.com.mbs.servicos;

import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;


public class ProcessaComprarProdutoPorCamundaBPM implements ProcessaComprarProduto {

	private RuntimeService runtimeService;
	 
	
	public ProcessaComprarProdutoPorCamundaBPM(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	
	@Override
	public void processar(Float idProduto, Integer quantidade) {
		Map<String,Object> valores = new HashMap<String, Object>();
		valores.put(BpmConstantes.ID_PRODUTO, idProduto);
		valores.put(BpmConstantes.QUANTIDADE_PRODUTO, quantidade);
		runtimeService.startProcessInstanceByKey("comprar-produto",valores);		
		
	}

}
