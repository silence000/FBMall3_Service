package com.excmmy.service;

import com.excmmy.bean.Propertyvalue;
import com.baomidou.mybatisplus.extension.service.IService;
import pojo.ResponseJsonBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-20
 */
public interface PropertyvalueService extends IService<Propertyvalue> {
    ResponseJsonBody getProductExtra(Integer id);
}
