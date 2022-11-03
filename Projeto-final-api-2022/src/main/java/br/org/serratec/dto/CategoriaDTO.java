package br.org.serratec.dto;

import javax.validation.constraints.NotBlank;

public class CategoriaDTO {

	private Long idCategoria;
	@NotBlank(message = "O nome da categoria não pode ser nulo!")
	private String nome;
	private String descricao;

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
