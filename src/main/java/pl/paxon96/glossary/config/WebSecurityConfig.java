package pl.paxon96.glossary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login as principal, pass as credentials, true from users where login = ?")
                .authoritiesByUsernameQuery("select users.login as principal, users.permission as role from users where login=?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/", "/index").permitAll()
//                .antMatchers("/produkty/dodaj").access("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/klienci/dodaj").access("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')or hasRole('ROLE_ADVANCED')")
//                .antMatchers("/kategorie/dodaj").access("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')or hasRole('ROLE_ADVANCED')")
//                .antMatchers("/dostawcy/dodaj").access("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/dostawy/**").access("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')or hasRole('ROLE_ADVANCED')")
//                .antMatchers("/administrator","/register","/pracownicy").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .defaultSuccessUrl("/",true)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
//                .and()
//                .exceptionHandling().accessDeniedPage("/error/brakdostepu");
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
