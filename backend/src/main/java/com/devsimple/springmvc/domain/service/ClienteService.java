package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.api.dto.ClienteDTO;
import com.devsimple.springmvc.api.dto.ClienteNewDTO;
import com.devsimple.springmvc.domain.enums.TipoCliente;
import com.devsimple.springmvc.domain.exception.DataIntegrityException;
import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.model.Endereco;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import com.devsimple.springmvc.domain.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente buscar(Long clienteId){
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente adicionar(Cliente cliente){
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        Cliente novoCliente = buscar(cliente.getId());
        atualizarCliente(novoCliente, cliente);
        return clienteRepository.save(novoCliente);
    }

    @Transactional
    public Cliente adicionarDTO(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteNewDTO.getTipo()));
        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(),
                clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if (clienteNewDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }
        return cliente;
    }

    @Transactional
    public void remover(Long cliente){
        buscar(cliente);
        try {
            clienteRepository.deleteById(cliente);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Erro! Cliente com pedidos!");
        }
    }

    public Page<Cliente> buscarPagina(Integer page, Integer linesPerPages, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente clienteDto(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void atualizarCliente(Cliente novoCliente, Cliente cliente){
        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
    }
}
