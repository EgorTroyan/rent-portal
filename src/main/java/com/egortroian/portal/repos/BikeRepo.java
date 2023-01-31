package com.egortroian.portal.repos;

import com.egortroian.portal.domain.Bike;
import com.egortroian.portal.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BikeRepo extends CrudRepository<Bike, Long> {
    List<Bike> findByIdNotIn(List<Long> ids);
}
