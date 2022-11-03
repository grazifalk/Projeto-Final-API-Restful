package br.org.serratec.service;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.dto.ProdutoDTO;
import br.org.serratec.exception.EmailException;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Produto;
import br.org.serratec.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelMapper modelMapper;

	public List<ProdutoDTO> buscarTodos() {
		List<Produto> produtos = produtoRepository.findAll();
		ModelMapper mapper = new ModelMapper();

		return produtos.stream().map(produto -> mapper.map(produto, ProdutoDTO.class)).collect(Collectors.toList());
	}

	public Produto produtoPorId(Long id) {
		Optional<Produto> optProduto = produtoRepository.findById(id);
		if (optProduto.isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return optProduto.get();
	}

	public ProdutoDTO buscarPorId(Long id) {
		return modelMapper.map(produtoPorId(id), ProdutoDTO.class);
	}

	public ProdutoDTO cadastrar(ProdutoDTO produtoDTO, MultipartFile file) throws IOException {
		Produto produto = new Produto();
		produtoDTO.setIdProduto(produto.getIdProduto());
		produtoDTO.setDataCadastro(LocalDate.now());
		produtoDTO.setUri(inserirUriImagem(produto).toString());
		produto = modelMapper.map(produtoDTO, Produto.class);
		produto.setImagem(file.getBytes());
		produto.setCategoria(categoriaService.categoriaPorId(produtoDTO.getIdCategoria()));
		produtoRepository.save(produto);
		return produtoDTO;
	}

//	public ProdutoDTO cadastrar(ProdutoDTO produtoDTO, MultipartFile file) throws IOException {
//		Produto produto = new Produto();
//		produtoDTO.setDataCadastro(LocalDate.now());
//		produto.setCategoria(categoriaService.categoriaPorId(produtoDTO.getIdCategoria()));
//		produtoDTO = inserirUriImagem(produto);
//		produto.setImagem(file.getBytes());
//		produtoRepository.save(produto);
//		return produtoDTO;
//	}

	public ProdutoDTO alterar(Long id, ProdutoDTO produtoDTO) {
		Produto produto = produtoPorId(id);
		produtoDTO.setIdProduto(id);
		produtoDTO.setDataCadastro(produto.getDataCadastro());
		produto = modelMapper.map(produtoDTO, Produto.class);

		modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
//		return inserirUriImagem(produto);
		return produtoDTO;
	}

	public void deletarProduto(Long id) {
		// if (produto.isEmpty()) lançar exceção
		produtoRepository.delete(produtoPorId(id));
	}

	public void removerDoEstoque(Produto produto, Integer quantidade) {
		Integer estoqueFinal = produto.getQuantidadeEstoque() - quantidade;
		produto.setQuantidadeEstoque(estoqueFinal);
		produtoRepository.save(produto);
	}

	private URI inserirUriImagem(Produto produto) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/produto/{id}/foto").buildAndExpand("id")
				.toUri();
	}

	public void atualizarEstoque(List<ItemPedido> itens) throws MessagingException, EmailException {

		List<Produto> listaEstoqueBaixo = new ArrayList<>();

		for (ItemPedido item : itens) {
			Produto produto = item.getProduto();
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
			produtoRepository.save(produto);

			if (produto.getQuantidadeEstoque() <= 5) {
				listaEstoqueBaixo.add(produto);
			}
		}

		if (!listaEstoqueBaixo.isEmpty()) {
			emailService.emailEstoqueBaixo(listaEstoqueBaixo);
		}
	}

}