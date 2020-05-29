package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RecInfo", description="")
public class RecInfoDTO implements Serializable {
    @ApiModelProperty(value = "收货人姓名")
    private String recName;

    @ApiModelProperty(value = "收货人手机号码")
    private String recPhone;

    @ApiModelProperty(value = "邮政编码")
    private String postcode;

    @ApiModelProperty(value = "收货人地址")
    private String recAddress;

    @ApiModelProperty(value = "卖家备注")
    private String remark;

    @ApiModelProperty(value = "提交的商品Id")
    private String productsId;
}
