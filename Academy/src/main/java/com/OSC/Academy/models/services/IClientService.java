package com.OSC.Academy.models.services;

import java.util.List;

import com.OSC.Academy.models.entity.*;
public interface IClientService {
	public List<Client> findAll();
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public void delete(Long id);
}
