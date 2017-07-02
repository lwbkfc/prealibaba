package com.lin.alibaba.spring.boot.filters;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class LoginAccessFilter extends AccessControlFilter {

	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappe) throws Exception {

		Subject subject = this.getSubject(request, response);
		boolean access = subject.isAuthenticated();
		return access;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletResponse resp = (HttpServletResponse)response;

		resp.setStatus(401);
		return false;
	}

}
