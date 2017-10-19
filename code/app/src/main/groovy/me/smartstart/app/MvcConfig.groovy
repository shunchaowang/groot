package me.smartstart.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

@Configuration
class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    LocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver()
        localeResolver.defaultLocale = new Locale("zh", "cn")
        return localeResolver
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor()
        interceptor.paramName = 'lang'
        return interceptor
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver()
        multipartResolver.maxUploadSize = 20971520 // 20MB
        multipartResolver.maxInMemorySize = 1048576;  // 1MB
        return multipartResolver
    }

    @Bean
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource()
        // load messages.properties from classpath, it can also be in other locations
        resource.basename = 'classpath:messages'
        resource.defaultEncoding = 'UTF-8'
        return resource
    }
}
