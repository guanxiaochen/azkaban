/*
 * Copyright 2012 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package azkaban.jobExecutor;

import azkaban.utils.Props;
import azkaban.utils.UndefinedPropertyException;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * A http job.
 */
public class HttpJob extends AbstractJob {
  public static final String HTTP_GET = "GET";
  public static final String HTTP_POST = "POST";

  public static final String HEADER_ELEMENT_DELIMITER = "\r\n";
  public static final String HEADER_NAME_VALUE_DELIMITER = ":";

  public static final String URL = "url";
  public static final String METHOD = "method";
  public static final String HEADERS = "headers";
  public static final String BODY = "body";
  public static final String TIMEOUT = "timeout";
  public static final String REQUEST_TIMEOUT = "requestTimeout";
  public static final String CONNECTION_TIMEOUT = "connectionTimeout";
  public static final String SOCKET_TIMEOUT = "connectionTimeout";

  protected Props jobProps;


  public HttpJob(String jobId, Props sysProps, Props jobProps, Logger log) {
    this(jobId, sysProps, jobProps, null, log);
  }

  public HttpJob(String jobId, Props sysProps, Props jobProps, Props privateProps, Logger log) {
    super(jobId, log);
    this.jobProps = jobProps;
  }

  @Override
  public void run() throws Exception {
    String url = jobProps.getString(URL);
    String method = jobProps.getString(METHOD, "GET");
    String headers = jobProps.getString(HEADERS, "");

    this.info("HTTP url: " + url);
    HttpRequestBase httpRequest;
    if (HTTP_POST.equals(method)) {
      String body = jobProps.getString(BODY, "");
      // put together an URL
      final HttpPost httpPost = new HttpPost(url);
      if (!body.isEmpty()) {
        info("HTTP body: " + body);
        httpPost.setEntity(new StringEntity(body));
      }
      httpRequest = httpPost;
    } else if (HTTP_GET.equals(method)) {
      // GET
      httpRequest = new HttpGet(url);
    } else {
      throw new UndefinedPropertyException("Unsupported request method: " + method + ". Only POST and GET are supported");
    }

    Header[] httpHeaders = parseHttpHeaders(headers);
    if (httpHeaders != null) {
      httpRequest.setHeaders(httpHeaders);
      info("# of headers found: " + httpHeaders.length);

      final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
      httpRequest.addHeader(new BasicHeader("Date", format.format(new Date())));
    }

    final int timeout = jobProps.getInt(TIMEOUT, 3000);
    final int connectionRequestTimeout = jobProps.getInt(REQUEST_TIMEOUT, timeout);
    final int connectionTimeout = jobProps.getInt(CONNECTION_TIMEOUT, timeout);
    final int socketTimeout = jobProps.getInt(SOCKET_TIMEOUT, timeout);

    final RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectionTimeout)
                    .setSocketTimeout(socketTimeout).build();

    final HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

    final long startMs = System.currentTimeMillis();

    boolean success = false;
    try {
      HttpResponse httpResponse = httpClient.execute(httpRequest, HttpClientContext.create());
      int statusCode = httpResponse.getStatusLine().getStatusCode();

      HttpEntity entity = httpResponse.getEntity();
      if (entity != null) {
        String content = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        this.info("HTTP response [" + content + "]");
      } else {
        this.info("HTTP No response");
      }
      if (statusCode / 100 == 4 || statusCode / 100 == 5) {
        throw new RuntimeException("HTTP execute error, status：" + statusCode + ", message: " + httpResponse.getStatusLine().getReasonPhrase());
      }
      success = true;
    } finally {
      info("HTTP " + getId() + " completed "
              + (success ? "successfully" : "unsuccessfully") + " in "
              + ((System.currentTimeMillis() - startMs) / 1000) + " seconds.");
    }
  }

  /**
   * Parse headers
   *
   * @return null if headers is null or empty
   */
  public static Header[] parseHttpHeaders(final String headers) {
    if (headers == null || headers.length() == 0) {
      return null;
    }

    final String[] headerArray = headers.split(HEADER_ELEMENT_DELIMITER);
    final List<Header> headerList = new ArrayList<>(headerArray.length);
    for (final String headerPair : headerArray) {
      final int index = headerPair.indexOf(HEADER_NAME_VALUE_DELIMITER);
      if (index != -1) {
        headerList.add(new BasicHeader(headerPair.substring(0, index),
                headerPair.substring(index + 1)));
      }
    }
    return headerList.toArray(new Header[0]);
  }
}