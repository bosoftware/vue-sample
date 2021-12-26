package vuesample.model.mapper;

import java.util.List;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
public interface EntityMapper<D, E> {

	E toEntity(D dto);

	D toDto(E entity);

	List<E> toEntity(List<D> dto);

	List<D> toDto(List<E> entity);
}
