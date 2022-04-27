package com.tuccicode.boot.app.task.assembler;

import com.tuccicode.boot.app.task.dto.vo.TaskVO;
import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import com.tuccicode.boot.domain.task.entity.Task;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class TaskAssembler {

    public static TaskVO toVO(TaskDO dataObject) {
        if(dataObject == null){
            return null;
        }
        TaskVO vo = new TaskVO();
        BeanUtils.copyProperties(dataObject, vo);
        return vo;
    }
}
