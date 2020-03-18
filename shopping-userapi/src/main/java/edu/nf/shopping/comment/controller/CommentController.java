package edu.nf.shopping.comment.controller;

import com.github.pagehelper.PageInfo;
import edu.nf.shopping.comment.entity.Comment;
import edu.nf.shopping.comment.service.CommentService;
import edu.nf.shopping.vo.BaseController;
import edu.nf.shopping.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Bull fighters
 * @date 2020/3/12
 */
@RestController
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/list_buyShow")
    @ApiOperation(value = "查询买家秀", notes = "查询单个商品的买家秀",
            httpMethod = "get")
    private ResponseVO<PageInfo<Comment>> listBuyShow(Integer pageNum, Integer pageSize, Integer replySize, String goodsId, String dateTime, String order,HttpServletRequest request) throws ParseException {
        PageInfo<Comment> pageInfo=commentService.listBuyShow(pageNum,pageSize,replySize,goodsId,"1578412684666",sdf.parse(dateTime),order);
        return success(pageInfo);
    }

    @RequestMapping("/list_comment")
    @ApiOperation(value = "查询回复评论", notes = "查询买家秀的回复评论",
            httpMethod = "post")
    private ResponseVO<PageInfo<Comment>> ListComment(Integer pageNum,Integer pageSize,String commentId, String dateTime, String order,HttpServletRequest request) throws ParseException {
        PageInfo<Comment> pageInfo=commentService.listComment(pageNum,pageSize,commentId,"1578412684666",sdf.parse(dateTime),order);
        return success(pageInfo);
    }

    @RequestMapping("/add_comment")
    @ApiOperation(value = "添加评论", notes = "用户发表一个评论",
            httpMethod = "post")
    private ResponseVO addComment(@ModelAttribute Comment comment, HttpServletRequest request){
        comment.setUserId("1578412684666");
        commentService.addComment(comment);
        return success(200,"发表评论成功");
    }
}