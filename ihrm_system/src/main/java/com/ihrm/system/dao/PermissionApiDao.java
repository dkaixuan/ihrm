package com.ihrm.system.dao;

import com.ihrm.domain.system.PermissionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 20:15
 * @Version 1.0
 * 权限api持久化类
 */
public interface PermissionApiDao extends JpaRepository<PermissionApi,String>, JpaSpecificationExecutor<PermissionApi> {
}
