package by.pochem.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebAppSecurityConfig.class)
@ComponentScan(basePackages= {"by.pochem.web.auth"})
public class WebAppContextConfig {
}
