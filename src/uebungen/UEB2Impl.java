package uebungen;

import java.io.InputStream;

import mywebserver.ServerImpl;
import mywebserver.UrlImpl;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.UEB2;
import BIF.SWE1.interfaces.Url;

public class UEB2Impl implements UEB2 {

	@Override
	public void helloWorld() {

	}

	@Override
	public Url getUrl(String s) {
		return new UrlImpl(s);
	}

	@Override
	public Request getRequest(InputStream inputStream) {
		return new ServerImpl();
	}

	@Override
	public Response getResponse() {
		return null;
	}
}
