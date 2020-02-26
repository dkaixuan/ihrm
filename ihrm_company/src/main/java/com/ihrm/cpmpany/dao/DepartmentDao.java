package com.ihrm.cpmpany.dao;
import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 12:45
 * @Version 1.0
 * 部门操作持久层
 */

public interface DepartmentDao extends JpaRepository<Department,String>,JpaSpecificationExecutor<Department> {

}
