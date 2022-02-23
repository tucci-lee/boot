package com.tuccicode.boot.task.application.assembler;

import com.tuccicode.boot.task.application.dto.vo.TaskVO;
import com.tuccicode.boot.task.domain.entity.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

/**
 * @author tucci.lee
 */
public class TaskAssembler {

    public static TaskVO toVO(Task entity) {
        if(entity == null){
            return null;
        }
        TaskVO vo = new TaskVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
