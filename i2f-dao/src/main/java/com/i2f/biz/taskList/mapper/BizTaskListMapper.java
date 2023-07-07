package com.i2f.biz.taskList.mapper;

import com.i2f.biz.taskList.data.dom.BizTaskListDo;
import com.i2f.biz.taskList.data.vo.BizTaskListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:53:41
 * @desc biz_task_list 任务列表
 */
@Mapper
public interface BizTaskListMapper {

    List<BizTaskListVo> page(@Param("post") BizTaskListVo post);

    List<BizTaskListVo> list(@Param("post") BizTaskListVo post);

    BizTaskListVo findByPk(@Param("id") Long id);

    <T extends BizTaskListDo> int insertSelective(@Param("post") T post);

    <T extends BizTaskListDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends BizTaskListDo> int insert(@Param("post") T post);

    <T extends BizTaskListDo> int updateByPk(@Param("post") T post);

    <T extends BizTaskListDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends BizTaskListDo> list);

    int backupHistory(@Param("id") Long id);
}