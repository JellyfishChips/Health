package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className HealthUserService
 * @description
 * @return
 * @date 2019/7/2 20:06
 */
@Component("healthUserService")
public class HealthUserService implements UserDetailsService {

    @Reference
    private UserService userService;

   // private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                Set<Permission> permissions = role.getPermissions();
                if (!CollectionUtils.isEmpty(permissions)) {
                    permissions.forEach(permission -> {
                        grantedAuthorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    });
                }
            });
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }
}
