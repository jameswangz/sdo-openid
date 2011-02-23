package com.snda.sdo.openid.infrastructure.openid;

import java.util.List;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.util.HttpCache;

public class ConfigurableYadisResolver extends YadisResolver {

	private final HttpCacheConfigurer httpCacheConfigurer;

	public ConfigurableYadisResolver(HttpCacheConfigurer httpCacheConfigurer) {
		this.httpCacheConfigurer = httpCacheConfigurer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List discover(String url, HttpCache cache) throws DiscoveryException {
		httpCacheConfigurer.configurer(cache);
		return super.discover(url, cache);
	}

	
	
	
}
