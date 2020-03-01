package com.ihrm.system.dao;

import com.ihrm.domain.system.PermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 20:13
 * @Version 1.0
 * 权限按钮点持久化类
 */
public interface PermissionPointDao extends JpaRepository<PermissionPoint,String>, JpaSpecificationExecutor<PermissionPoint> {

}
