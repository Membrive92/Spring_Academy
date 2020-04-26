package com.OSC.Academy.models.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.OSC.Academy.models.dao.IClientDao;
import com.OSC.Academy.models.entity.Client;

//this anotation mark this like a service component
@Service
public class ClientServiceImpl implements IClientService {
	
	//dependencies injector
	@Autowired
	private IClientDao clientDao;
	
	@Override
    @Transactional(readOnly = true)
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return (List<Client>) clientDao.findAll();
	}
	 @Transactional(readOnly = true)
	@Override
	public Client findById(Long id) {
		// TODO Auto-generated method stub
		return clientDao.findById(id).orElse(null);
	}

	@Override
	public Client save(Client client) {
		// TODO Auto-generated method stub
		return clientDao.save(client);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clientDao.deleteById(id);
		
	}

}
