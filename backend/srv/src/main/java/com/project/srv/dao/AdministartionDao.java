package com.project.srv.dao;


import com.project.srv.bean.Administration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministartionDao extends JpaRepository<Administration, Long> {

    Administration findByName(String name);

}
