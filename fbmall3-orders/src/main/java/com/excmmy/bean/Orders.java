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
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    @TableField("orderCode")
    private String orderCode;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮编")
    private String post;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "客户留言")
    @TableField("userMessage")
    private String userMessage;

    @ApiModelProperty(value = "订单创建时间")
    @TableField("createDate")
    private Date createDate;

    @ApiModelProperty(value = "订单支付时间")
    @TableField("payDate")
    private Date payDate;

    @ApiModelProperty(value = "订单发货时间")
    @TableField("deliveryDate")
    private Date deliveryDate;

    @ApiModelProperty(value = "确认收货时间")
    @TableField("confirmDate")
    private Date confirmDate;

    @ApiModelProperty(value = "外键 user_id")
    private Integer uid;

    @ApiModelProperty(value = "订单状态")
    private String status;

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
