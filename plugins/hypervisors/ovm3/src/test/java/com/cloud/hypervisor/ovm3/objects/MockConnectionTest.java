package com.cloud.hypervisor.ovm3.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.easymock.EasyMock;
import org.junit.Test;

public class MockConnectionTest {

	public Connection mockedConnectionTest;
	private final Logger LOGGER = Logger.getLogger(ConnectionTest.class);
	XmlTestResultTest results = new XmlTestResultTest();
	String result;
	List<String> multiRes = new ArrayList<String>();
	String hostIp;
	private Map<String, String> methodResponse = new HashMap<String, String>();

	public MockConnectionTest() {
		this.mockedConnectionTest = EasyMock.partialMockBuilder(Connection.class).createMock();
		mockCallTimeoutInSec();
		EasyMock.replay(this.mockedConnectionTest);
	}

	private void mockCallTimeoutInSec() {
		try {
			EasyMock.expect(this.mockedConnectionTest.callTimeoutInSec(EasyMock.anyString(), EasyMock.<List<?>> anyObject(), EasyMock.anyInt(),
					EasyMock.anyBoolean())).andReturn(null).anyTimes();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

	public void setMethodResponse(String method, String response) {
		methodResponse.put(method, response);
	}

	public String getMethodResponse(String method) {
		if (methodResponse.containsKey(method)) {
			return methodResponse.get(method);
		}
		return null;
	}

	public void removeMethodResponse(String method) {
		if (methodResponse.containsKey(method)) {
			methodResponse.remove(method);
		}
	}

	public void setResult(String res) {
		multiRes = new ArrayList<String>();
		multiRes.add(0, res);
	}

	public void setResult(List<String> l) {
		multiRes = new ArrayList<String>();
		multiRes.addAll(l);
	}

	public void setNull() {
		multiRes = new ArrayList<String>();
		multiRes.add(0, null);
	}

	/* result chainsing */
	public void addResult(String e) {
		multiRes.add(e);
	}

	public void addNull() {
		multiRes.add(null);
	}

	public String getResult() {
		return popResult();
	}

	public String popResult() {
		String res = multiRes.get(0);
		if (multiRes.size() > 1) multiRes.remove(0);
		return res;
	}

	public List<String> resultList() {
		return multiRes;
	}

	@Test
	public void testConnection() {
		String host = "ovm-1";
		String user = "admin";
		String pass = "password";
		Integer port = 8899;
		List<?> emptyParams = new ArrayList<Object>();
		Connection con = new Connection(host, port, user, pass);
		results.basicStringTest(con.getIp(), host);
		results.basicStringTest(con.getUserName(), user);
		results.basicStringTest(con.getPassword(), pass);
		results.basicIntTest(con.getPort(), port);
		try {
			con.callTimeoutInSec("ping", emptyParams, 1);
			// con.call("ping", emptyParams, 1, false);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception: " + e);
		}
		new Connection(host, user, pass);
	}
}
