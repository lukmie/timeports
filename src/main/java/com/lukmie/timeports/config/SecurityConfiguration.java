package com.lukmie.timeports.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
//                .fullyAuthenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
    }

//    #WeJit ldap
//    @Autowired
//    public void configureAuthenticationProviders(
//            Environment environment,
//            LdapProperties ldapProperties,
//            AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=users,dc=jitsolutions,dc=pl")
//                .groupSearchBase("ou=ldap_groups,dc=jitsolutions,dc=pl")
//                .groupRoleAttribute("cn")
//                .groupSearchFilter("(uniqueMember={0})")
//                .contextSource()
//                .url(ldapProperties.determineUrls(environment)[0])
//                .managerDn(ldapProperties.getUsername())
//                .managerPassword(ldapProperties.getPassword());
//    }

}
