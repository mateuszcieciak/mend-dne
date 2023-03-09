package com.cieciak.web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InputRequestBody {
    @NotNull(message = "seq is mandatory in request body")
    private int[] seq;
}
