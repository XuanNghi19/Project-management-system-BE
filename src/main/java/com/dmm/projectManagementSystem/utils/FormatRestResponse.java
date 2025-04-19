package com.dmm.projectManagementSystem.utils;

import com.dmm.projectManagementSystem.controller.student.ProjectController;
import com.dmm.projectManagementSystem.controller.student.TeamController;
import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Autowired
    private HttpServletResponse httpServletResponse;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        System.out.println("In supports() method of " + getClass().getSimpleName());
        return returnType.getContainingClass() == TeamController.class || returnType.getContainingClass() == ProjectController.class || returnType.getContainingClass() == TeamController.class;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletResponse httpServletResponse = ((ServletServerHttpResponse)response).getServletResponse();
        int status = httpServletResponse.getStatus();

        if (body instanceof String){
            return body;
        }
        RestResponse<Object> restResponse = new RestResponse<>();
        if (status >= 400){
            return body;
        }else {
            restResponse.setStatusCode(httpServletResponse.getStatus());
            restResponse.setData(body);
            ApiMessageResponse message = returnType.getMethodAnnotation(ApiMessageResponse.class);
            restResponse.setMessage(message != null ? message.value() : "Gọi API thành công !");
        }
        return restResponse;
    }
}
