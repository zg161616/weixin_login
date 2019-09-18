package com.cwc;

/**
 * @author bwh
 * @date 2019/9/16/016 - 11:08
 * @Description
 */
public class Routes extends com.jfinal.config.Routes {
    @Override
    public void config() {
        setBaseViewPath("/template");
        add("/",Controller.class);
    }
}
