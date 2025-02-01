package org.java.financial.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import java.time.Duration;
import java.util.Locale;

/**
 * Configuration for Internationalization (i18n)
 */
@Configuration
public class LocaleConfig {

    /**
     * Defines the default locale resolver using cookies.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH); // Default language

        // ✅ New approach: Set cookie attributes using ResponseCookie
        localeResolver.setCookiePath("/");                   // Available across the entire app
        localeResolver.setCookieHttpOnly(false);       // Allow JavaScript access if needed
        localeResolver.setCookieSecure(false);
        localeResolver.setCookieMaxAge(Duration.ofHours(1));// Set to true if using HTTPS
        return localeResolver;
    }

    /**
     * Intercepts requests and checks for a language parameter.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang"); // URL parameter to change language
        return localeInterceptor;
    }

    /**
     * Loads message properties files for i18n.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages"); // Refers to messages.properties
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
