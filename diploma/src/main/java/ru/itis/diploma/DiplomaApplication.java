package ru.itis.diploma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.itis.diploma.utils.SpeechSynthesizeUtil;

@SpringBootApplication
@EnableWebMvc
public class DiplomaApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DiplomaApplication.class, args);
//        SpeechSynthesizeUtil synthesizeUtil = context.getBean(SpeechSynthesizeUtil.class);
//        synthesizeUtil.synthesizeVoice("Здарова, Самат) не, ничем таким не балуюсь)) пока, А можешь залить на github? В какую нибудь тест ветку Так удобнее будет) Хорошо, сказано, Самат) большое спасибо, и тебя с Днем защитника Отечества!");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        return resolver;
    }

}
