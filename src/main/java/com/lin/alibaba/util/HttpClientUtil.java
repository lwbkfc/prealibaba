package com.lin.alibaba.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weidong Http工具
 */
public class HttpClientUtil {

	public static CookieStore cookieStore = null;
	private static String charset = "UTF-8";
	private static PoolingHttpClientConnectionManager cm;
	private static final String EMPTY_STR = "";

	private HttpClientUtil() {
	}

	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(50);// 整个连接池最大连接数
			cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient buildHttpClient() {
		init();
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(cm);
		return cookieStore != null ? builder.setDefaultCookieStore(cookieStore).build() : builder.build();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static HttpResult get(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getHttpResult(httpGet, url);
	}

	@SuppressWarnings("rawtypes")
	public static HttpResult get(String url, Map<String, Object> params) throws URISyntaxException {
		StringBuilder strBuilder = new StringBuilder();
		for (Entry entry : params.entrySet()) {
			strBuilder.append(entry.getKey());
			strBuilder.append("=");
			strBuilder.append(entry.getValue());
			strBuilder.append("&");
		}
		String paraStr = strBuilder.toString();
		paraStr = paraStr.endsWith("&") ? paraStr.substring(0, paraStr.length() - 1) : paraStr;
		String targetUrl = url + "?" + paraStr;
		return get(targetUrl);
	}

	public static HttpResult get(String url, Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getHttpResult(httpGet, url);
	}

	public static HttpResult post(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getHttpResult(httpPost, url);
	}

	public static HttpResult post(String url, Map<String, Object> params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
		return getHttpResult(httpPost, url);
	}

	@SuppressWarnings("deprecation")
	public static HttpResult post(String url, Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		boolean isJsonMime = false;
		String headerValue;
		for (Entry<String, Object> param : headers.entrySet()) {
			headerValue = String.valueOf(param.getValue());
			httpPost.addHeader(param.getKey(), headerValue);
			if (!isJsonMime) {
				isJsonMime = headerValue.contains("application/json");
			}
		}

		if (isJsonMime) {
			httpPost.setEntity(new StringEntity(JSON.toJSONString(params), "application/json", charset));
		} else {
			ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
		}

		return getHttpResult(httpPost, url);
	}

	@SuppressWarnings("deprecation")
	public static HttpResult postJson(String url, String json) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
		httpPost.setEntity(new StringEntity(json, "application/json", charset));
		return getHttpResult(httpPost, url);
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static HttpResult getHttpResult(HttpRequestBase request, String url) {
		HttpResult dataResp = new HttpResult();
		CloseableHttpClient httpClient = buildHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			setCookieStore(response, url);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				response.close();
				return dataResp.setHeader(response.getAllHeaders()).setContent(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getResult(HttpRequestBase request, String url) {
		return getHttpResult(request, url).getContent();
	}

	private static void setCookieStore(HttpResponse httpResponse, String url) {
		cookieStore = new BasicCookieStore();
		Header firstHead = httpResponse.getFirstHeader("Set-Cookie");
		if (firstHead != null) {
			String setCookie = firstHead.getValue();
			if (setCookie.contains("JSESSIONID")) {
				String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
				BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
				cookie.setVersion(0);
				cookie.setDomain(getDomain(url));
				cookie.setPath("/");
				cookieStore.addCookie(cookie);
			}
		}
	}

	private static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		Matcher m = p.matcher(url);
		if (m.find()) {
			return m.group();
		}
		return EMPTY_STR;
	}
}
