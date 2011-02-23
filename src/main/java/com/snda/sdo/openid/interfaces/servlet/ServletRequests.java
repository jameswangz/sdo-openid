/**
 * 
 * Copyright (c) 2010 Shanda Corporation. All rights reserved.
 *
 */

package com.snda.sdo.openid.interfaces.servlet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * 
 * @author wangzheng.james@snda.com (James Wang)
 * @since 2010-12-31
 */
public abstract class ServletRequests {

	public static String remoteAddrOf(HttpServletRequest request) {
        String ip = null;
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null) {
            String[] forwardeds = forwarded.split(",");
            if (StringUtils.hasText(forwardeds[0])) {
                String tempIp = forwardeds[0].trim();
                if (tempIp.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                    ip = tempIp;
                }
            }
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
	}

}
