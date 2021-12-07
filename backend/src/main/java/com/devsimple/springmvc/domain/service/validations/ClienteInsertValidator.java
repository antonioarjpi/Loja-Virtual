package com.devsimple.springmvc.domain.service.validations;

import com.devsimple.springmvc.api.dto.ClienteNewDTO;
import com.devsimple.springmvc.domain.enums.TipoCliente;
import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import com.devsimple.springmvc.domain.service.validations.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<EntidadeNaoEncontradaException> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new EntidadeNaoEncontradaException("CPF inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new EntidadeNaoEncontradaException("Cnpj inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if (cliente != null){
            list.add(new EntidadeNaoEncontradaException("Email já existente"));
        }

        for (EntidadeNaoEncontradaException e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getMessage()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}