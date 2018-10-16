package uebungen;

import java.io.InputStream;
import java.time.LocalDate;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.UEB6;

public class UEB6Impl implements UEB6 {

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
	public Plugin getTemperaturePlugin() {
		return null;
	}

	@Override
	public Plugin getNavigationPlugin() {
		return null;
	}

	@Override
	public Plugin getToLowerPlugin() {
		return null;
	}

	@Override
	public String getTemperatureUrl(LocalDate localDate, LocalDate localDate1) {
		return null;
	}

	@Override
	public String getTemperatureRestUrl(LocalDate localDate, LocalDate localDate1) {
		return null;
	}

	@Override
	public String getNaviUrl() {
		return null;
	}

	@Override
	public String getToLowerUrl() {
		return null;
	}
}
