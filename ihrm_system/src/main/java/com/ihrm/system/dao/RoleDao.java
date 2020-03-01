package com.ihrm.system.dao;

import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 16:56
 * @Version 1.0
 */
public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
}
