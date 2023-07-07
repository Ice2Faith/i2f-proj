package com.i2f.biz.taskList.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:54:52
 * @desc biz_task_list_history 任务处理历史表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizTaskListHistoryDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 任务ID，见：biz_task_list.id
     */
    protected Long taskId;

    /**
     * 任务等级0-10，越高越紧急
     */
    protected Integer level;

    /**
     * 任务价值0-10，越高越有价值
     */
    protected Integer value;

    /**
     * 进度
     */
    protected Double progress;

    /**
     * 处理描述
     */
    protected String process;

    /**
     * 截止时间
     */
    protected Date deadline;

    /**
     * 状态：0 运行，1 结束，2 挂起
     */
    protected Integer status;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建时间
     */
    protected Date createTime;


    public <T extends BizTaskListHistoryDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}