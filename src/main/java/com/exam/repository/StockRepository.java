package com.exam.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	/*
	 * 다음 메서드가 지원됨
	 * findAll()
	 * findById()
	 * count()
	 * delete()
	 * deleteById()
	 * 
	 * 이외의 변수로 조회하기 위해서는 추가로 메서드를 정의해야 된다.( Query Method 규칙을 따름 )
	 */
	
	@Query("SELECT s FROM Stock s WHERE s.expdate < CURRENT_DATE")
	List<Stock> findByExpdate(Date expdate);//Date expdate
	
}
