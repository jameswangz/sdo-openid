package com.snda.sdo.openid.infrastructure.guice;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.util.HttpCache;

import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.snda.sdo.openid.domain.model.UserRepository;
import com.snda.sdo.openid.domain.service.AccountRegister;
import com.snda.sdo.openid.domain.service.AuditService;
import com.snda.sdo.openid.domain.service.CasService;
import com.snda.sdo.openid.domain.service.ErrorReporter;
import com.snda.sdo.openid.domain.service.OpenIdProfileService;
import com.snda.sdo.openid.domain.service.PermissionManager;
import com.snda.sdo.openid.domain.service.RegisterService;
import com.snda.sdo.openid.domain.service.TimeService;
import com.snda.sdo.openid.domain.service.impl.AuditServiceImpl;
import com.snda.sdo.openid.domain.service.impl.CasServiceImpl;
import com.snda.sdo.openid.domain.service.impl.LocalTimeService;
import com.snda.sdo.openid.domain.service.impl.OpenIdProfileServiceImpl;
import com.snda.sdo.openid.domain.service.impl.PermissionManagerImpl;
import com.snda.sdo.openid.domain.service.impl.RegisterServiceImpl;
import com.snda.sdo.openid.infrastructure.openid.ConfigurableYadisResolver;
import com.snda.sdo.openid.infrastructure.openid.HttpCacheConfigurer;
import com.snda.sdo.openid.infrastructure.persistence.hibernate.HibernateUserRepository;
import com.snda.sdo.openid.infrastructure.remote.http.HttpAccountRegister;
import com.snda.sdo.openid.infrastructure.remote.http.HttpErrorReporter;

public abstract class DomainModules {

	private static final int CONNECTION_TIMEOUT = 60000;
	private static final int SOCKET_TIMEOUT = 60000;

	public static Module create() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(ConsumerManager.class).toInstance(consumerManager());
				bind(OpenIdProfileService.class).to(OpenIdProfileServiceImpl.class).in(Scopes.SINGLETON);
				bind(RegisterService.class).to(RegisterServiceImpl.class).in(Scopes.SINGLETON);
				bind(CasService.class).to(CasServiceImpl.class).in(Scopes.SINGLETON);
				bind(UserRepository.class).to(HibernateUserRepository.class).in(Scopes.SINGLETON);
				bind(AccountRegister.class).to(HttpAccountRegister.class).in(Scopes.SINGLETON);
				bind(PermissionManager.class).to(PermissionManagerImpl.class).in(Scopes.SINGLETON);
				bind(AuditService.class).to(AuditServiceImpl.class).in(Scopes.SINGLETON);
				bind(TimeService.class).to(LocalTimeService.class).in(Scopes.SINGLETON);
				bind(ErrorReporter.class).to(HttpErrorReporter.class).in(Scopes.SINGLETON);
			}
		};
	}

	private static ConsumerManager consumerManager() {
		try {
			ConsumerManager consumerManager = new ConsumerManager();
			consumerManager.setDiscovery(discovery());
			consumerManager.setAssociations(new InMemoryConsumerAssociationStore());
			consumerManager.setNonceVerifier(new InMemoryNonceVerifier(Integer.parseInt(EnvironmentModules.get("nonce.maxAgeSeconds"))));
			consumerManager.setConnectTimeout(CONNECTION_TIMEOUT);
			consumerManager.setSocketTimeout(SOCKET_TIMEOUT);
			return consumerManager;
		} catch (ConsumerException e) {
			throw Throwables.propagate(e);
		}
	}

	private static Discovery discovery() {
		Discovery discovery = new Discovery();
		YadisResolver yadisResolver = new ConfigurableYadisResolver(new HttpCacheConfigurer() {
			@Override
			public void configurer(HttpCache httpCache) {
				httpCache.getDefaultRequestOptions().setConnTimeout(CONNECTION_TIMEOUT);
				httpCache.getDefaultRequestOptions().setSocketTimeout(SOCKET_TIMEOUT);
			}
		});
		discovery.setYadisResolver(yadisResolver);
		return discovery;
	}
	
}
