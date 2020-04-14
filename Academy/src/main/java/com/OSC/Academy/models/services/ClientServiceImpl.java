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

	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return (List<Client>) clientDao.findAll();
	}

}
