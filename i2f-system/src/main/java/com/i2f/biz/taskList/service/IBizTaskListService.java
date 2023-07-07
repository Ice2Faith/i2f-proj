package com.i2f.biz.taskList.service;

import com.i2f.biz.taskList.data.vo.BizTaskListHistoryVo;
import com.i2f.biz.taskList.data.vo.BizTaskListVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:53:41
 * @desc biz_task_list 任务列表
 */
public interface IBizTaskListService {

    ApiPage<BizTaskListVo> page(BizTaskListVo webVo,
                                ApiPage<BizTaskListVo> page);

    List<BizTaskListVo> list(BizTaskListVo webVo);

    BizTaskListVo find(Long id);

    void add(BizTaskListVo webVo);

    void update(BizTaskListVo webVo);

    void delete(Long id);

    void suspend(Long id);

    void run(Long id);

    void finish(Long id);

    List<BizTaskListHistoryVo> history(Long id);
}