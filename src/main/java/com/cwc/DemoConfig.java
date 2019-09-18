package com.cwc;


import com.jfinal.config.*;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

/**
 * @author bwh
 * @date 2019/9/16/016 - 10:53
 * @Description
 */
public class DemoConfig extends JFinalConfig{
    static Prop p;

    static{
        p = PropKit.use("config-dev.txt");
    }
    @Override
    public void configConstant(Constants constants) {
        constants.setDevMode(true);
        constants.setInjectDependency(true);
    }

    @Override
    public void configRoute(com.jfinal.config.Routes routes) {
        routes.add(new Routes());
    }


    @Override
    public void configEngine(Engine engine) {
    }

    @Override
    public void configPlugin(Plugins plugins) {

        DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
        plugins.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        _MappingKit.mapping(arp);
        plugins.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {
    }

    public static void main(String[] args) {
        UndertowServer.start(DemoConfig.class,80,true);
    }
}
