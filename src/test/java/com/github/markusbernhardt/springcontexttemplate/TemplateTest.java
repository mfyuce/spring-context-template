package com.github.markusbernhardt.springcontexttemplate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.markusbernhardt.springcontexttemplate.beans.Container;
import com.github.markusbernhardt.springcontexttemplate.beans.Simple;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/github/markusbernhardt/springcontexttemplate/template-test.xml" })
public class TemplateTest {

	@Autowired
	protected ApplicationContext context;

	@Test
	public void testLoadContext() {
	}

	@Test
	public void testElementBeanName() {
		assertTrue("Bean simple-dev must be defined",
				context.containsBean("simple-dev"));
		assertTrue("Bean container-dev must be defined",
				context.containsBean("container-dev"));
	}

	@Test
	public void testElementBeanClass() {
		assertEquals(Simple.class, context.getBean("simple-dev").getClass());
		assertEquals(Container.class, context.getBean("container-dev")
				.getClass());
	}

	@Test
	public void testElementSimpleConstructorValue() {
		Simple simple = (Simple) context.getBean("simple-dev");

		assertEquals("constructorData.dev", simple.getConstructorValue());
		assertEquals("ExternalizedConstructorDev",
				simple.getExternalizedConstructorValue());
	}

	@Test
	public void testElementSimplePropertyValue() {
		Simple simple = (Simple) context.getBean("simple-dev");

		assertEquals("propertyData.dev", simple.getPropertyValue());
		assertEquals("ExternalizedPropertyDev",
				simple.getExternalizedPropertyValue());
	}

	@Test
	public void testElementContainerPropertyValue() {
		Container container = (Container) context.getBean("container-dev");
		Simple simple = (Simple) context.getBean("simple-dev");

		assertEquals(container.getBean(), simple);
		assertNotEquals(container.getInnerAnonymous(), simple);
	}

	@Test
	public void testElementContainerDependsOn() {
		BeanDefinition containerBeanDefinition = ((GenericApplicationContext) context)
				.getBeanFactory().getBeanDefinition("container-dev");
		assertArrayEquals(new String[] { "simple-dev" },
				containerBeanDefinition.getDependsOn());
	}

	@Test
	public void testAttributeBeanName() {
		assertTrue("Bean simple-test must be defined",
				context.containsBean("simple-test"));
		assertTrue("Bean container-test must be defined",
				context.containsBean("container-test"));
	}

	@Test
	public void testAttributeBeanClass() {
		assertEquals(Simple.class, context.getBean("simple-test").getClass());
		assertEquals(Container.class, context.getBean("container-test")
				.getClass());
	}

	@Test
	public void testAttributeSimpleConstructorValue() {
		Simple simple = (Simple) context.getBean("simple-test");

		assertEquals("constructorData.test", simple.getConstructorValue());
		assertEquals("ExternalizedConstructorTest",
				simple.getExternalizedConstructorValue());
	}

	@Test
	public void testAttributeSimplePropertyValue() {
		Simple simple = (Simple) context.getBean("simple-test");

		assertEquals("propertyData.test", simple.getPropertyValue());
		assertEquals("ExternalizedPropertyTest",
				simple.getExternalizedPropertyValue());
	}

	@Test
	public void testAttributeContainerPropertyValue() {
		Container container = (Container) context.getBean("container-test");
		Simple simple = (Simple) context.getBean("simple-test");

		assertEquals(container.getBean(), simple);
		assertNotEquals(container.getInnerAnonymous(), simple);
	}

	@Test
	public void testAttributeContainerDependsOn() {
		BeanDefinition containerBeanDefinition = ((GenericApplicationContext) context)
				.getBeanFactory().getBeanDefinition("container-test");
		assertArrayEquals(new String[] { "simple-test" },
				containerBeanDefinition.getDependsOn());
	}
}
