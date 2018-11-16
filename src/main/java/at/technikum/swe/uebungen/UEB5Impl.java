package at.technikum.swe.uebungen;

import java.io.InputStream;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.UEB5;

public class UEB5Impl implements UEB5 {

	@Override
	public void helloWorld() {

	}

	@Override
	public Request getRequest(InputStream inputStream) {
		return null;
	}

	@Override
	public PluginManager getPluginManager() {
		return null;
	}

	@Override
	public Plugin getStaticFilePlugin() {
		return null;
	}

	@Override
	public void setStatiFileFolder(String s) {

	}

	@Override
	public String getStaticFileUrl(String s) {
		return null;
	}
}
