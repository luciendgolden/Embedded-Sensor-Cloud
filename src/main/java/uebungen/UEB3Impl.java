package main.java.uebungen;

import java.io.InputStream;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB3;
import main.java.plugin.TestPlugin;
import main.java.request.RequestImpl;
import main.java.response.ResponseImpl;

public class UEB3Impl implements UEB3 {

	@Override
	public void helloWorld() {

	}

	@Override
	public Request getRequest(InputStream inputStream) {
		return new RequestImpl(inputStream);
	}

	@Override
	public Response getResponse() {
		return new ResponseImpl();
	}

	@Override
	public Plugin getTestPlugin() {
		return new TestPlugin();
	}
}
