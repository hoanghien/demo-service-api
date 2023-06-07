package aaa.bbb.ccc.config;

import aaa.bbb.ccc.common.AppContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"aaa.bbb.ccc"})
//@ImportResource(value = { "classpath:applicationContext.xml" })
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Primary
    public AppContext appContext() {
        return new AppContext();
    }
}
