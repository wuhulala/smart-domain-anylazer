package com.wuhulala.sda.controller;

import com.wuhulala.rest.base.BaseResult;
import com.wuhulala.rest.controller.AbstractRestProxyController;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhulala<br>
 * @date 2019/6/24<br>
 * @since v1.0<br>
 */
@RestController
public class CenterController extends AbstractRestProxyController {

    @Override
    public Object handlerResult(BaseResult baseResult) {
        return baseResult.getData();
    }
}
