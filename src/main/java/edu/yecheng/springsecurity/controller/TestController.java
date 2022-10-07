package edu.yecheng.springsecurity.controller;

import edu.yecheng.springsecurity.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-06-15:15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello-security";
    }

    @GetMapping("/index")
    public String index() {
        return "hello-index";
    }

    @GetMapping("/update")
//    @Secured({"ROLE_jack","ROLE_yecheng"})  //角色校验注解
//    @PreAuthorize("hasAuthority('admins')")  //权限校验注解，在方法执行之前校验
//    @PostAuthorize("hasAuthority('admin')")  //权限校验注解，在方法执行之后校验，所以就算没权限也是会执行方法
    @PreAuthorize("hasAnyAuthority('menu:system')")
    public String update() {
        System.out.println("update....................");
        return "hello-update";
    }

    @GetMapping("/getAll")
    @PostAuthorize("hasAuthority('admins')")
    @PostFilter("filterObject.username == 'admin1'")
    public List<Users> getAllUser() {

        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(1, "admin1", "6666"));
        list.add(new Users(2, "admin2", "888"));
        System.out.println(list);
        return list;
    }
//    @PostAuthorize("hasAnyRole('ROLE_ajack','ROLE_yecheng')")
    //权限验证之后对数据进行过滤 留下用户名是 admin1 的数据
}
