package com.excmmy.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Excmmy
 * @since 2020-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "外键 role_id")
    private Integer rid;

    @ApiModelProperty(value = "必备字段_创建时间")
    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "必备字段_修改时间")
    @TableField(value = "gmtModified", fill = FieldFill.UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "必备字段_逻辑删除")
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;


}
