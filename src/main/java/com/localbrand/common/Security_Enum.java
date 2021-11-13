package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Security_Enum {

    SECRET("secret");

    @Getter
    private String secret;
}
