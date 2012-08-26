package org.secmem.remoteroid.lib.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.secmem.remoteroid.lib.api.API;
import org.secmem.remoteroid.lib.data.Account;
import org.secmem.remoteroid.lib.request.Request;
import org.secmem.remoteroid.lib.request.Request.RequestFactory;
import org.secmem.remoteroid.lib.request.Response;

public class RequestTest {

	//@Test
	public void testRegister(){
		Request request = RequestFactory.getRequest(API.Account.ADD_ACCOUNT);
		Account account = new Account();
		account.setEmail("test@test.com");
		account.setPassword("pass");
		request.attachPayload(account);
		try {
			Response resp = request.sendRequest();
			if(resp.isSucceed()){
				Account acc = resp.getPayloadAsAccount();
				assertEquals("test@test.com", acc.getEmail());
			}else{
				fail();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLogin(){
		Request request = RequestFactory.getRequest(API.Account.LOGIN);
		Account account = new Account();
		account.setEmail("test@test.com");
		account.setPassword("pass");
		request.attachPayload(account);
		try{
			Response resp = request.sendRequest();
			if(!resp.isSucceed()){
				fail();
			}
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}