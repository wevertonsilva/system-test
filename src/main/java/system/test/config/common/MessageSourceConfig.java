package system.test.config.common;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Locale.getDefault;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(UTF_8.name());
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(getDefault());
        return resolver;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(final MessageSource messageSource){
        return new MessageSourceAccessor(messageSource, getDefault());
    }

}