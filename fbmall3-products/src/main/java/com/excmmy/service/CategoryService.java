package com.excmmy.service;

import com.excmmy.bean.Category;
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
public interface CategoryService extends IService<Category> {
    ResponseJsonBody getCategory(Integer current, Integer size);
    ResponseJsonBody getMainMenu(Integer categoryId);
}
