package com.ihrm.system.dao;

import com.ihrm.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 20:09
 * @Version 1.0
 * 权限数据访问接口
 */
public interface PermissionDao extends JpaRepository<Permission,String>, JpaSpecificationExecutor<Permission> {
    List findByTypeAndPid(int type, String pid);
}
