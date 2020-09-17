package com.OSC.Academy.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.OSC.Academy.models.entity.*;

public interface IClientService {
	public List<Client> findAll();

	public Page<Client> findAll(Pageable pageable);

	public Client findById(Long id);

	public Client save(Client client);

	public void delete(Long id);
}
