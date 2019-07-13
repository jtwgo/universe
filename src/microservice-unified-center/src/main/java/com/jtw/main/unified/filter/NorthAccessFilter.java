package com.jtw.main.unified.filter;

import com.jtw.main.unified.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter
public class NorthAccessFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(NorthAccessFilter.class);

    private static final String AUTH_TOKEN = "X-Auth-Token";

    private static final int BUFFER_SIZE = 1024;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        RequestWrapper request = new RequestWrapper((HttpServletRequest) servletRequest);
        ResponseWrapper response = new ResponseWrapper((HttpServletResponse) servletResponse);
        recordRequestLog(request);
        filterChain.doFilter(request, response);
        recordResponseLog(response);
    }

    private void recordRequestLog(RequestWrapper request)
    {
        String token = request.getHeader(AUTH_TOKEN);
        String userName = token;
        String url = request.getRequestURI();
        String ip = HttpUtils.getRealRemoteAddr(request);
        String method = request.getMethod();
        int contentLength = request.getContentLength();
        String content = new String(request.getContent());
        Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = pattern.matcher(content);
        content = m.replaceAll("");
        String protocol = request.getProtocol();
        StringBuilder sb = new StringBuilder();
        sb.append("[request]").append("[user=").append(userName)
                .append("],").append("[url=").append(url)
                .append("],").append("[ip=").append(ip)
                .append("],").append("[method=").append(method)
                .append("],").append("[contentLength=").append(contentLength)
                .append("],").append("[content=").append(content)
                .append("]").append("[httpVersion=").append(protocol).append("]");
        LOGGER.info(sb.toString());
    }

    private void recordResponseLog(ResponseWrapper response)
    {
        int resultCode = response.getStatus();
        String contentType = response.getContentType();
        byte[] message = response.getBody();
        int contentLength = message.length;
        StringBuilder sb = new StringBuilder();
        sb.append("[response]").append("[resultCode=").append(resultCode)
                .append("],").append("[contentType=").append(contentType)
                .append("],").append("[message=").append(new String(message))
                .append("],").append("[contentLength=").append(contentLength).append("]");
        LOGGER.info(sb.toString());
    }

    private static class RequestWrapper extends HttpServletRequestWrapper
    {
        private byte[] buffer;
        private ByteArrayOutputStream baos;
        public RequestWrapper(HttpServletRequest request) throws IOException
        {
            super(request);
            this.baos = new ByteArrayOutputStream();
            ServletInputStream inputStream = request.getInputStream();
            int len;
            byte[] buff = new byte[BUFFER_SIZE];
            while ((len = inputStream.read(buff)) > 0)
            {
                baos.write(buff, 0, len);
            }
            baos.flush();
            buffer = baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream()
                throws IOException
        {
            return new WrapperedServletInputStream(buffer);
        }

        @Override
        public BufferedReader getReader() throws IOException
        {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        public byte[] getContent()
        {
            return this.buffer;
        }

        private static class WrapperedServletInputStream extends ServletInputStream
        {
            private ByteArrayInputStream bais;
            public WrapperedServletInputStream(byte[] buffer)
            {
                bais = new ByteArrayInputStream(buffer);
            }
            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public boolean isReady()
            {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener)
            {

            }

            @Override
            public int read() throws IOException
            {
                return bais.read();
            }
        }
    }

    private class ResponseWrapper extends HttpServletResponseWrapper
    {

        private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        private HttpServletResponse response;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
            this.response = response;
        }

        public byte[] getBody() {
            return byteArrayOutputStream.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStreamWrapper(this.byteArrayOutputStream , this.response);
        }

        @Override
        public PrintWriter getWriter() throws IOException
        {
            return new PrintWriter(new OutputStreamWriter(this.byteArrayOutputStream , this.response.getCharacterEncoding()));
        }


        private  class ServletOutputStreamWrapper extends ServletOutputStream
        {

            private ByteArrayOutputStream outputStream;

            private HttpServletResponse response;

            public ServletOutputStreamWrapper(ByteArrayOutputStream outputStream, HttpServletResponse response)
            {
                this.outputStream = outputStream;
                this.response = response;
            }

            @Override
            public boolean isReady()
            {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener)
            {

            }

            @Override
            public void write(int b) throws IOException
            {
                this.outputStream.write(b);
            }

            @Override
            public void flush() throws IOException
            {
                if (! this.response.isCommitted())
                {
                    byte[] body = this.outputStream.toByteArray();
                    ServletOutputStream outputStream = this.response.getOutputStream();
                    outputStream.write(body);
                    outputStream.flush();
                }
            }
        }
    }

    @Override
    public void destroy()
    {

    }


}
