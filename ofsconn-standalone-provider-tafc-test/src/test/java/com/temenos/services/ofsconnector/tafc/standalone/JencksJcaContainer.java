package com.temenos.services.ofsconnector.tafc.standalone;

import javax.naming.NamingException;

import org.jencks.JCAContainer;
import org.jencks.factory.BootstrapContextFactoryBean;
import org.jencks.factory.ConnectionFactoryFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.temenos.tocf.t24ra.T24ResourceAdapter;
import com.temenos.tocf.t24ra.outbound.T24ManagedConnectionFactory;

/**
 * A simple spring based JCA container to run the T24 RA to provide connectivity
 * to T24.
 * 
 */
public class JencksJcaContainer {

	private String hosts;
	private String ports;
	private String ofsSource;

	private GenericApplicationContext ctx = null;

	public JencksJcaContainer(String hosts, String ports, String ofsSource) {
		this.hosts = hosts;
		this.ports = ports;
		this.ofsSource = ofsSource;
	}

	public void load() {
		if (ctx == null) {
			buildContext();
			provideJndiCf();
		}
	}

	private void buildContext() {
		ctx = new GenericApplicationContext();
		buildContainer();
		buildConnectionFactory();
	}

	private void buildContainer() {
		BeanDefinitionBuilder containerBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(JCAContainer.class);
		containerBuilder.addPropertyReference("bootstrapContext",
				buildBootstrapContext());
		containerBuilder.addPropertyReference("resourceAdapter",
				buildT24ResourceAdapter());
		String beanName = "JcaContainer";
		ctx.registerBeanDefinition(beanName, containerBuilder
				.getBeanDefinition());
	}

	private String buildBootstrapContext() {
		BeanDefinitionBuilder bootstrapContext = BeanDefinitionBuilder
				.rootBeanDefinition(BootstrapContextFactoryBean.class);
		bootstrapContext.addPropertyValue("threadPoolSize", 5);
		String beanName = "bootstrapContext";
		ctx.registerBeanDefinition(beanName, bootstrapContext
				.getBeanDefinition());
		return beanName;
	}

	private String buildT24ResourceAdapter() {
		BeanDefinitionBuilder t24RaBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(T24ResourceAdapter.class);
		String beanName = "t24ResourceAdapter";
		ctx.registerBeanDefinition(beanName, t24RaBuilder.getBeanDefinition());
		return beanName;
	}

	private void buildConnectionFactory() {
		BeanDefinitionBuilder cfBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(ConnectionFactoryFactoryBean.class);
		String beanName = "t24ConnectionFactory";
		cfBuilder.addPropertyReference("managedConnectionFactory",
				buildManagedConnectionFactory());
		ctx.registerBeanDefinition(beanName, cfBuilder.getBeanDefinition());
	}

	private String buildManagedConnectionFactory() {
		BeanDefinitionBuilder mcfBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(T24ManagedConnectionFactory.class);
		mcfBuilder.addPropertyValue("hosts", hosts);
		mcfBuilder.addPropertyValue("ports", ports);
		mcfBuilder.addPropertyValue("envVariables", "OFS_SOURCE=" + ofsSource);
		String beanName = "t24ManagedConnetionFactory";
		ctx.registerBeanDefinition(beanName, mcfBuilder.getBeanDefinition());
		return beanName;
	}

	private void provideJndiCf() {
		Object connectionFactory = ctx.getBean("t24ConnectionFactory");
		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		builder.bind("java:comp/env/jca/t24ConnectionFactory",
				connectionFactory);
		try {
			builder.activate();
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
}