package com.purotoken.eltokenaso.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.purotoken.eltokenaso.models.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long>{
    
}
