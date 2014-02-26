package com.jeffy.util;

import com.jeffy.bo.Response;
import com.jeffy.constants.MessageConstants;

/**
 * User: Administrator
 * Date: 14-2-26
 * Time: 下午4:57
 */
public class ResponseUtil {
    public static Response getResponse() {
        Response response = new Response();
        response.setCode(MessageConstants.SUCCESS_CODE);
        response.setMessage(MessageConstants.GET_SUCCESS);
        return response;
    }

    public static Response getListResponse() {
        Response response = new Response();
        response.setCode(MessageConstants.SUCCESS_CODE);
        response.setMessage(MessageConstants.GET_LIST_SUCCESS);
        return response;
    }

    public static Response saveResponse() {
        Response response = new Response();
        response.setCode(MessageConstants.SUCCESS_CODE);
        response.setMessage(MessageConstants.SAVE_SUCCESS);
        return response;
    }

    public static Response updateResponse() {
        Response response = new Response();
        response.setCode(MessageConstants.SUCCESS_CODE);
        response.setMessage(MessageConstants.UPDATE_SUCCESS);
        return response;
    }

    public static Response deleteResponse() {
        Response response = new Response();
        response.setCode(MessageConstants.SUCCESS_CODE);
        response.setMessage(MessageConstants.DELETE_SUCCESS);
        return response;
    }
}
