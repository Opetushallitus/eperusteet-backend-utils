package fi.vm.sade.eperusteet.utils.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.ServiceAuthenticationDetails;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Spring Security 7 moved {@link ServiceAuthenticationDetails} from
 * {@code org.springframework.security.cas.web.authentication} to
 * {@code org.springframework.security.cas.authentication}. Shared copy of the
 * OPH filter helper so it compiles and links against Security 7.
 */
public class OpintopolkuServiceAuthenticationDetailsSource extends ServiceAuthenticationDetailsSource {

    private final ServiceProperties serviceProperties;

    public OpintopolkuServiceAuthenticationDetailsSource(ServiceProperties serviceProperties) {
        super(serviceProperties);
        this.serviceProperties = serviceProperties;
    }

    @Override
    public ServiceAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new OpintopolkuAuthenticationDetails(request, serviceProperties.getService());
    }

    public static class OpintopolkuAuthenticationDetails extends WebAuthenticationDetails
            implements ServiceAuthenticationDetails {

        private final String serviceUrl;

        public OpintopolkuAuthenticationDetails(HttpServletRequest request, String serviceUrl) {
            super(request);
            this.serviceUrl = serviceUrl;
        }

        @Override
        public String getServiceUrl() {
            return serviceUrl;
        }
    }
}
