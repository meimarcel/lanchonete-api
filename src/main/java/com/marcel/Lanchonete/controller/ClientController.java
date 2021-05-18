package com.marcel.Lanchonete.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.marcel.Lanchonete.assembler.ClientAssembler;
import com.marcel.Lanchonete.assembler.ClientManagerAssembler;
import com.marcel.Lanchonete.dto.ClientDTO;
import com.marcel.Lanchonete.enums.DetailType;
import com.marcel.Lanchonete.error.UserAlreadyExistException;
import com.marcel.Lanchonete.error.UserNotFoundException;
import com.marcel.Lanchonete.error.MasterDetails;
import com.marcel.Lanchonete.helper.ClientHelper;
import com.marcel.Lanchonete.model.Client;
import com.marcel.Lanchonete.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ClientController {
    
    @Autowired
    private ClientRepository clientDAO;

    @Autowired
    private ClientAssembler clientAssembler;

    @Autowired
    private ClientManagerAssembler clientManagerAssembler;

    @Autowired
    private PagedResourcesAssembler<Client> pagedResourcesAssembler;

    @Autowired
    private ClientHelper clientHelper;

    @PostMapping("/public/client/create")
    @Transactional
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        this.verifyIfClientAlreadyExist(clientDTO.getEmail());
        Client client = clientHelper.toClient(clientDTO);

        EntityModel<Client> clientEntity = clientAssembler.toModel(clientDAO.save(client));
        return ResponseEntity
            .created(clientEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(clientEntity);
    }

    @GetMapping("/admin/client/all")
    public ResponseEntity<?> listClients(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.ASC) Pageable pageable) {
        Page<Client> cursoPage = clientDAO.findAll(pageable);
        PagedModel<EntityModel<Client>> clientPagedModel = pagedResourcesAssembler.toModel(cursoPage, clientManagerAssembler);
        
        return ResponseEntity.ok(clientPagedModel);
    }

    @GetMapping("/public/client/byEmail/{email}")
    public ResponseEntity<?> getClient(@PathVariable("email") String email) {
        Client client = clientDAO.findById(email)
            .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado."));
        return ResponseEntity.ok(clientAssembler.toModel(client));
    }
    
    @PutMapping("/public/client/update")
    @Transactional
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = clientDAO.findById(clientDTO.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado."));
        client = clientHelper.toClient(client, clientDTO);
        EntityModel<Client> clientEntity = clientAssembler.toModel(clientDAO.save(client));
        return ResponseEntity
            .created(clientEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(clientEntity);
    }

    @DeleteMapping("/public/client/delete/{email}")
    @Transactional
    public ResponseEntity<?> deleteClient(@PathVariable("email") String email) {
        Client client = clientDAO.findById(email)
            .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado."));
        clientDAO.delete(client);

        return ResponseEntity.ok(MasterDetails.Builder
            .newBuilder()
            .title("Information")
            .message("Cliente deletado com sucesso.")
            .type(DetailType.SUCCESS)
            .timestamp(LocalDateTime.now())
            .build());
    }

    private void verifyIfClientAlreadyExist(String email) {
        Client client = clientDAO.findById(email).orElse(null);
        if(client != null) {
            throw new UserAlreadyExistException("Email já cadastrado.");
        }
    }
}
 