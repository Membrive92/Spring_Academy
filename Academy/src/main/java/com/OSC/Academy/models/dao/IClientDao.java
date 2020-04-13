package com.OSC.Academy.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.OSC.Academy.models.entity.Client;
import com.sun.xml.bind.v2.model.core.ID;

public interface IClientDao extends CrudRepository<Client, Long> {

}
