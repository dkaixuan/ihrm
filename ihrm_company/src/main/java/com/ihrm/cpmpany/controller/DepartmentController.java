package com.ihrm.cpmpany.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;

import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.response.DeptListResult;
import com.ihrm.cpmpany.service.CompanyService;
import com.ihrm.cpmpany.service.DepartmentService;

import com.ihrm.domain.company.Department;
import javafx.collections.ObservableArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    /**
     * 保存
     */
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        //1.设置保存的企业id
        /**
         * 企业id：目前使用固定值1，以后会解决
         */
        department.setCompanyId(companyId);
        //2.调用service完成保存企业
        departmentService.save(department);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public Result findAll() {
        //1.指定企业id
        Company company = companyService.findById(companyId);
        company.setName("江苏传智播客教育股份有限公司");
        //2.完成查询
        List<Department> list = departmentService.findAll(companyId);
        Map<String, Department> map = new HashMap<>();
        List<Department> root = new ArrayList<>();
        //把department的id当作key
        for (Department department : list) {
            map.put(department.getId(), department);
        }

        for (Department department : list) {
            if (department.getPid().equals("0")) {
                root.add(department);
            }
            else {
                Department parent = map.get(department.getPid());
                if (parent != null) {
                    parent.getChildren().add(department);
                }
            }
        }
        //3.构造返回结果
        DeptListResult deptListResult = new DeptListResult(company, root);
        return new Result(ResultCode.SUCCESS, deptListResult);

    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS, department);
    }

    /**
     * 修改Department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Department department) {
        //1.设置修改的部门id
        department.setId(id);
        //2.调用service更新
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        departmentService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }


}
