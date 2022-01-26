package br.com.mbs.entidades;

import java.util.Date;

public class Produto {

	private Float id;
	private String nome;
	private Float preco;
	private Date dataCadastro;
	
	public Float getId() {
		return id;
	}
	public void setId(Float id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Float getPreco() {
		return preco;
	}
	public void setPreco(Float preco) {
		this.preco = preco;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	
}
