package com.mms.util.dto;

import org.modelmapper.ModelMapper;

public class DtoUtils {
 
  public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
    return new ModelMapper().map(obj, mapper.getClass());
  }
 
  public Object convertToEntity(Object obj, DTOEntity mapper) {
    return new ModelMapper().map(mapper, obj.getClass());
  }
 }