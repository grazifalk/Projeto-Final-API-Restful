package br.org.serratec.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.CategoriaDTO;
import br.org.serratec.exception.ResourceNotFoundException;
import br.org.serratec.model.Categoria;
import br.org.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<CategoriaDTO> buscarCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias.stream().map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
				.collect(Collectors.toList());
	}

	public Categoria categoriaPorId(Long id) {
		Optional<Categoria> optCategoria = categoriaRepository.findById(id);
		if (optCategoria.isEmpty()) {
			throw new ResourceNotFoundException("Produto não encontrado");
		}
		return optCategoria.get();
	}

	public CategoriaDTO buscarPorId(Long id) {
		return modelMapper.map(categoriaPorId(id), CategoriaDTO.class);
	}

	public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria = modelMapper.map(categoriaDTO, Categoria.class);

		return modelMapper.map(categoriaRepository.save(categoria), CategoriaDTO.class);
	}

	public CategoriaDTO alterarCategoria(Long id, CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaPorId(id);
		categoriaDTO.setIdCategoria(id);
		categoria = modelMapper.map(categoriaDTO, Categoria.class);

		return modelMapper.map(categoriaRepository.save(categoria), CategoriaDTO.class);
	}

	public void deletarCategoria(Long id) {
		categoriaRepository.delete(categoriaPorId(id));
	}

}