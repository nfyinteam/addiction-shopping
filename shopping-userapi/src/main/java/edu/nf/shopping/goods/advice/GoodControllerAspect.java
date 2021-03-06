package edu.nf.shopping.goods.advice;

import edu.nf.shopping.goods.exception.GoodsException;
import edu.nf.shopping.goods.exception.GoodsImgException;
import edu.nf.shopping.goods.exception.ImgsInfoException;
import edu.nf.shopping.goods.exception.SkuInfoException;
import edu.nf.shopping.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Achine
 * @date 2019/11/14
 */
@ControllerAdvice({"edu.nf.shopping.goods.controller"})
public class GoodControllerAspect {

    @ExceptionHandler(GoodsException.class)
    @ResponseBody
    public ResponseVO goodsException(GoodsException e){
        ResponseVO vo = new ResponseVO();
        vo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        vo.setMessage(e.getMessage());
        return vo;
    }

    @ExceptionHandler(SkuInfoException.class)
    @ResponseBody
    public ResponseVO skuInfoException(SkuInfoException e){
        ResponseVO vo = new ResponseVO();
        vo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        vo.setMessage(e.getMessage());
        return vo;
    }

    @ExceptionHandler(ImgsInfoException.class)
    @ResponseBody
    public ResponseVO imgsInfoException(ImgsInfoException e){
        ResponseVO vo = new ResponseVO();
        vo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        vo.setMessage(e.getMessage());
        return vo;
    }

    @ExceptionHandler(GoodsImgException.class)
    @ResponseBody
    public ResponseVO goodsImgException(GoodsImgException e){
        ResponseVO vo = new ResponseVO();
        vo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        vo.setMessage(e.getMessage());
        return vo;
    }

}
