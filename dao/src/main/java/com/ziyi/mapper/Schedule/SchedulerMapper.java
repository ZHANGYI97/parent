package com.ziyi.mapper.Schedule;

import com.ziyi.entity.Schedule.ParamMap;
import com.ziyi.entity.Schedule.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SchedulerMapper {

    int delete(Long jobId);

    int insert(ScheduleJob record);

    ScheduleJob selectByClassAndMethod(ParamMap paramMap);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int update(ScheduleJob record);

    List<ScheduleJob> getAll();

}
