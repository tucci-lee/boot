package com.tuccicode.boot.app.task.assembler;

import com.tuccicode.boot.app.task.dto.vo.TaskLogVO;
import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class TaskLogAssembler {

    public static TaskLogVO toVO(TaskLogDO dataObject) {
        if(dataObject == null){
            return null;
        }
        TaskLogVO vo = new TaskLogVO();
        BeanUtils.copyProperties(dataObject, vo);
        return vo;
    }
}
