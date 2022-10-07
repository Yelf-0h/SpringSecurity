package edu.yecheng.springsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.yecheng.springsecurity.entity.Users;
import edu.yecheng.springsecurity.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.Query;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-06-17:16
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Users users = usersMapper.selectOne(queryWrapper);

        if (users == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> role = AuthorityUtils.commaSeparatedStringToAuthorityList("role,admins,ROLE_jack");
        /*从查询数据库返回users对象，得到用户名和密码返回*/
        return new User(users.getUsername(), bCryptPasswordEncoder.encode(users.getPassword()), role);

    }
}
