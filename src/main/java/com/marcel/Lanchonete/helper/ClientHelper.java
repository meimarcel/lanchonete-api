package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.ClientDTO;
import com.marcel.Lanchonete.model.Client;

import org.springframework.stereotype.Component;

@Component
public class ClientHelper {
    public Client toClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setEmail(clientDTO.getEmail());
        client.setName(clientDTO.getName());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setTelephoneNumber(clientDTO.getTelephoneNumber());
        return client;
    }

    public Client toClient(Client client, ClientDTO clientDTO) {
        client.setName(clientDTO.getName());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setTelephoneNumber(clientDTO.getTelephoneNumber());
        return client;
    }
}
