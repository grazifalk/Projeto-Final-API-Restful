package br.org.serratec.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.org.serratec.dto.EnderecoDTO;
import br.org.serratec.dto.EnderecoRequestDTO;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Endereco cadastrar(EnderecoRequestDTO enderecoRequestDTO) {
		Endereco endereco;
		if (buscarPorCep(enderecoRequestDTO.getCep().replaceAll("-", "")) != null) {
			endereco = buscarPorCep(enderecoRequestDTO.getCep().replaceAll("-", ""));
			if (endereco.getComplemento().equalsIgnoreCase(enderecoRequestDTO.getComplemento())
					&& endereco.getNumero() == enderecoRequestDTO.getNumero())
				return endereco;
			endereco.setComplemento(enderecoRequestDTO.getComplemento());
			endereco.setNumero(enderecoRequestDTO.getNumero());
			return enderecoRepository.save(endereco);
		}

		endereco = buscarNoViacep(enderecoRequestDTO.getCep());
		String cepSemTraco = endereco.getCep().replaceAll("-", "");
		endereco.setCep(cepSemTraco);
		endereco.setComplemento(enderecoRequestDTO.getComplemento());
		endereco.setNumero(enderecoRequestDTO.getNumero());
		return enderecoRepository.save(endereco);
	}

	public Endereco buscarPorCep(String cep) {
		Optional<Endereco> optEndereco = enderecoRepository.findByCep(cep);
		if (optEndereco.isEmpty())
			return null;
		return optEndereco.get();
	}

	public Endereco buscarNoViacep(String cep) {
		RestTemplate rs = new RestTemplate();
		String uri = "http://viacep.com.br/ws/" + cep + "/json";
		Optional<EnderecoDTO> enderecoViaCep = Optional.ofNullable(rs.getForObject(uri, EnderecoDTO.class));

		if (enderecoViaCep.isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return fromEnderecoDTO(enderecoViaCep.get());
	}

	public Endereco fromEnderecoDTO(EnderecoDTO enderecoDTO) {
		Endereco endereco = new Endereco();
		endereco.setBairro(enderecoDTO.getBairro());
		endereco.setCep(enderecoDTO.getCep());
		endereco.setCidade(enderecoDTO.getLocalidade());
		endereco.setRua(enderecoDTO.getLogradouro());
		endereco.setUf(enderecoDTO.getUf());
		return endereco;
	}

	public Endereco buscarPorId(Long id) {
		Optional<Endereco> optEndereco = enderecoRepository.findById(id);
		if (optEndereco.isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return optEndereco.get();
	}

	public List<Endereco> buscarTodos() {
		return enderecoRepository.findAll();
	}

	public Endereco atualizar(Long id, EnderecoRequestDTO enderecoRequestDTO) {
		Endereco endereco = buscarPorId(id);
		endereco = modelMapper.map(enderecoRequestDTO, Endereco.class);
		return enderecoRepository.save(endereco);
	}

	public void deletar(Long id) {
		enderecoRepository.delete(buscarPorId(id));
	}

}