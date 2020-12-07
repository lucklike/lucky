package com.lucky.web.beanfactory;

import com.lucky.framework.AutoScanApplicationContext;
import com.lucky.framework.container.Module;
import com.lucky.framework.container.factory.Destroy;
import com.lucky.framework.uitls.base.Assert;
import com.lucky.web.mapping.UrlMappingCollection;

/**
 * @author fk7075
 * @version 1.0.0
 * @date 2020/12/6 下午7:42
 */
public class WebDestroy implements Destroy {

    @Override
    public void destroy() {
        Module module = AutoScanApplicationContext.create().getModule("urlMappingCollection");
        if(Assert.isNotNull(module)){
            UrlMappingCollection urlMappingCollection = (UrlMappingCollection) module.getComponent();
            urlMappingCollection.closeRun();
        }
    }
}
