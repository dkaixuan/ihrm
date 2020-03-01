package com.ihrm.system.service;

import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.processing.RoundEnvironment;
import java.util.*;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/2/28 16:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public Page<User> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(pageRequest);
    }

    public void assignRole(String userId, List<String> roleIds) {
        //根据Id查询用户
        //set用户的角色集合
        //更新用户集合
        Set<Role> roleSet = new HashSet<>();
        Optional<User> optionaUser= userDao.findById(userId);

        if (optionaUser.isPresent()) {
            User user = optionaUser.get();
            for (String roleId : roleIds) {
                Optional<Role> optionalRole = roleDao.findById(roleId);
                if (optionalRole.isPresent()) {
                    Role role = optionalRole.get();
                    roleSet.add(role);
                }
            }
            user.setRoles(roleSet);
            userDao.save(user);
        }
    }


    public List<String> getAllRoleIds(String userId) {
        List<Role> all = roleDao.findAll();
        List<String> roleIds = new ArrayList<>();
        for (Role role : all) {
            roleIds.add(role.getId());
        }
        return roleIds;
    }

    public List<String> getUserRoleIds(String userId) {
        Optional<User> optional = userDao.findById(userId);
        User user = optional.get();
        Set<Role> roles = user.getRoles();
        System.out.println(user);
        return null;
    }

    public User findById(String userId) {
       return userDao.findById(userId).get();
    }

    public void deleteRoles(String userId, List<String> roleIds) {
        userDao.deleteUserRole(userId,roleIds);
    }

    public User findByMobile(String mobile) {
      return userDao.findByMobile(mobile);
    }


}
