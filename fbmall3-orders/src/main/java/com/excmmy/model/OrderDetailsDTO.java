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
@ApiModel(value="OrdersDTO", description="")
public class OrderDetailsDTO implements Serializable {
    @ApiModelProperty(value = "订单ID")
    private Integer id;

    @ApiModelProperty(value = "订单创建时间")
    private Date createDate;

    @ApiModelProperty(value = "订单支付时间")
    private Date payDate;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "订单状态码")
    private String statusCode;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "订单总价, 由前端填写")
    private String sumPrice;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "收货邮政编码")
    private String post;
}
