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
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Product对象", description="")
public class Product implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "小标题")
    @TableField("subTitle")
    private String subTitle;

    @ApiModelProperty(value = "原始价格")
    @TableField("originalPrice")
    private Float originalPrice;

    @ApiModelProperty(value = "促销价格")
    @TableField("promotePrice")
    private Float promotePrice;

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "外键 category_id")
    private Integer cid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createDate")
    private Date createDate;

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
