package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/2/28 16:43
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Modifying
    @Query(value = "delete from pe_user_role where user_id=?1 and role_id in ?2",nativeQuery = true)
    void deleteUserRole(String userid,List<String> roleIds);

    User findByMobile(String mobile);

}
