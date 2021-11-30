package com.shop.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.text.StringEscapeUtils;

public class RequestParameterHandler {

    public String getAsString(HttpServletRequest request, String parameterName) {
        return getEscaped(request, parameterName);
    }

    public Long getAsLong(HttpServletRequest request, String parameterName) {
        String parameter = getEscaped(request, parameterName);
        return Long.parseLong(parameter);
    }

    public Double getAsDouble(HttpServletRequest request, String parameterName) {
        String parameter = getEscaped(request, parameterName);
        return Double.parseDouble(parameter);
    }

    private String getEscaped(HttpServletRequest request, String parameterName) {
        String parameter = request.getParameter(parameterName);
        return StringEscapeUtils.escapeHtml4(parameter);
    }
}
