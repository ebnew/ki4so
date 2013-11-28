package com.github.ebnew.ki4so.core.authentication.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ebnew.ki4so.core.app.App;
import com.github.ebnew.ki4so.core.app.AppServiceImpl;

public class AppServiceImplTest {
	
	private AppServiceImpl appServiceImpl;

	@Before
	public void setUp() throws Exception {
		appServiceImpl = new AppServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAppById() {
		Assert.assertNull(appServiceImpl.findAppById(""));
		Assert.assertNull(appServiceImpl.findAppById(null));
		
		Assert.assertNull(appServiceImpl.findAppById("not exsited"));
		
		App app = appServiceImpl.findAppById("1001");
		Assert.assertNotNull(app);
		Assert.assertEquals("http://localhost:8080/ki4so-app/", app.getHost());
		System.out.println(app);
	}

	@Test
	public void testFindKi4soServerApp() {
		App app = appServiceImpl.findKi4soServerApp();
		Assert.assertNotNull(app);
		System.out.println(app);
	}

	@Test
	public void testFindAppByHost() {
		//查询异常情况。
		Assert.assertNull(appServiceImpl.findAppByHost(""));
		Assert.assertNull(appServiceImpl.findAppByHost(null));
		
		Assert.assertNull(appServiceImpl.findAppByHost("ddddddddddd"));
		
		//查询带斜线和不带斜线的情况。
		App app = appServiceImpl.findAppByHost("http://localhost:8080/ki4so-app/");
		Assert.assertNotNull(app);
		Assert.assertEquals("1001", app.getAppId());
		
		app = appServiceImpl.findAppByHost("http://localhost:8080/ki4so-app");
		Assert.assertNotNull(app);
		Assert.assertEquals("1001", app.getAppId());
		
		app = appServiceImpl.findAppByHost("http://localhost:8080/ki4so-app/dafdasfdas");
		Assert.assertNotNull(app);
		Assert.assertEquals("1001", app.getAppId());
		
		app = appServiceImpl.findAppByHost("http://localhost:8080/ki4so-app/hell/w3d.htm?a=b");
		Assert.assertNotNull(app);
		Assert.assertEquals("1001", app.getAppId());
	}
}
