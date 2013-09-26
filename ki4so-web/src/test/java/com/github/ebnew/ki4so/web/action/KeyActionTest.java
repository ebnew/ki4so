package com.github.ebnew.ki4so.web.action;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

public class KeyActionTest {
	
	private KeyAction keyAction = new KeyAction();
	
	@Before
	public void setUp() throws Exception {
		keyAction = new KeyAction();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFetchKey() throws UnsupportedEncodingException {
		KeyService keyService = Mockito.mock(KeyService.class);
		Ki4soKey ki4soKey = Mockito.mock(Ki4soKey.class);
		Mockito.when(keyService.findKeyByAppId(Mockito.anyString())).thenReturn(ki4soKey);
		keyAction.setKeyService(keyService);
		Ki4soKey result = keyAction.fetchKey("100");
		Assert.assertTrue(ki4soKey==result);
	}

}
