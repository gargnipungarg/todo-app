package org.opensource.todo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);
    TODOEntity sourceToDestination(TodoTask todoTask);
}
