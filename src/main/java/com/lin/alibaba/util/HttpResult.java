package com.lin.alibaba.util;

import org.apache.http.Header;

/**
 * @author weidong http结果
 */
public class HttpResult {
	private Header[] header;
	private String content;

	public Header[] getHeader() {
		return header;
	}

	public HttpResult setHeader(Header[] header) {
		this.header = header;
		return this;
	}

	public String getContent() {
		return content;
	}

	public HttpResult setContent(String content) {
		this.content = content;
		return this;
	}

}
