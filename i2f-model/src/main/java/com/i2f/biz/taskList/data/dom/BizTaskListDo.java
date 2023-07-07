package com.i2f.biz.taskList.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:53:41
 * @desc biz_task_list 任务列表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizTaskListDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 任务名称
     */
    protected String name;

    /**
     * 任务内容
     */
    protected String content;

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

    /**
     * 更新时间
     */
    protected Date updateTime;


    public <T extends BizTaskListDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}