package com.lxh.jpa.controller.norepeat;

import com.lxh.jpa.utils.norepeatsubmit.annotation.NoRepeatSubmit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    @NoRepeatSubmit
    public String test() {
        return "程序逻辑返回";
    }
}
