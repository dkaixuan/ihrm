package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 22:26
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "sys")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 保存
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map)throws Exception{
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 修改
     */
    @RequestMapping(value="/permission/{id}",method = RequestMethod.PUT)
    public Result update (@PathVariable(name = "id")String id,@RequestBody Map<String,Object>map)throws Exception{
        //构造id
       map.put("id",id);
       permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询列表
     *
     */
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map map)throws Exception{
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(name="id")String id)throws Exception{
        Map<String, Object> map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name="id")String id)throws Exception{
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
