package at.technikum.swe.uebungen;

import at.technikum.swe.plugin.PluginManagerImpl;
import at.technikum.swe.request.RequestImpl;
import at.technikum.swe.response.ResponseImpl;
import java.io.InputStream;

import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB4;

public class UEB4Impl implements UEB4 {

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
	public PluginManager getPluginManager() {
		return new PluginManagerImpl();
	}
}
