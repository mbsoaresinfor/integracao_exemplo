package br.com.mbs.servicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.mbs.entidades.Produto;

@Service
public class ProdutoServico {

	private Map<Float,Produto> mapaProduto = new HashMap();
	private static Float contadorId = 0f;
	
	
	public Float salvarProduto(Produto produto) {
		produto.setId(++contadorId);
		mapaProduto.put(produto.getId(),produto);
		return produto.getId();
	}
	
	public Produto getProduto(Float key) {
		return mapaProduto.get(key);
	}
	
	public List<Produto> getLista(){
		return new ArrayList<Produto>(mapaProduto.values());
	}
	
	public boolean existeChave(Float key) {
		return mapaProduto.containsKey(key);
	}
}
