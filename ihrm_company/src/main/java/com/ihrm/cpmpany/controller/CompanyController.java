package com.ihrm.cpmpany.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.company.Company;
import com.ihrm.cpmpany.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 18:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * 添加企业
     * @param company
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result add(@RequestBody Company company)throws Exception{
        companyService.add(company);
        return Result.SUCCESS();
    }

    /**
     * 根据ID更新企业信息
     * @param id
     * @param company
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(name="id")String id,@RequestBody Company company)throws Exception{
        Company one = companyService.findById(id);
        one.setName(company.getName());
        one.setRemarks(company.getRemarks());
        one.setState(company.getState());
        one.setAuditState(company.getAuditState());
        companyService.update(company);
        return Result.SUCCESS();
    }

    /**
     * 根据ID删除企业信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name="id")String id)throws Exception{
        companyService.deleteById(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID获取公司信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(name="id")String id)throws Exception{
        Company company = companyService.findById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll()throws Exception{
        List<Company> companyList = companyService.findAll();
        return new Result(ResultCode.SUCCESS);
    }

}
