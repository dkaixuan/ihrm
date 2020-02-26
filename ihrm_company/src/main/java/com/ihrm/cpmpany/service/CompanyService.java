package com.ihrm.cpmpany.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.cpmpany.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 18:08
 * @Version 1.0
 *
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加企业
     * @param company  企业信息
     * @return
     */
    public Company add(Company company){
        company.setId(idWorker.nextId()+"");
        company.setCreateTime(new Date());
        company.setState(1);//启用
        company.setAuditState("0");//待审核
        company.setBalance(0d);
        return companyDao.save(company);
    }

    public Company update(Company company){
        return companyDao.save(company);
    }

    public Company findById(String id){
        return companyDao.findById(id).get();
    }

    public void deleteById(String id){
        companyDao.deleteById(id);
    }

    public List<Company> findAll(){
        return companyDao.findAll();
    }

}
