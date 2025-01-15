package PlayWrightFactory;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

public class InitializePlaywright {

	static Playwright playwright;
	static APIRequest request;
	static APIRequestContext requestContext;

	public static APIRequestContext InitiatePlayWrightAPI()
	{
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
        return requestContext;
	}

}
