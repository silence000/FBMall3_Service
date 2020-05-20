package com.excmmy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excmmy.bean.User;
import com.excmmy.mapper.UserMapper;
import com.excmmy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pojo.MallConstant;
import pojo.ResponseJsonBody;
import util.RegxEnum;
import util.RegxVerify;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseJsonBody insertUser(String username, String userpass) {
        // 查询该用户名是否已经存在
        ResponseJsonBody responseJsonBody = new ResponseJsonBody();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        userQueryWrapper.eq("isDelete", "0");
        User originalUser = userMapper.selectOne(userQueryWrapper);
        if (originalUser != null) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE_NAME_CONFLICT);
            responseJsonBody.setMsg(MallConstant.FAIL_CODE_NAME_CONFLICT_DESC);
            return responseJsonBody;
        }
        // 后台正则校验
        if (RegxVerify.checkout(username, RegxEnum.USERNAME) || RegxVerify.checkout(userpass, RegxEnum.PASSWORD)) {
            responseJsonBody.setCode(MallConstant.FAIL_CODE_ILLEGAL_FIELD);
            responseJsonBody.setMsg(MallConstant.FAIL_CODE_ILLEGAL_FIELD_DESC);
            return responseJsonBody;
        }
        // 写入到数据库
        String hashPwd = BCrypt.hashpw(userpass, BCrypt.gensalt());
        User newUser = new User();
        newUser.setName(username);
        newUser.setPassword(hashPwd);
        newUser.setRid(3);
        int flag = userMapper.insert(newUser);
        if (flag == 1) {
            responseJsonBody.setCode(MallConstant.SUCCESS_CODE);
            responseJsonBody.setMsg(MallConstant.REGISTER_SUCCESS);
        } else {
            responseJsonBody.setCode(MallConstant.FAIL_CODE_DB_ERROR);
            responseJsonBody.setMsg(MallConstant.FAIL_CODE_DB_ERROR_DESC);
        }
        return responseJsonBody;
    }
}
