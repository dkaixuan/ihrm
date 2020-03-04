package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.common.utils.MD5Util;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/2/28 16:42
 */
@CrossOrigin
@RequestMapping("sys")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PermissionService permissionService;


    @RequiresPermissions(value ="API-USER-DELETE")
    @RequestMapping(value = "/user/{id}",method =RequestMethod.DELETE,name ="API-USER-DELETE")
    public Result delete(@PathVariable("id")String id) {
        userService.deleteById(id);
        return Result.SUCCESS();
    }

    @RequestMapping(value ="/user",method =RequestMethod.POST)
    public Result save(@RequestBody User user) {
        userService.save(user);
        return Result.SUCCESS();
    }

     @GetMapping("/user")
     public Result findAll(@RequestParam(value = "page") int page,
     @RequestParam(value = "size") int size) {
     Page<User> pageList = userService.findAll(page, size);
     PageResult<User> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
     return new Result(ResultCode.SUCCESS, pageResult);
     }


     @GetMapping("/user/assignRoleList/{userid}")
     public Result getUserAssignRole(@PathVariable(name = "userid") String userId) {
     // 添加 roleIds (用户已经具有的角色id数组)
     User user = userService.findById(userId);
     UserResult userResult = new UserResult(user);
     return new Result(ResultCode.SUCCESS, userResult);
     }


     /**
     * 分配用户角色
     *
     * @param map
     * @return
     */
    @PostMapping("/user/assignRoles")
    public Result assignRole(@RequestBody Map<String, Object> map) {
        String userId = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.assignRole(userId, roleIds);
        return Result.SUCCESS();
    }


    /**
     * 删除用户的角色
     * @param map
     * @return
     */
    @PostMapping("/user/deleteRoles")
    public Result deleteRoles(@RequestBody Map<String, Object> map) {
        String userId = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.deleteRoles(userId, roleIds);
        return Result.SUCCESS();
    }
//
//    public static void main(String[] args) {
//        String mobile = "13800000004";
//        String password = "1";
//        String s = new Md5Hash(password, mobile, 3).toString();
//        System.out.println(s);
//    }



    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");

        //构造方法三个参数 1.密码 2.盐 3.加密次数
        password=new Md5Hash(password, mobile, 3).toString();
        //1.构造登录令牌 UserNameAndPassWorldToken
        UsernamePasswordToken upToken = new UsernamePasswordToken(mobile,password);
        //2.获取subject
        Subject subject = SecurityUtils.getSubject();
        //3.调用login方法，进入realm完成认证
        subject.login(upToken);
        //4.获取sessionId
        Serializable sessionId = subject.getSession().getId();
        //5.构造返回结果
        return new Result(ResultCode.SUCCESS, sessionId);


        //JWT的认证方式
//        User user = userService.findByMobile(mobile);
//        if (user == null || !user.getPassword().equals(MD5Util.digest(password))) {
//            return new Result(ResultCode.MOBILEORPASSWORDERROR);
//        } else {
//            //登录成功
//            StringBuilder builder = new StringBuilder();
//            Set<Role> roles = user.getRoles();
//            for (Role role : roles) {
//                for (Permission permission :role.getPermissions()) {
//                     builder.append(permission.getCode()).append(",");
//                }
//            }
//            Map<String, Object> map = new HashMap<>();
//            map.put("apis", builder.toString());
//            map.put("companyId", user.getCompanyId());
//            map.put("companyName", user.getCompanyName());
//            String token = jwtUtil.encode(user.getId(), user.getUsername(), map, null);
//            return new Result(ResultCode.SUCCESS, token);
//        }
    }


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Result profile() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        ProfileResult profileResult =null;
        if (principals != null && !principals.isEmpty()) {
             profileResult = (ProfileResult) principals.getPrimaryPrincipal();
        }
//
//        String userId = claims.getId();
//        User user = userService.findById(userId);
//
//        ProfileResult result=null;
//        if ("user".equals(user.getLevel())) {
//            result = new ProfileResult(user);
//        }else {
//            Map map = new HashMap();
//            if ("coAdmin".equals(user.getLevel())) {
//                map.put("enVisible","1");
//            }
//            List<Permission> permissionList = permissionService.findAll(map);
//            result = new ProfileResult(user, permissionList);
//        }
        return new Result(ResultCode.SUCCESS, profileResult);
    }








}
