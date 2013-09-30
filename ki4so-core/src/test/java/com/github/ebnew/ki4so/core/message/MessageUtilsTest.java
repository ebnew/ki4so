package com.github.ebnew.ki4so.core.message;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;
import com.github.ebnew.ki4so.core.exception.NoKi4soKeyException;

public class MessageUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMessage() {
		Assert.assertNull(MessageUtils.getMessage(null));
		
		Assert.assertNull(MessageUtils.getMessage(""));
		
		Assert.assertNotNull(MessageUtils.getMessage(InvalidCredentialException.MSG_KEY));
		Assert.assertNotNull(MessageUtils.getMessage(NoKi4soKeyException.MSG_KEY));
	}

}
