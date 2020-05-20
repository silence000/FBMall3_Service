package com.excmmy.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateInsert = formatter.format(date);
        try {
            this.setFieldValByName("gmtCreate", formatter.parse(dateInsert), metaObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateInsert = formatter.format(date);
        try {
            this.setFieldValByName("gmtModified", formatter.parse(dateInsert), metaObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
