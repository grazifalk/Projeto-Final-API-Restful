package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.org.serratec.dto.ItemPedidoDTO;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ModelMapper modelMapper;

	public List<ItemPedidoDTO> buscarTodos() {
		List<ItemPedido> itens = itemPedidoRepository.findAll();
		return itens.stream().map(item -> modelMapper.map(item, ItemPedidoDTO.class)).collect(Collectors.toList());
	}

	public ItemPedido itemPorId(Long id) {
		Optional<ItemPedido> optItemPedido = itemPedidoRepository.findById(id);
		if (optItemPedido.isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return optItemPedido.get();
	}

	public List<ItemPedido> itensPorIdPedido(Long idPedido) {
		return itemPedidoRepository.findByPedido(pedidoService.pedidoPorId(idPedido));
	}

	public List<ItemPedido> itensPorPedido(Pedido pedido) {
		return itemPedidoRepository.findByPedido(pedido);
	}

	public ItemPedidoDTO buscarPorId(Long id) {
		return modelMapper.map(itemPorId(id), ItemPedidoDTO.class);
	}

	@Transactional
	public ItemPedido cadastrar(ItemPedidoDTO itemPedidoDTO, Pedido pedido) {
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());

		Produto produto = produtoService.produtoPorId(itemPedidoDTO.getIdProduto());
		itemPedido.setProduto(produto);
		itemPedido.setPedido(pedido);

		if (itemPedidoDTO.getQuantidade() > produto.getQuantidadeEstoque())
			throw new HttpClientErrorException(HttpStatus.NO_CONTENT); // TODO: tratar erro estoque insuficiente

		Float precoVenda = produto.getValorUnitario() * itemPedidoDTO.getQuantidade();
		itemPedido.setPrecoVenda(precoVenda);

		return itemPedido;
	}

	public ItemPedidoDTO atualizar(Long id, ItemPedidoDTO itemPedidoDto) {
		ItemPedido itemPedido = itemPorId(id);
		itemPedido = modelMapper.map(itemPedido, ItemPedido.class);
		itemPedido.setIdItemPedido(id);

		return modelMapper.map(itemPedidoRepository.save(itemPedido), ItemPedidoDTO.class);
	}

	public void deletar(Long id) {
		itemPedidoRepository.delete(itemPorId(id));
	}

	public void salvarTodos(List<ItemPedido> itens) {
		for (ItemPedido item : itens) {
			produtoService.removerDoEstoque(item.getProduto(), item.getQuantidade());
		}

		itemPedidoRepository.saveAll(itens);
	}

}