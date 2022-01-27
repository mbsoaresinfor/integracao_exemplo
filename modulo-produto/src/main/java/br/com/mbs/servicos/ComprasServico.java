package br.com.mbs.servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mbs.entidades.Compras;

@Service
public class ComprasServico {

	private List<Compras> listaCompras = new ArrayList<Compras>();
	
	public void salvarCompras(Compras compras) {
		this.listaCompras.add(compras);
	}
	
	public List<Compras> getLista(){
		return listaCompras;
	}
}
