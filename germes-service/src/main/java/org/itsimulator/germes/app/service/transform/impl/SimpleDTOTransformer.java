package org.itsimulator.germes.app.service.transform.impl;

import org.itsimulator.germes.app.infra.util.Checks;
import org.itsimulator.germes.app.infra.util.CommonUtil;
import org.itsimulator.germes.app.infra.util.ReflectionUtil;
import org.itsimulator.germes.app.model.entity.base.AbstractEntity;
import org.itsimulator.germes.app.rest.dto.base.BaseDTO;
import org.itsimulator.germes.app.service.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleDTOTransformer implements Transformer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleDTOTransformer.class);
	
	public <T extends AbstractEntity, P extends BaseDTO<T>> P transform(
			final T entity, final Class<P> clz) {
		checkParams(entity, clz);
		P dto = ReflectionUtil.createInstance(clz);
		//Now just copy all similar fields
		ReflectionUtil.copyFields(entity, dto, 
				ReflectionUtil.findSimilarFields(entity.getClass(), clz));
		dto.transform(entity);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object",
					CommonUtil.toString(dto));
		}
		return dto;
	}
	
	private void checkParams(final Object param, final Class<?> clz) {
		Checks.checkParameter(param != null, "Source transformation object is not initialized");
		Checks.checkParameter(clz != null, "No class is defined for transformation");
	}
	
	public <T extends AbstractEntity, P extends BaseDTO<T>> T untransform(
			final P dto, final Class<T> clz) {
		checkParams(dto, clz);
		T entity = ReflectionUtil.createInstance(clz);
		ReflectionUtil.copyFields(dto, entity, 
				ReflectionUtil.findSimilarFields(dto.getClass(), clz));
		dto.unstansform(entity);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SimpleDTOTransformer.untransform: {} entity",
					CommonUtil.toString(entity));
		}

		
		return entity;
	}
}
