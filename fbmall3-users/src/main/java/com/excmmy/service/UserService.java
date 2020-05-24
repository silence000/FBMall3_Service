package com.excmmy.service;

import com.excmmy.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-17
 */
public interface UserService extends IService<User> {
    ResponseJsonBody insertUser(String username, String userpass);

    ResponseJsonBody getUsernameById(Integer id);
}
