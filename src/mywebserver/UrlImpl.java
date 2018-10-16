package mywebserver;

import java.util.Map;

import BIF.SWE1.interfaces.Url;

public class UrlImpl implements Url {
	
	public UrlImpl()
    {

    }

    public UrlImpl(String raw)
    {

    }

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRawUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtension() {
		return null;
	}

	@Override
	public String getFragment() {
		return null;
	}

	@Override
	public Map<String, String> getParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getParameterCount() {
		return 0;
	}

	@Override
	public String[] getSegments() {
		// TODO Auto-generated method stub
		return null;
	}

}
