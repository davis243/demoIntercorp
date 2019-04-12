package pe.davis.demoIntercorp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.davis.demoIntercorp.domain.model.ClienteEntity;
import pe.davis.demoIntercorp.domain.repository.ClienteRepository;

@Service
public class ClientServiceImpl implements ClienteService{

	@Autowired
	ClienteRepository clienteRepository;
	@Override
	public void save(ClienteEntity cliente) {
		
		clienteRepository.save(cliente);
	}
	@Override
	public List<ClienteEntity> getListaClientes() {
		
		return (List<ClienteEntity>) clienteRepository.findAll();
	}

}
