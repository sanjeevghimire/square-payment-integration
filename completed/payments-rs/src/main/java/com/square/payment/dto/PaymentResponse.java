package com.square.payment.dto;

import com.squareup.square.models.Error;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse implements Serializable {
    private String status;
    private List<Error> errors;

    public PaymentResponse(String status, List<Error> errors) {
        this.status = status;
        this.errors = errors;
    }
}
