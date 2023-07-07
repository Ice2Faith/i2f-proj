package com.i2f.biz.taskList.mapper;

import com.i2f.biz.taskList.data.dom.BizTaskListHistoryDo;
import com.i2f.biz.taskList.data.vo.BizTaskListHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:54:52
 * @desc biz_task_list_history 任务处理历史表
 */
@Mapper
public interface BizTaskListHistoryMapper {

    List<BizTaskListHistoryVo> page(@Param("post") BizTaskListHistoryVo post);

    List<BizTaskListHistoryVo> list(@Param("post") BizTaskListHistoryVo post);

    BizTaskListHistoryVo findByPk(@Param("id") Long id);

    <T extends BizTaskListHistoryDo> int insertSelective(@Param("post") T post);

    <T extends BizTaskListHistoryDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends BizTaskListHistoryDo> int insert(@Param("post") T post);

    <T extends BizTaskListHistoryDo> int updateByPk(@Param("post") T post);

    <T extends BizTaskListHistoryDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends BizTaskListHistoryDo> list);

    int deleteByTaskId(@Param("taskId") Long taskId);

    List<BizTaskListHistoryVo> historyList(@Param("taskId") Long taskId);
}