package in.transportstack.delhi.gatewayservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = defaultGrantedAuthoritiesConverter.convert(jwt);

        // Extract custom roles from the JWT token. Adjust the claim name as needed
        @SuppressWarnings("unchecked")
        List<GrantedAuthority> customAuthorities = ((List<String>) jwt.getClaim("cognito:groups"))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        grantedAuthorities.addAll(customAuthorities);
        return grantedAuthorities;
    }
}
