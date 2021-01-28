package com.arthuranthony.cursomc.services;

import java.util.Optional;
import com.arthuranthony.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthuranthony.cursomc.domain.Cliente;
import com.arthuranthony.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Cliente.class.getName()));
	}
}

