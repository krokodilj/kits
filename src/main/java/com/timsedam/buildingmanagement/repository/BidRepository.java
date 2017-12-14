package com.timsedam.buildingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Report;

public interface BidRepository  extends JpaRepository<Bid, Long> {

	List<Bid> findByReportBid(Report report);


}
