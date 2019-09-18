package com.cwc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bwh
 * @date 2019/9/16/016 - 16:40
 * @Description
 */
public class Handler extends com.jfinal.handler.Handler {
    @Override
    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean[] booleans) {

        System.out.println("hello");
    }
}
