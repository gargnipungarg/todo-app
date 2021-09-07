package org.opensource.todo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoAddItemRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoAddItemMapper {
    TodoAddItemMapper INSTANCE = Mappers.getMapper(TodoAddItemMapper.class);
    TODOEntity sourceToDestination(TodoAddItemRequest todoTask);
}
