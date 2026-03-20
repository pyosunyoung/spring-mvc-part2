package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean // 필터를 등록하는 방법 bean에 등록
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);// 필터 순서?
        filterRegistrationBean.addUrlPatterns("/*"); // url 패턴은? 이렇게 하면 모든 url에 다 적용됨.

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);// 필터 순서?
        filterRegistrationBean.addUrlPatterns("/*"); // 화이트 필터 등록 함으로써 미래 까지 필터 적용되어지게 설정. 화이트 제외 모든 필터 적용되어지게 설정.

        return filterRegistrationBean;
    }
}
