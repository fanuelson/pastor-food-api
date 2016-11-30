package com.foundation.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.foundation.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends GenericFilterBean {

	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		
		if(isLoginRequest(request) || isOptionMethodRequest(request)){
			chain.doFilter(req, res);
			return;
		}
		
		final String token = getRequestToken(request);

		try {
			final Claims claims = getTokenService().getClaims(token);
			final DateTime expirationDate = new DateTime(claims.getExpiration());
			final Period periodo = new Period(new DateTime(new Date()), new DateTime(expirationDate));
			final int standardMinutesRemaining = Math.abs(periodo.toStandardMinutes().getMinutes());
			if(standardMinutesRemaining >= 0 && standardMinutesRemaining < 5 ) {
				response.addHeader("refresh-token", getTokenService().refreshToken(claims));
			}
			response.addHeader("minutes-remaining", new Integer(standardMinutesRemaining).toString());
			chain.doFilter(req, res);
		} catch (final JwtException e) {
			response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.warn("JWT EXCEPTION: " + e.getMessage());
		} catch (final IllegalArgumentException ex) {
			response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.warn("TOKEN NULO");
		} 

	}
	
	private boolean isLoginRequest(HttpServletRequest request) {
		return request.getServletPath().equals("/api/auth/login");
	}
	
	private boolean isOptionMethodRequest(HttpServletRequest request) {
		return request.getMethod().equals(HttpMethod.OPTIONS.name());
	}

	private String getRequestToken(final HttpServletRequest request) throws ServletException {
		final String authHeader = request.getHeader("Authorization");
		return StringUtils.stripToNull(StringUtils.substringAfter(authHeader, "Bearer"));
	}
	
	private TokenService getTokenService() {
		return WebApplicationContextUtils.
				getRequiredWebApplicationContext(getServletContext()).
				getBean(TokenService.class);
	}

}
