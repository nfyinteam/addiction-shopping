package edu.nf.shopping.comment.controller;

import com.github.pagehelper.PageInfo;
import edu.nf.shopping.comment.entity.Comment;
import edu.nf.shopping.comment.service.CommentService;
import edu.nf.shopping.vo.BaseController;
import edu.nf.shopping.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bull fighters
 * @date 2020/3/12
 */
@RestController
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/list_buyShow")
    private ResponseVO<PageInfo<Comment>> listComment(Integer pageNum,Integer pageSize,String goodsId,String order){
        PageInfo<Comment> pageInfo=commentService.listBuyShow(pageNum,pageSize,goodsId,order);
        return success(pageInfo);
    }
}