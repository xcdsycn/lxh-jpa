package com.lxh.jpa.controller.norepeat;

import lombok.Data;

@Data
public class ApiResult {
    private int i;
    private String mesg;
    private String userId;
    public ApiResult(int i, String 成功, String userId) {
    }
}
