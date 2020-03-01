package com.ihrm.system.controller;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.common.utils.MD5Util;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/2/28 16:42
 */
@CrossOrigin
@RequestMapping("sys")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;


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
     *
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


    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");

        User user = userService.findByMobile(mobile);
        if (user == null || !user.getPassword().equals(MD5Util.digest(password))) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        } else {
            //登录成功
            //
            Map<String, Object> map = new HashMap<>();
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            String token = jwtUtil.encode(user.getId(), user.getUsername(), map, null);
            return new Result(ResultCode.SUCCESS, token);
        }
    }


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        //前后端约定头信息内容以 Bearer+空格+token 形式组成
        String token = authorization.replace("Bearer ", "");
        //比较并获取claims
        Claims claims = jwtUtil.decode(token, null);
        if (claims == null) {
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        String userId = claims.getId();
        User user = userService.findById(userId);
        return new Result(ResultCode.SUCCESS, new ProfileResult(user));

    }


}
