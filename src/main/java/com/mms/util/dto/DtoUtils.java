package com.mms.util.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.mms.dto.BasicCampaignDto;
import com.mms.model.Campaign;

public class DtoUtils {

	public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
		return new ModelMapper().map(obj, mapper.getClass());
	}

	public Object convertToEntity(Object obj, DTOEntity mapper) {
		return new ModelMapper().map(mapper, obj.getClass());
	}

	public Object convertToCampaignEntity(Object obj, DTOEntity mapper, PropertyMap<BasicCampaignDto, Campaign> skipProperties) {
	    ModelMapper modelMapper = new ModelMapper();
	    
	    modelMapper.addMappings(skipProperties);
	    
	    return modelMapper.map(mapper, obj.getClass());
	}
}