package at.technikum.swe.uebungen;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB3;
import at.technikum.swe.plugin.TestPlugin;
import at.technikum.swe.request.RequestImpl;
import at.technikum.swe.response.ResponseImpl;
import java.io.InputStream;

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
