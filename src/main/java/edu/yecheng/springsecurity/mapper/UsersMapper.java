package edu.yecheng.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.yecheng.springsecurity.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-06-17:43
 */

public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 获取用户列表
     *
     * @return {@link List}<{@link Users}>
     */
    List<Users> getUserList();
}
