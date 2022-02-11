package com.square.payment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest implements Serializable {

    @NotNull
    private Long amount;
    @NotNull
    private String token;
    @NotNull
    private String locationId;
}
