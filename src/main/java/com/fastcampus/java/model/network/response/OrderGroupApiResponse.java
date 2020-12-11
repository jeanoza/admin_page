package com.fastcampus.java.model.network.response;

import com.fastcampus.java.model.enumclass.E_OrderType;
import com.fastcampus.java.model.enumclass.E_PaymentType;
import com.fastcampus.java.model.enumclass.E_Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderGroupApiResponse {

    private Long id;
    private E_Status status;
    private E_OrderType orderType;
    private String revAddress;
    private String revName;
    private E_PaymentType paymentType;
    private BigDecimal totalPrice;
    private Integer totalQuantity;
    private LocalDateTime orderAt;
    private LocalDateTime arrivalDate;
    private Long userId;

}
