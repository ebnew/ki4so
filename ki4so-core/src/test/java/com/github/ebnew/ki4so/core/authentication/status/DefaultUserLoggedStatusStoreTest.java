package com.github.ebnew.ki4so.core.authentication.status;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultUserLoggedStatusStoreTest {
	
	private DefaultUserLoggedStatusStore defaultUserLoggedStatusStore;

	@Before
	public void setUp() throws Exception {
		defaultUserLoggedStatusStore = new DefaultUserLoggedStatusStore();
	}

	@After
	public void tearDown() throws Exception {
		defaultUserLoggedStatusStore = null;
	}

	@Test
	public void testAddUserLoggedStatus() {
		//测试加入空对象的情况。
		defaultUserLoggedStatusStore.addUserLoggedStatus(null);
		//校验结果。
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getLoggedStatus().size());
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getUserIdIndexMap().size());
		
		//测试加入对象中有不合法的属性值的情况。
		String userId= "test";
		UserLoggedStatus userLoggedStatus = new UserLoggedStatus(userId, "");
		defaultUserLoggedStatusStore.addUserLoggedStatus(userLoggedStatus);
		//校验结果。
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getLoggedStatus().size());
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getUserIdIndexMap().size());
		
		userLoggedStatus = new UserLoggedStatus("", "");
		defaultUserLoggedStatusStore.addUserLoggedStatus(userLoggedStatus);
		//校验结果。
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getLoggedStatus().size());
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getUserIdIndexMap().size());
		
		//测试正常数据的情况。
		String appId = "1001";
		userLoggedStatus = new UserLoggedStatus(userId, appId);
		defaultUserLoggedStatusStore.addUserLoggedStatus(userLoggedStatus);
		//校验结果。
		//有数据。
		Assert.assertEquals(1, defaultUserLoggedStatusStore.getLoggedStatus().size());
		//有数据。
		Assert.assertEquals(1, defaultUserLoggedStatusStore.getUserIdIndexMap().size());
		Assert.assertEquals(userId, defaultUserLoggedStatusStore.findUserLoggedStatus(userId).get(0).getUserId());
		
	}

	@Test
	public void testDeleteUserLoggedStatus() {
		String userId= "test";
		String appId = "1001";
		
		//测试删除异常数据。
		defaultUserLoggedStatusStore.deleteUserLoggedStatus("", "");
		
		//测试删除异常数据。
		defaultUserLoggedStatusStore.deleteUserLoggedStatus(userId, "");
		
		//测试正常数据。
		defaultUserLoggedStatusStore.deleteUserLoggedStatus(userId, appId);
	}

	@Test
	public void testClearUpUserLoggedStatus() {
		String userId= "test";
		
		//测试清楚异常数据情况。
		defaultUserLoggedStatusStore.clearUpUserLoggedStatus(null);
		defaultUserLoggedStatusStore.clearUpUserLoggedStatus("");
		
		//测试正常数据。
		defaultUserLoggedStatusStore.clearUpUserLoggedStatus(userId);
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getLoggedStatus().size());
		//没有数据。
		Assert.assertEquals(0, defaultUserLoggedStatusStore.getUserIdIndexMap().size());
		
	}

	@Test
	public void testFindUserLoggedStatus() {
		String userId= "test";
		
		//测试查询异常数据情况。
		Assert.assertNull(defaultUserLoggedStatusStore.findUserLoggedStatus(null));
		Assert.assertNull(defaultUserLoggedStatusStore.findUserLoggedStatus(""));
		Assert.assertNull(defaultUserLoggedStatusStore.findUserLoggedStatus(userId));
	}
	
	/**
	 * 将相关的CRUD方法集成起来测试。只测试正常情况。
	 */
	@Test
	public void testAll(){
		DefaultUserLoggedStatusStore userLoggedStatusStore = new DefaultUserLoggedStatusStore();
		
		//添加3条数据。
		userLoggedStatusStore.addUserLoggedStatus(new UserLoggedStatus("test1", "1000", new Date()));
		userLoggedStatusStore.addUserLoggedStatus(new UserLoggedStatus("test1", "1001", new Date()));
		userLoggedStatusStore.addUserLoggedStatus(new UserLoggedStatus("test2", "1000", new Date()));
		
		//查询其中一个用户。
		List<UserLoggedStatus> list = userLoggedStatusStore.findUserLoggedStatus("test1");
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("1000", list.get(0).getAppId());
		Assert.assertEquals("1001", list.get(1).getAppId());
		
		//删除一条数据。
		userLoggedStatusStore.deleteUserLoggedStatus("test1", "1001");
		list = userLoggedStatusStore.findUserLoggedStatus("test1");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("1000", list.get(0).getAppId());
		
		
		//清空数据。
		userLoggedStatusStore.clearUpUserLoggedStatus("test1");
		list = userLoggedStatusStore.findUserLoggedStatus("test1");
		Assert.assertNull(list);
		
		userLoggedStatusStore.clearUpUserLoggedStatus("test2");
		list = userLoggedStatusStore.findUserLoggedStatus("test2");
		Assert.assertNull(list);
	}

}
