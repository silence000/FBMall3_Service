package com.excmmy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excmmy.bean.Permission;
import com.excmmy.bean.Rolepermission;
import com.excmmy.mapper.PermissionMapper;
import com.excmmy.mapper.RolepermissionMapper;
import com.excmmy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pojo.UserDTO;

import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolepermissionMapper rolepermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    // 根据账号查询用户权限信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<com.excmmy.bean.User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        userQueryWrapper.eq("isDelete", 0);
        com.excmmy.bean.User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            return null;
        }
        Map<String,Object> rolePermissionCondition = new HashMap<>();
        rolePermissionCondition.put("rid", user.getRid());
        rolePermissionCondition.put("isDelete", 0);
        List<Rolepermission> rolepermissionList = rolepermissionMapper.selectByMap(rolePermissionCondition);
        List<String> permissions = new ArrayList<>();
        for (Rolepermission rolepermission : rolepermissionList) {
            QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
            permissionQueryWrapper.eq("id", rolepermission.getPmid());
            permissionQueryWrapper.eq("isDelete", 0);
            Permission permission = permissionMapper.selectOne(permissionQueryWrapper);
            permissions.add(permission.getPremissionKey() + "");
        }
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
        // 将userDto转成Json
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId() + "");
        userDTO.setName(user.getName());
        String principal = JSON.toJSONString(userDTO);
        UserDetails userDetails = User.withUsername(principal)
                .password(user.getPassword())
                .authorities(permissionArray).build();
        return userDetails;
    }
}
