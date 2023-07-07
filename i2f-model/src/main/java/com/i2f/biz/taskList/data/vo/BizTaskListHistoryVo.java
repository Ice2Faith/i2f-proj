package com.i2f.biz.taskList.data.vo;


import com.i2f.biz.taskList.data.dom.BizTaskListHistoryDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:54:52
 * @desc biz_task_list_history 任务处理历史表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizTaskListHistoryVo extends BizTaskListHistoryDo {


    /**
     * 任务等级0-10，越高越紧急
     */
    protected String levelDesc;
    /**
     * 任务价值0-10，越高越有价值
     */
    protected String valueDesc;
    /**
     * 状态：0 运行，1 结束，2 挂起
     */
    protected String statusDesc;

    public BizTaskListHistoryDo parent() {
        return (BizTaskListHistoryDo) this;
    }


}