package br.com.five.seven.food.rest;

import br.com.five.seven.food.rest.mapper.ClientMapper;
import br.com.five.seven.food.rest.request.ClientRequest;
import br.com.five.seven.food.rest.response.ClientResponse;
import br.com.five.seven.food.domain.model.Client;
import br.com.five.seven.food.application.ports.in.ClientUseCase;
import br.com.five.seven.food.infra.annotations.client.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Client", description = "Operations related to client management")
@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientUseCase service;

    private final ClientMapper mapper;

    @SwaggerCreateClient
    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) throws ValidationException {
        Client saved = service.createClient(mapper.requestToDomain(request));
        return ResponseEntity.ok(mapper.domainToResponse(saved));
    }

    @SwaggerListAllClients
    @GetMapping
    public ResponseEntity<List<ClientResponse>> listAllClients() {
        return ResponseEntity.ok(service.findAll()
                .stream()
                .map(mapper::domainToResponse)
                .collect(Collectors.toList()));
    }

    @SwaggerFindByCpf
    @GetMapping("/{cpf}")
    public ResponseEntity<ClientResponse> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(mapper.domainToResponse(service.findByCpf(cpf)));
    }

    @SwaggerUpdateClient
    @PutMapping("/{cpf}")
    public ResponseEntity<ClientResponse> update(@PathVariable String cpf, @RequestBody ClientRequest request) throws ValidationException {
        Client updated = service.update(cpf, mapper.requestToDomain(request));
        return ResponseEntity.ok(mapper.domainToResponse(updated));
    }

    @SwaggerDeleteClient
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        service.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
