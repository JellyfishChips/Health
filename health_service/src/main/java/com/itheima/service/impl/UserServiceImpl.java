package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className UserServiceImpl
 * @description
 * @return
 * @date 2019/7/2 20:43
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) {
        //根据用户名查询user
        User user = userDao.findUserByUsername(username);
        //如果是空返回null
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        //根据user id 查询role
        Set<Role> roleSet = roleDao.findRoleByUserId(user.getId());
        if (!CollectionUtils.isEmpty(roleSet)) {
            roleSet.forEach(role -> {
                //根据role id 查询 permission
                Set<Permission> permissionSet = permissionDao.findPermissionByRoleId(role.getId());
                role.setPermissions(permissionSet);
            });
            user.setRoles(roleSet);
        }
        return user;
    }
}
