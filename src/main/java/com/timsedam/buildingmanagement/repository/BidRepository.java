package com.timsedam.buildingmanagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Report;

public interface BidRepository  extends JpaRepository<Bid, Long> {

	List<Bid> findByReportBid(Report report);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Bid b set b.status = ?1 where b.id = ?2")
	void setStatus(String status, Long id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Bid b set b.status = ?1 where b.reportBid = ?2")
	void setStatus(String status, Report reportBid);

}
