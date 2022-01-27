package br.com.mbs.delegate;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mbs.entidades.Compras;
import br.com.mbs.servicos.BpmConstantes;
import br.com.mbs.servicos.ComprasServico;
import br.com.mbs.servicos.ProdutoServico;

@Component("SalvarComprasDelegate")
public class SalvarComprasDelegate implements JavaDelegate{

	
	@Autowired
	ProdutoServico produtoServico;
	
	@Autowired 
	ComprasServico comprasServico;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Processando "+ this.getClass().getSimpleName());
		
		Float idProduto = (Float) execution.getVariable(BpmConstantes.ID_PRODUTO);
		Integer quantidade = (Integer) execution.getVariable(BpmConstantes.QUANTIDADE_PRODUTO);
		
		 Compras compra = new Compras();
		 compra.setData(new Date());
		 compra.setProduto(produtoServico.getProduto(idProduto));
		 compra.setQuantidade(quantidade);		 
		 comprasServico.salvarCompras(compra);
	}

}
