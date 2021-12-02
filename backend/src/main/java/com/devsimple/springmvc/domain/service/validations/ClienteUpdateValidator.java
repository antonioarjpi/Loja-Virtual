package com.devsimple.springmvc.domain.service.validations;

import com.devsimple.springmvc.api.dto.ClienteDTO;
import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<EntidadeNaoEncontradaException> list = new ArrayList<>();

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if (cliente != null && !cliente.getId().equals(uriId)){
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