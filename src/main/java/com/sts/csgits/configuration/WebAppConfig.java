package com.sts.csgits.configuration;


import com.sts.csgits.inc.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：gb
 * @date ：Created in 2020/3/10 21:19
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.servlet.multipart.location}")
    private String storageRootFolder;

    @Value("${spring.servlet.asset.virtual.path}")
    private String virtualPath;

    /**
     * 配置图片等资源虚拟路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath).addResourceLocations("file:" + storageRootFolder + "/", Const.IMAGE_VIRTUAL_PATH);
    }
}
