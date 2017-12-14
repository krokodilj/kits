package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Bid;

public interface BidRepository  extends JpaRepository<Bid, Long> {

}
