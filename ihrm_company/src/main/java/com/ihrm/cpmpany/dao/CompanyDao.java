package com.ihrm.cpmpany.dao;

import com.ihrm.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 18:05
 * @Version 1.0
 * 企业数据访问接口
 * JpaRepository提供了基本的增删改查 JpaSpeciﬁcationExecutor用于做复杂的条件查询
 */
@Repository
public interface CompanyDao extends JpaRepository<Company,String>, JpaSpecificationExecutor<Company> {

}
