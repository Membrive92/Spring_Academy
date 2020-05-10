package com.OSC.Academy.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.OSC.Academy.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long> {

}
