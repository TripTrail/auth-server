package com.company.application.authserver.config;

import static com.company.application.authserver.constant.Constant.*;

import com.company.application.co.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    @Qualifier(AUTH_MANAGER_BEAN)
    private AuthenticationManager authenticationManager;

    @Value(OAUTH_CLIENT_ID)
    private String clientId;

    @Value(OAUTH_CLIENT_SECRET)
    private String secret;

    @Value(OAUTH_CLIENT_GRANT_TYPES)
    private String[] grantType;

    @Value(OAUTH_CLIENT_SCOPE)
    private String[] scope;

    @Value(OAUTH_CLIENT_ACCESS_TOKEN_VALIDITY)
    private int accessTokenValidity;

    @Value(OAUTH_CLIENT_REFRESH_TOKEN_VALIDITY)
    private int refreshTokenValidity;

    @Autowired
    @Qualifier(DATASOURCE)
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
      throws Exception {
        clients.jdbc(dataSource)
                .withClient(clientId)
                .secret(secret)
                .authorizedGrantTypes(grantType)
                .scopes(scope)
                .accessTokenValiditySeconds(accessTokenValidity)
                .refreshTokenValiditySeconds(refreshTokenValidity);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .exceptionTranslator(exception -> {
                    APIResponse<String> response = new APIResponse<>(false, exception.getMessage(), NULL);
                    return new ResponseEntity(response, HttpStatus.OK);
                });
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier(DATASOURCE) DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    @Value(SCHEMA_SCRIPT)
    private Resource schemaScript;

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(schemaScript);
        return databasePopulator;
    }

}
