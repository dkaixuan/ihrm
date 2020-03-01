package com.ihrm.system.dao;

import com.ihrm.domain.system.PermissionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 20:12
 * @Version 1.0
 * 权限菜单持久化类
 */
public interface PermissionMenuDao extends JpaRepository<PermissionMenu,String>, JpaSpecificationExecutor<PermissionMenu> {
}
