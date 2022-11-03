package br.org.serratec.dto;

import br.org.serratec.model.Endereco;

public class EnderecoResponseDTO {

	private Long idEndereco;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private Integer numero;
	private String complemento;
	private String uf;

	public EnderecoResponseDTO() {
	}

	public EnderecoResponseDTO(Endereco endereco) {
		this.cep = endereco.getCep();
		this.rua = endereco.getRua();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cidade = endereco.getCidade();
		this.uf = endereco.getUf();
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereço) {
		this.idEndereco = idEndereço;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Endereco: \n" + "Rua: " + rua + "\n Número: " + numero + "\n Bairro: " + bairro + "\n Complemento:"
				+ complemento + "\n Cidade: " + cidade + "\n UF:" + uf + "\n CEP: " + cep + "]";
	}

}