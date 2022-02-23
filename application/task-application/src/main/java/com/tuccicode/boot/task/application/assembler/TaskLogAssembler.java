package com.tuccicode.boot.task.application.assembler;

import com.tuccicode.boot.task.application.dto.vo.TaskLogVO;
import com.tuccicode.boot.task.domain.entity.TaskLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class TaskLogAssembler {

    public static TaskLogVO toVO(TaskLog entity) {
        if(entity == null){
            return null;
        }
        TaskLogVO vo = new TaskLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
