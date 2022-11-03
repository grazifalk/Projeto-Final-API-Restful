package br.org.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.EnderecoResponseDTO;
import br.org.serratec.dto.cliente.ClienteRequestDTO;
import br.org.serratec.dto.cliente.ClienteResponseDTO;
import br.org.serratec.exception.ResourceNotFoundException;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;

	private ClienteResponseDTO clienteResponseDTO;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<ClienteResponseDTO> buscarTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteResponseDTO> clienteDTOs = new ArrayList<>();

		clientes.forEach(cliente -> {
			ClienteResponseDTO clienteDTO = modelMapper.map(cliente, ClienteResponseDTO.class);
			clienteDTO.setEnderecoResponseDTO(modelMapper.map(cliente.getEndereco(), EnderecoResponseDTO.class));
			clienteDTOs.add(clienteDTO);
		});

		return clienteDTOs;
	}

	public Cliente clientePorId(Long id) {
		Optional<Cliente> optCliente = clienteRepository.findById(id);
		if (optCliente.isEmpty())
			throw new ResourceNotFoundException("Produto não encontrado");
		return optCliente.get();
	}

	public ClienteResponseDTO buscarPorId(Long id) {
		Cliente cliente = clientePorId(id);

		ClienteResponseDTO clienteResponseDTO = modelMapper.map(cliente, ClienteResponseDTO.class);
		clienteResponseDTO.setEnderecoResponseDTO(modelMapper.map(cliente.getEndereco(), EnderecoResponseDTO.class));
		return clienteResponseDTO;
	}

	@Transactional
	public ClienteResponseDTO cadastrar(ClienteRequestDTO clienteRequestDTO) {
//		if (clienteRequestDTO.getCpf().isEmpty()) {
		Cliente cliente = new Cliente();
		cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
		cliente.setSenha(bCryptPasswordEncoder.encode(cliente.getSenha()));
		Endereco endereco = enderecoService.cadastrar(clienteRequestDTO.getEnderecoRequestDTO());
		cliente.setEndereco(endereco);
		ClienteResponseDTO clienteResponseDTO = modelMapper.map(clienteRepository.save(cliente),
				ClienteResponseDTO.class);
		clienteResponseDTO.setEnderecoResponseDTO(modelMapper.map(endereco, EnderecoResponseDTO.class));

		return clienteResponseDTO;
	}
//		else if (clienteRequestDTO.getCpf().equals(clienteResponseDTO.getCpf()));{
//				throw new ResourceNotFoundException("Cliente Já cadastrado!");
//			}
//	}

	@Transactional
	public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO) {
		Cliente cliente = clientePorId(id);
		cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
		cliente.setIdCliente(id);

		return modelMapper.map(clienteRepository.save(cliente), ClienteResponseDTO.class);
	}

	public void deletar(Long id) {
		clienteRepository.delete(clientePorId(id));
	}

}