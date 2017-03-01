package tz.co.fasthub.ona.controller;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FacebookController {
	
	private static final String APP_SECRET = "cade2665e5fe350a997d203183c3e893";
	private static final String APP_ID = "383479438692276";
	private static final String SCOPE = "user_friends, publish_actions";
	private static final String CALLBACK_URL = "http://127.0.0.1:8080/facebook/callback";
	private static final String TOKEN_NAME = "facebookToken";

	@RequestMapping("/facebook")
	public String fb(HttpServletRequest request, Model model) {
		String accessToken = (String) request.getSession().getAttribute(TOKEN_NAME);
		
		Facebook facebook = new FacebookTemplate(accessToken);					
		if(facebook.isAuthorized()) {
			
			PostData postData = new PostData(facebook.userOperations().getUserProfile().getId());
			
			postData.message("this is facebook!!!g");

			facebook.feedOperations().post(postData);
		
			return "fb";
		}
		else {
			return "redirect:/facebook/login";
		}	
	}
	
	@RequestMapping("/facebook/login")
	public void login(HttpServletResponse response) throws IOException {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(APP_ID, APP_SECRET);

		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(CALLBACK_URL);
		params.setScope(SCOPE);

		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(params);
		
		response.sendRedirect(authorizeUrl);
	}
		
	@RequestMapping("/facebook/callback")
	public String callback(@RequestParam("code") String authorizationCode, HttpServletRequest request) {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(APP_ID, APP_SECRET);
		
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, CALLBACK_URL, null);

		String token = accessGrant.getAccessToken();
		request.getSession().setAttribute(TOKEN_NAME, token);
		
		return "redirect:/facebook/fb";
	}
}
