package com.fastcampus.java.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum E_PaymentType {
    CARD(0, "credit card", "pay by credit card"),
    CASH(1, "cash", "pay by cash"),
    BANK(2, "bank", "pay by bank")
    ;
    private Integer Id;
    private String title;
    private String description;
}
