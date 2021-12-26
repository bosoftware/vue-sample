package vuesample.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import vuesample.entity.User;
import vuesample.model.UserVo;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserVo, User> {

}
