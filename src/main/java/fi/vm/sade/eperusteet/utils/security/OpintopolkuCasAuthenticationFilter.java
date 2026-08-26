package fi.vm.sade.eperusteet.utils.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Extends Spring {@link CasAuthenticationFilter} so that it can obtain the ticket
 * from the {@code CasSecurityTicket} HTTP header in addition to the request parameter.
 *
 * Shared copy of {@code fi.vm.sade.java_utils.security.OpintopolkuCasAuthenticationFilter}
 * from opintopolku-cas-servlet-filter (built against Spring Security 6). That artifact
 * still references {@code org.springframework.security.cas.web.authentication.ServiceAuthenticationDetails},
 * which was moved in Spring Security 7 and causes {@code NoClassDefFoundError} at startup.
 */
public class OpintopolkuCasAuthenticationFilter extends CasAuthenticationFilter {

    public static final String CAS_SECURITY_TICKET = "CasSecurityTicket";

    public OpintopolkuCasAuthenticationFilter(ServiceProperties serviceProperties) {
        setServiceProperties(serviceProperties);
        setAuthenticationDetailsSource(new OpintopolkuServiceAuthenticationDetailsSource(serviceProperties));
    }

    @Override
    protected String obtainArtifact(HttpServletRequest request) {
        String casTicketHeader = request.getHeader(CAS_SECURITY_TICKET);
        if (casTicketHeader != null) {
            if (casTicketHeader.equals(getSessionTicket())) {
                logger.debug("ticket already authenticated in session: " + casTicketHeader);
                return null;
            }
            return casTicketHeader;
        }

        if ("POST".equals(request.getMethod())) {
            logger.debug("skipping cas obtainArtifact because post and already authenticated");
            return null;
        }

        return super.obtainArtifact(request);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Object sessionTicket = getSessionTicket();
        if (sessionTicket != null) {
            String requestTicket = obtainArtifact(request);
            boolean ticketChanged = requestTicket != null && !requestTicket.equals(sessionTicket);
            if (ticketChanged) {
                logger.warn("clear authentication because ticket changed, requestTicket: "
                        + requestTicket + ", sessionTicket: " + sessionTicket);
                SecurityContextHolder.clearContext();
            }
        }

        return super.requiresAuthentication(request, response);
    }

    private Object getSessionTicket() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getCredentials();
        }
        return null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        try {
            return super.attemptAuthentication(request, response);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException cause
                    && cause.getMessage() != null
                    && cause.getMessage().contains("412")
                    && cause.getMessage().contains("proxyValidate")) {
                throw new BadCredentialsException(
                        "Possible error with auth system or infra.. check: 1) configs, urls, ports, "
                                + "2) caller ticket not expired, 3) cas logs for req ticket: "
                                + obtainArtifact(request),
                        e);
            }
            throw e;
        }
    }
}
