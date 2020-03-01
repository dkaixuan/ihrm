package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;

import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ihrm.domain.system.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 16:58
 * @Version 1.0
 */
@Service
public class RoleService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 添加角色
     */
    public void save(Role role) {
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);

    }

    /**
     * 更新角色
     */
    public void update(Role role) {
        Role targer = roleDao.getOne(role.getId());
        targer.setDescription(role.getDescription());
        targer.setName(role.getName());
        roleDao.save(targer);
    }

    /**
     * 根据ID查询角色
     */
    public Role findById(String id) {
        return roleDao.findById(id).get();

    }

    /**
     * 删除角色
     */
    public void delete(String id) {
        roleDao.deleteById(id);
    }


    /**
     * 分配权限
     *
     * @param roleId
     * @param permIds
     */
    public void assignPrems(String roleId, List<String> permIds) {
        //获取分配的角色对象、
        Role role = roleDao.findById(roleId).get();
        //构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询api权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            perms.addAll(apiList);
            perms.add(permission);//当前菜单或按钮的权限
        }
        System.out.println(perms.size());
        //设置角色和权限的关系
        role.setPermissions(perms);
        //更新角色
        roleDao.save(role);
    }


    public Page<Role> getRoleList(int page, int pagesize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize);
        return roleDao.findAll(pageRequest);
    }

    public List<Role> findAll() {
       return roleDao.findAll();
    }
}
