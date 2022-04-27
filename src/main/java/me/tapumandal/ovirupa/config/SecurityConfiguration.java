package me.tapumandal.ovirupa.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import me.tapumandal.ovirupa.filters.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private String apiVersionUrl = "/api/v1";

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


                http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(apiVersionUrl+"/public/**").permitAll()
                .antMatchers(apiVersionUrl+"/payment/confirmation").permitAll()
                .antMatchers(apiVersionUrl+"/admin/registration").permitAll()
                .antMatchers(apiVersionUrl+"/admin/authenticate").permitAll()
                .antMatchers(apiVersionUrl+"/admin/list").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/consumer/registration").permitAll()
                .antMatchers(apiVersionUrl+"/consumer/authenticate").permitAll()
                .antMatchers(apiVersionUrl+"/consumer/access").permitAll()
                .antMatchers(apiVersionUrl+"/sms/otp").permitAll()
                .antMatchers(apiVersionUrl+"/notification/notify").permitAll()
                .antMatchers("/socket/**").permitAll()
                .antMatchers("/sockjs-node/**").permitAll()
                .antMatchers("/topic/notification").hasAnyAuthority("ADMIN")
                .antMatchers("/cloud_messaging/consumer/send").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/consumer/address/update").hasAnyAuthority("CONSUMER")
                .antMatchers(apiVersionUrl+"/consumer/myprofile").hasAnyAuthority("CONSUMER")
                .antMatchers(apiVersionUrl+"/payment/request").permitAll()
                .antMatchers(apiVersionUrl+"/cart/consumer/create").permitAll()
                .antMatchers(apiVersionUrl+"/cart/consumer/list").permitAll()
                .antMatchers(apiVersionUrl+"/consumer/{user_id}/cart/list").hasAnyAuthority("ADMIN", "CONSUMER")
                .antMatchers(apiVersionUrl+"/product/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/list").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/update").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/activate/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/deactivate/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/product/consumer/{id}").hasAnyAuthority("ADMIN","CONSUMER")
                .antMatchers(apiVersionUrl+"/product/consumer/**").permitAll()
                .antMatchers(apiVersionUrl+"/product/consumer/name/{name}").hasAnyAuthority("ADMIN","CONSUMER")
                .antMatchers(apiVersionUrl+"/product/consumer/list/{flag}/{selectedParentMenu}").permitAll()
                .antMatchers(apiVersionUrl+"/product/consumer/search/{query}").permitAll()
                .antMatchers(apiVersionUrl+"/business_settings/get").permitAll()
                .antMatchers(apiVersionUrl+"/business_settings/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/home-management/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/home-management/get").permitAll()
                .antMatchers(apiVersionUrl+"/bootstrap/basic").permitAll()
                .antMatchers(apiVersionUrl+"/navigation/get").permitAll()
                .antMatchers(apiVersionUrl+"/navigation/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/slider/**").permitAll()
                .antMatchers(apiVersionUrl+"/slider/get").permitAll()
                .antMatchers(apiVersionUrl+"/highlight/**").permitAll()
                .antMatchers(apiVersionUrl+"/highlight/get").permitAll()
                .antMatchers(apiVersionUrl+"/live/**").permitAll()
                .antMatchers(apiVersionUrl+"/live/get").permitAll()
                .antMatchers(apiVersionUrl+"/dashboard").hasAnyAuthority("ADMIN", "CONSUMER")
                .antMatchers(apiVersionUrl+"/admin").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/user").hasAnyAuthority("CONSUMER")
                .antMatchers(apiVersionUrl+"/user/**").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/company/**").hasAnyAuthority("ADMIN", "CONSUMER")
                .antMatchers(apiVersionUrl+"/promotion/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/promotion/list").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/promotion/consumer/apply/{code}").hasAnyAuthority("CONSUMER")
                .antMatchers(apiVersionUrl+"/fcm_registration_token/consumer/create").permitAll()
                .antMatchers(apiVersionUrl+"/public/images/**").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/image/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/images/blog/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/**").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/create").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/list").hasAnyAuthority("ADMIN", "CONSUMER")
                .antMatchers(apiVersionUrl+"/blog/update").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/{id}").hasAnyAuthority("ADMIN", "CONSUMER")
                .antMatchers(apiVersionUrl+"/blog/activate/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/deactivate/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(apiVersionUrl+"/blog/consumer/**").permitAll()
                .antMatchers(apiVersionUrl+"/online/payment/check").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


//                .anyRequest().authenticated()
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}