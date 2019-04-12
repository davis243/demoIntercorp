package pe.davis.demoIntercorp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.davis.demoIntercorp.domain.model.ClienteEntity;


public interface ClienteService {
	void save(ClienteEntity cliente);
	List<ClienteEntity> getListaClientes();
}
