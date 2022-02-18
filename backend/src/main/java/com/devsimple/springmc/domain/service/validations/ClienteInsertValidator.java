package com.devsimple.springmc.domain.service.validations;

import com.devsimple.springmc.api.dto.ClienteNewDTO;
import com.devsimple.springmc.domain.enums.TipoCliente;
import com.devsimple.springmc.domain.exception.ObjectNotFoundException;
import com.devsimple.springmc.domain.model.Cliente;
import com.devsimple.springmc.domain.repository.ClienteRepository;
import com.devsimple.springmc.domain.service.validations.utils.BR;
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

        List<ObjectNotFoundException> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new ObjectNotFoundException("CPF inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new ObjectNotFoundException("Cnpj inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if (cliente != null){
            list.add(new ObjectNotFoundException("Email já existente"));
        }

        for (ObjectNotFoundException e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getMessage()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}