package com.exam.repository;


import java.sql.Date;
import java.util.List;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.dto.GnameSummaryDTO;
import com.exam.dto.StockDTO;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Goods;
import com.exam.entity.Movement;
import com.exam.entity.OrderCart;
import com.exam.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	@Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3 WHERE s.goods.gcode = :gcode")
    void updateLocationByGcode(String gcode, String loc1, String loc2, String loc3);
	
	//유통기한 지난 재고 상품들 disuse테이블로 데이터 저장 시키기
	@Query("SELECT s FROM Stock s WHERE s.expdate < CURRENT_DATE")
	List<Stock> findByExpdate(Date expdate);
	//stock테이블에서 유통기한 안 지난 재고 상품들만 보여주기
	@Query("SELECT s FROM Stock s WHERE s.expdate >= :currentDate")
    List<Stock> findAllValidStocks(LocalDate currentDate);
	

	// stock 테이블 + goods 테이블
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods")
    List<Stock> findAllWithGoods();
    
    // 특정 gcode에 대한 stock 데이터 조회
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods WHERE s.goods.gcode = :gcode")
    List<Stock> findByGcode(String gcode);

	List<Stock> findByGoodsGcode(String gcode);
	
	// 모바일 - 상세정보페이지 위치 업데이트
    @Modifying
    @Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3 WHERE s.goods.gcode = :gcode")
    void mobileUpdateLocationByGcode(String gcode, String loc1, String loc2, String loc3);
    
    //관리자 메인화면 지점 순위 막대그래프
    @Query("SELECT s.user.branchId, s.user.branchName, count(s) FROM Stock s GROUP BY s.user.branchId")
    List<Object[]> countStocksByBranch();

    // branchId 로 Stock 조회
    // 유통기한 안 지난 재고 상품들만 branchid를 기준으로 가져오기
    @Query("SELECT s FROM Stock s WHERE s.expdate >= :currentDate AND s.user.branchId = :branchId")
    List<Stock> findByBranchIdStock(@Param("currentDate") LocalDate currentDate, @Param("branchId") String branchId);
 
    //관리자 메인화면 상품순위 도넛그래프
    @Query("SELECT g.gname, SUM(s.stockquantity) as summ FROM Stock s JOIN s.goods g GROUP BY g.gname order by summ desc")
    List<Object[]> findGnameSummaries();
    

    
}
