package com.boyu.erp.platform.sso.authorization.approval;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**拷贝自源码**/
/**org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint**/
/**需要授权的时候本来源码要弹出确认授权表单页面，现在改造成**/
@RestController
@SessionAttributes("authorizationRequest")
public class SsoApprovalEndpoint {

	private static String CSRF = "<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}' />";
	private static String DENIAL = "<form id='denialForm' name='denialForm' action='${path}/oauth/authorize' method='post'><input name='user_oauth_approval' value='false' type='hidden'/>%csrf%<label><input name='deny' value='Deny' type='submit'/></label></form>";
	private static String TEMPLATE = "<html>" +
			"<body>" +
			"<div style='display:none;'>" +  //增加DIV 把HTML内容隐藏
			"<h1>OAuth Approval</h1><p>Do you authorize '${authorizationRequest.clientId}' to access your protected resources?</p><form id='confirmationForm' name='confirmationForm' action='${path}/oauth/authorize' method='post'><input name='user_oauth_approval' value='true' type='hidden'/>%csrf%%scopes%<label><input name='authorize' value='Authorize' type='submit'/></label></form>%denial%" +
			"</div>" +
			"<script>document.getElementById('confirmationForm').submit()</script>" + //白页面--自动提交表单
			"</body>" +
			"</html>";
	private static String SCOPE = "<li><div class='form-group'>%scope%: <input type='radio' name='%key%' value='true'%approved%>Approve</input> <input type='radio' name='%key%' value='false'%denied%>Deny</input></div></li>";

	public SsoApprovalEndpoint() {
	}

	@RequestMapping({"/oauth/confirm_access"})
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		String template = this.createTemplate(model, request);
		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}

		return new ModelAndView(new SsoSpelView(template), model);
	}

	protected String createTemplate(Map<String, Object> model, HttpServletRequest request) {
		String template = TEMPLATE;
		if (!model.containsKey("scopes") && request.getAttribute("scopes") == null) {
			template = template.replace("%scopes%", "").replace("%denial%", DENIAL);
		} else {
			template = template.replace("%scopes%", this.createScopes(model, request)).replace("%denial%", "");
		}

		if (!model.containsKey("_csrf") && request.getAttribute("_csrf") == null) {
			template = template.replace("%csrf%", "");
		} else {
			template = template.replace("%csrf%", CSRF);
		}

		return template;
	}

	private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
		StringBuilder builder = new StringBuilder("<ul>");
		Map<String, String> scopes = (Map)((Map)(model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes")));
		Iterator var5 = scopes.keySet().iterator();

		while(var5.hasNext()) {
			String scope = (String)var5.next();
			String approved = "true".equals(scopes.get(scope)) ? " checked" : "";
			String denied = !"true".equals(scopes.get(scope)) ? " checked" : "";
			String value = SCOPE.replace("%scope%", scope).replace("%key%", scope).replace("%approved%", approved).replace("%denied%", denied);
			builder.append(value);
		}

		builder.append("</ul>");
		return builder.toString();
	}

}
