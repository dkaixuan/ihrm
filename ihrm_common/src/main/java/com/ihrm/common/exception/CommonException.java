package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;
import lombok.Data;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 19:59
 * @Version 1.0
 * 自定义异常
 */
@Data
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -280921713449805779L;

    private ResultCode code = ResultCode.SERVER_ERROR;

    public CommonException(){};

    public CommonException(ResultCode resultCode){
        super(resultCode.message());
        this.code = resultCode;
    }
}
