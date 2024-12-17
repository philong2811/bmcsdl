package hcmute.it.oracleDB.Config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import hcmute.it.oracleDB.Common.TypeUserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  private final AuthenticationProvider authenticationProvider;

  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }

  @Bean
  public SecurityFilterChain applicationSecutiry(HttpSecurity http) throws Exception{
    http
        .cors().and()
        .csrf().disable()
        .authorizeHttpRequests()
          .requestMatchers(antMatcher("/api/auth/**")).permitAll()
          .requestMatchers(antMatcher("/user/**")).hasAuthority(TypeUserEnum.USER.name())
          .requestMatchers(antMatcher("/admin/**")).hasAuthority(TypeUserEnum.ADMIN.name())
          .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
