package org.ruthvik.config;

import org.aeonbits.owner.Config;
import org.ruthvik.enums.Environment;
import org.ruthvik.config.converters.StringToEnvironmentConverter;

@Config.Sources({
        "file:./src/test/resources/config/env.properties"
})
public interface ConfigReader extends Config {
    @Key("BASE_URL")
    String baseUrl();

    @Key("ENV")
    @ConverterClass(StringToEnvironmentConverter.class)
    Environment env();

    @Key("PROD_USER_ENDPOINT")
    String userEndpoint();

    @Key("PROD_AUTH_LOGIN_ENDPOINT")
    String authLoginEndpoint();
    @Key("PROD_PRODUCTS_ENDPOINT")
    String productsEndpoint();

    @Key("PROD_AUTH_VIEW_PROFILE")
    String authViewProfile();
}

