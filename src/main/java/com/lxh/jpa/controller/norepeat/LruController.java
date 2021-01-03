package com.lxh.jpa.controller.norepeat;

import com.lxh.jpa.utils.norepeatlru.IdempotentUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注意：<br>
 * 1.DCL 适用于重复提交频繁比较高的业务场景，对于相反的业务场景下 DCL 并不适用。<br>
 * 2.LRUMap 的本质是持有头结点的环回双链表结构，当使用元素时，<br>
 * 就将该元素放在双链表 header 的前一个位置，在新增元素时，如果容量满了就会移除 header 的后一个元素。
 */
@RequestMapping("/lru")
@RestController
public class LruController {
    @RequestMapping("/add")
    public String addUser(String id) {
        if(!IdempotentUtils.judge(id, this.getClass())) {
            return "执行失败";
        }

        System.out.println("==> 增加用户成功，用户ID：" + id);
        return "执行成功";
    }
}
