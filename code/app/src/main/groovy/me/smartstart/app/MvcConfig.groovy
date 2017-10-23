package me.smartstart.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

/**
 * To make Thymeleaf pick up localeResolver and messageSource, either name the beans
 * localeResolver and messageSource, I guess Thymeleaf is doing auto wiring by bean's qualifier, instead of type;
 * or name the methods be the name of localeResolver and messageSource, this will make beans' default name to be methods
 * name unless specified explicitly.
 */
@Configuration
class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
//(name = 'localeResolver')
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver()
//        localeResolver.defaultLocale = new Locale("zh", "cn") // the same with Locale.CHINA
        localeResolver.defaultLocale = Locale.CHINA
        return localeResolver
    }

    /**
     * add a lang interceptor to change the locale
     * @return LocaleChangeInterceptor
     */
    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor()
        interceptor.paramName = 'lang'
        return interceptor
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
//(name = "multipartResolver")
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver()
        multipartResolver.maxUploadSize = 20971520 // 20MB
        multipartResolver.maxInMemorySize = 1048576  // 1MB
        return multipartResolver
    }

    @Bean
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource()
        // load messages.properties from classpath, it can also be in other locations
        resource.basename = 'classpath:i18n/messages'
        resource.defaultEncoding = 'UTF-8'
        resource.cacheSeconds = 3600
        return resource
    }

    /**
     * {@inheritDoc}
     * <p>This implementation returns {@code null}.
     */
    @Override
    Validator getValidator() {
        return validator()
    }

    /**
     * Load ValidationMessages from classpath as a message source
     * @return
     */
    private ReloadableResourceBundleMessageSource validationMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource()
        // load ValidationMessages.properties from classpath, it can also be in other locations
        resource.basename = 'classpath:validation/ValidationMessages'
        resource.defaultEncoding = 'UTF-8'
        resource.cacheSeconds = 3600
        return resource
    }

    /**
     * create a validator factory from validation message source
     * @return
     */
    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean()
        bean.setValidationMessageSource(validationMessageSource())
        return bean
    }
}
