package com.training.javaexercise.filter;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @Author Han Pasha
 */
@Component
public class ResponseLogFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(ResponseLogFilter.class);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            if(responseDataStream == null) {
                logger.info("Body : {}","");
                return null;
            }

            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream,"UTF-8"));
            logger.info("Body: {}", responseData);

            context.setResponseBody(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
