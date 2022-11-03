package br.org.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.org.serratec.dto.EmailDTO;
import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.dto.PedidoDTO;
import br.org.serratec.exception.EmailException;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.model.StatusPedido;
import br.org.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelMapper modelMapper;

	public List<PedidoDTO> buscarTodos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDTO> pedidoDTOs = new ArrayList<>();

		pedidos.forEach(pedido -> {
			PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
			pedidoDTO.setItens(pedido.getItens().stream().map(item -> modelMapper.map(item, ItemPedidoDTO.class))
					.collect(Collectors.toList()));
			pedidoDTOs.add(pedidoDTO);
		});

		return pedidoDTOs;
	}

	public Pedido pedidoPorId(Long id) {
		Optional<Pedido> optPedido = pedidoRepository.findById(id);
		if (optPedido.isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return optPedido.get();
	}

	public PedidoDTO buscarPorId(Long id) {
		return modelMapper.map(pedidoPorId(id), PedidoDTO.class);
	}

	@Transactional
	public PedidoDTO cadastrar(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.PENDENTE);
		pedidoDTO.setDataPedido(LocalDate.now());
		pedido = modelMapper.map(pedidoDTO, Pedido.class);
		List<ItemPedido> itens = new ArrayList<>();

		for (ItemPedidoDTO item : pedidoDTO.getItens()) {
			itens.add(itemPedidoService.cadastrar(item, pedido));
		}
		pedido.setItens(itens);
		pedido = pedidoRepository.save(pedido);
		itemPedidoService.salvarTodos(itens);

		pedidoDTO.setIdPedido(pedido.getIdPedido());

		emailService.enviar(prepararEmail(pedido.getIdPedido()));

		return pedidoDTO;
	}

	@Transactional
	public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTO) {
		Pedido pedido = pedidoPorId(id);
		pedidoDTO.setDataPedido(pedido.getDataPedido());
		pedido = modelMapper.map(pedidoDTO, Pedido.class);
		pedido.setIdPedido(id);

		return modelMapper.map(pedidoRepository.save(pedido), PedidoDTO.class);
	}

	public void deletar(Long id) {
		pedidoRepository.delete(pedidoPorId(id));
	}

	public String finalizar(Long id) throws MessagingException, EmailException {

		Pedido pedido = pedidoPorId(id);

		pedido.setStatus(StatusPedido.FINALIZADO);
		pedido.setDataPedido(LocalDate.now());

		produtoService.atualizarEstoque(pedido.getItens());

		pedidoRepository.save(pedido);

		emailService.emailPedidoFinalizado(pedido);

		return String.format("Pedido id %d finalizado com sucesso!", id);
	}

	private EmailDTO prepararEmail(Long idPedido) {
		List<String> destinatarios = new ArrayList<>();
		destinatarios.add("grazifalk@gmail.com");
		destinatarios.add("queziamenezessouza@gmail.com");
		destinatarios.add("pritere90@gmail.com");
		destinatarios.add("roni.schanuel@docente.firjan.senai.br");

		String mensagem = "<h1 style=\"color:red\">  Pedido nº: " + idPedido + "! </h1> <p style=\"font-weight: bold\">Realizado com sucesso!</p> ";

		return new EmailDTO("Novo pedido criado.", mensagem, "graziela.falk@aluno.senai.br", destinatarios);
	}

}