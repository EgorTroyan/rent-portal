package com.egortroian.portal.repos;

import com.egortroian.portal.domain.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepo extends CrudRepository<Booking, Long> {

    List<Booking> findAllByStartDateLessThanAndEndDateGreaterThan(LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> findByStartDateBetweenOrEndDateBetween(LocalDateTime startStart,
                                                         LocalDateTime startEnd,
                                                         LocalDateTime endStart,
                                                         LocalDateTime endEnd);
}
