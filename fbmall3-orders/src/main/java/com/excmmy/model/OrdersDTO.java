package com.excmmy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.ProductsDetailsDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OrdersDTO", description="")
public class OrdersDTO implements Serializable {
    @ApiModelProperty(value = "订单ID")
    private Integer id;

    @ApiModelProperty(value = "订单创建时间")
    private Date createDate;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "订单状态码")
    private String statusCode;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "订单总价, 由前端填写")
    private String sumPrice;
}
