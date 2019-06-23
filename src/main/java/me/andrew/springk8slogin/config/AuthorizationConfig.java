package me.andrew.springk8slogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager; //실제 인증 서버

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception{
        configurer
                .inMemory()
                .withClient("spring-k8s-login-client") //app-name
                .secret(passwordEncoder.encode("password"))
                .authorizedGrantTypes("password",
                        "authorization_code",
                        "refresh_token",
                        "implicit")
                .scopes("read","write","trust")
                .accessTokenValiditySeconds(1*60*60)
                .refreshTokenValiditySeconds(6*60*60);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception {
        endpointsConfigurer
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager);

    }

}
