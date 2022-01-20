package com.ycj.student.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Http请求工具
 *
 */
public class HttpUtils {
    private static final int TIMEOUT = 60000;
    public static final String CHARSET = "UTF-8";
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    /**
     * 处理请求头
     *
     * @param request
     * @param header
     */
    private static void dealHeader(HttpUriRequest request, Map<String, String> header) {
        if (!ObjectUtils.isEmpty(header)) {
            Iterator<Entry<String, String>> iterator = header.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * <p>请求头是否是application/json</p>
     */
    public static boolean isRequestBody(HttpServletRequest request) {

        String contentType = request.getHeader("Content-Type");
        if(StringUtils.isNotEmpty(contentType)){
            return contentType.contains(CONTENT_TYPE_APPLICATION_JSON);
        }
        return false;
    }

    /**
     * 序列化请求地址
     *
     * @param url
     * @param params
     * @param reqCharset
     * @return
     * @throws IOException
     */
    public static String serialiseRequestUrl(String url, Map<String, String> params, String reqCharset) throws IOException {
        Assert.notNull(url, "the url must be not null.");
        URL in = new URL(url);
        StringBuilder realUrl = new StringBuilder();
        boolean first = true;
        // 协议、主机、端口、路径
        realUrl.append(in.getProtocol()).append("://").append(in.getAuthority()).append(in.getPath()).append("?");
        // 如果存在查询参数，添加
        if (in.getQuery() != null) {
            realUrl.append(in.getQuery());
            first = false;
        }
        if (params != null) {
            if (!first) {
                realUrl.append('&');
            }
            List<NameValuePair> parameters = new ArrayList<>();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            realUrl.append(URLEncodedUtils.format(parameters, StringUtils.isEmpty(reqCharset) ? CHARSET : reqCharset));
        }
        return realUrl.toString();
    }

    /**
     * GET请求
     *
     * @param url
     * @param params
     * @return
     * @throws RuntimeException
     */
    public static String get(String url, Map<String, String> params) throws RuntimeException {
        return invoke(Method.GET, url, params, null, null, null, null);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> header) throws RuntimeException {
        return invoke(Method.GET, url, params, header, null, null, null);
    }

    /**
     * POST请求
     *
     * @param url
     * @param data
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static String post(String url, Object data) throws RuntimeException {
        return invoke(Method.POST, url, data, null, null, null, null);
    }

    /**
     * POST请求
     *
     * @param url
     * @param data
     * @param header
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static String post(String url, Object data, Map<String, String> header) throws RuntimeException {
        return invoke(Method.POST, url, data, header, null, null, null);
    }

    /**
     * 处理请求
     *
     * @param request    请求对象
     * @param resCharset 响应编码，为空时则首先使用响应头Content-Type中的编码，如果该编码无效，则使用默认编码：
     *                   {@link #CHARSET}
     * @param out        当out参数不为空时，请求结果会输出到out中，方法返回null，否则将请求结果转换成字符串返回
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static String invoke(HttpUriRequest request, String resCharset, OutputStream out) throws RuntimeException {
        Assert.notNull(request, "the request must be not null.");
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = getHttpClient();
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (out != null) {
                entity.writeTo(out);
            } else {
                String charset = resCharset;
                Header header = response.getFirstHeader("Content-Type");
                if (header != null) {
                    charset = getCharsetFromContentType(header.getValue());
                }
                return EntityUtils.toString(response.getEntity(), StringUtils.isEmpty(charset) ? CHARSET : charset);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(client);
        }
        return null;
    }

    /**
     * 请求网络数据
     *
     * @param method     请求方法
     * @param url        请求地址
     * @param data       请求数据，当请求方法为：{@link Method#GET}、{@link Method#DELETE}、
     *                   {@link Method#HEAD}、{@link Method#OPTIONS}、{@link Method#TRACE}
     *                   中的一种时，data的数据类型必须为{@link Map}
     * @param header     请求头
     * @param reqCharset 请求编码，为空时则使用默认编码：{@link #CHARSET}
     * @param resCharset 响应编码，为空时则首先使用响应头Content-Type中的编码，如果该编码无效，则使用默认编码：
     *                   {@link #CHARSET}
     * @param out        当out参数不为空时，请求结果会输出到out中，方法返回null，否则将请求结果转换成字符串返回
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static String invoke(Method method, String url, Object data, Map<String, String> header, String reqCharset, String resCharset,
                                OutputStream out) throws RuntimeException {
        Assert.notNull(method, "the method must be not null.");
        Assert.notNull(url, "the url must be not null.");
        HttpUriRequest request;
        try {
            request = accessMethod(method, url, data, reqCharset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (request instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase entityEnclosingRequest = (HttpEntityEnclosingRequestBase) request;
            if (data instanceof Map) {
                handleMap(data, reqCharset, entityEnclosingRequest);
            } else if (data instanceof byte[]) {
                ByteArrayEntity entity = new ByteArrayEntity((byte[]) data);
                entityEnclosingRequest.setEntity(entity);
            } else if (data instanceof String) {
                StringEntity entity = new StringEntity((String) data, StringUtils.isEmpty(reqCharset) ? CHARSET : reqCharset);
                entityEnclosingRequest.setEntity(entity);
            } else {
                throw new IllegalArgumentException("not support data type.");
            }
        }
        dealHeader(request, header);
        return invoke(request, resCharset, out);
    }

    private static void handleMap(Object data, String reqCharset, HttpEntityEnclosingRequestBase entityEnclosingRequest) throws RuntimeException {
        List<NameValuePair> parameters = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Iterator<Entry<String, Object>> iterator = ((Map<String, Object>) data).entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            if (entry.getValue() != null) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, StringUtils.isEmpty(reqCharset) ? CHARSET : reqCharset);
            entityEnclosingRequest.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static HttpUriRequest accessMethod(Method method, String url, Object data, String reqCharset) throws IOException {
        HttpUriRequest request = null;
        switch (method) {
            case POST:
                request = new HttpPost(url);
                break;
            case GET:
                request = new HttpGet(serialiseRequestUrl(url, (Map<String, String>) data, reqCharset));
                break;
            case PUT:
                request = new HttpPut(url);
                break;
            case DELETE:
                request = new HttpDelete(serialiseRequestUrl(url, (Map<String, String>) data, reqCharset));
                break;
            case HEAD:
                request = new HttpHead(serialiseRequestUrl(url, (Map<String, String>) data, reqCharset));
                break;
            case OPTIONS:
                request = new HttpOptions(serialiseRequestUrl(url, (Map<String, String>) data, reqCharset));
                break;
            case PATCH:
                request = new HttpPatch(url);
                break;
            case TRACE:
                request = new HttpTrace(serialiseRequestUrl(url, (Map<String, String>) data, reqCharset));
                break;
        }
        return request;
    }

    /**
     * 获得HttpClient
     *
     * @throws RuntimeException
     */
    public static CloseableHttpClient getHttpClient() throws RuntimeException {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT)
                .build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Charset.forName(CHARSET)).build();
        SSLContext sslContext;
        HttpClientBuilder builder = HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultConnectionConfig(connectionConfig)
                .setSSLHostnameVerifier(new HostnameVerifier() {

                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new X509TrustManager[]{new X509TrustManagerImplementation()}, new SecureRandom());
            builder.setSSLContext(sslContext);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return builder.build();
    }

    /**
     * 从content-type头中解析字符集
     *
     * @param contentType
     * @return
     * @throws RuntimeException
     */
    public static String getCharsetFromContentType(String contentType) throws RuntimeException {
        if (contentType == null)
            return null;
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim();
            charset = charset.replace("charset=", "");
            return validateCharset(charset);
        }
        return null;
    }

    /**
     * 验证字符集
     *
     * @param str
     * @return
     * @throws RuntimeException
     */
    private static String validateCharset(String str) throws RuntimeException {
        String cs = str;
        if (cs == null || cs.length() == 0)
            return null;
        cs = cs.trim().replaceAll("[\"']", "");
        try {
            if (Charset.isSupported(cs))
                return cs;
            cs = cs.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(cs))
                return cs;
        } catch (IllegalCharsetNameException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static final class X509TrustManagerImplementation implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // 不可以直接返回null，无效
            return new X509Certificate[0];
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
            return;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            return;
        }
    }

    /**
     * 请求方法
     *
     * @author gjjiang
     */
    enum Method {
        GET, POST, DELETE, HEAD, OPTIONS, PUT, TRACE, PATCH
    }


    /**
     * Post String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static synchronized HttpResponse doPost(String host, String path, String method,
                                                   Map<String, String> headers,
                                                   Map<String, String> querys,
                                                   String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }


    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


}
