package edu.nf.shopping.comment.controller;

import com.github.pagehelper.PageInfo;
import edu.nf.shopping.comment.entity.ReportReason;
import edu.nf.shopping.comment.service.ReportReasonService;
import edu.nf.shopping.vo.BaseController;
import edu.nf.shopping.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bull fighters
 * @date 2020/3/20
 */
@RestController
public class ReportReasonController extends BaseController {

    @Autowired
    private ReportReasonService reportReasonService;

    @RequestMapping("/get/reportReason")
    @ApiOperation(value = "默认举报理由", notes = "查询所有的默认举报理由",
            httpMethod = "get")
    private ResponseVO listReportReason(){
        List<ReportReason> list=reportReasonService.listReportReason();
        System.out.println(list.size());
        return success(list);
    }
}