package com.snda.sdo.openid.infrastructure.guice;

import java.util.Properties;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.googlecode.guicehibernate.event.def.TimeSettingSaveEventListener;
import com.googlecode.guicehibernate.event.def.TimeSettingUpdateEventListener;
import com.googlecode.guicespring.support.PropertiesLoaders;

public abstract class HibernateModules {

    public static Module create(final String... packages) {
        return new AbstractModule() {
            protected void configure() {
                try {
                    LocalSessionFactoryBean sessionFactoryBean = bindSessionFactoryBean();
                    SessionFactory sessionFactory = sessionFactoryBean.getObject();
                    bind(SessionFactory.class).toInstance(sessionFactory);
                    bind(HibernateOperations.class).toInstance(new HibernateTemplate(sessionFactory));

                    PlatformTransactionManager txManager = transactionManager(sessionFactory);
                    bind(PlatformTransactionManager.class).toInstance(txManager);
                    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor(txManager));
                } catch (Exception e) {
                    Throwables.propagate(e);
                }
            }

            private LocalSessionFactoryBean bindSessionFactoryBean() throws Exception {
                Properties hibernateProperties = PropertiesLoaders.classpath("jdbc.properties");
                DataSource dataSource = JdbcModules.dataSource(hibernateProperties);
                bind(DataSource.class).toInstance(dataSource);
                LocalSessionFactoryBean sessionFactoryBean = sessionFactoryBean(dataSource, hibernateProperties);
                bind(LocalSessionFactoryBean.class).toInstance(sessionFactoryBean);
                return sessionFactoryBean;
            }

            private LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource, Properties properties) throws Exception {
                AnnotationSessionFactoryBean sfb = new AnnotationSessionFactoryBean();
                sfb.setDataSource(dataSource);
                sfb.setNamingStrategy(new ImprovedNamingStrategy());
                sfb.setSchemaUpdate(true);
                sfb.setPackagesToScan(packages);
                String[] hibernateProperties = {Environment.HBM2DDL_AUTO, Environment.DIALECT, Environment.SHOW_SQL};
                for (String p : hibernateProperties) {
                    sfb.getHibernateProperties().setProperty(p, properties.getProperty(p));
                }
                sfb.getHibernateProperties().setProperty(Environment.FORMAT_SQL, "true");
                sfb.setEventListeners(ImmutableMap.<String, Object>of(
                	"save", new TimeSettingSaveEventListener(),
                	"update", new TimeSettingUpdateEventListener()
                ));
                sfb.afterPropertiesSet();
                return sfb;
            }
        };
    }


    private static MethodInterceptor transactionInterceptor(PlatformTransactionManager tm) {
        return new TransactionInterceptor(tm, new AnnotationTransactionAttributeSource());
    }

    private static PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }


}
