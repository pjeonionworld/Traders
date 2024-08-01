package com.exam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

	List<Movement> findByOrdercode(Long ordercode);
	List<Movement> findByMovdate(LocalDate movdate);
	
	@Query("SELECT m.movdate, count(m) FROM Movement m GROUP BY m.movdate order by m.movdate desc")
    List<Object[]> findGroupedByMovdate();
    
    List<Movement> findAllByOrderByMovdateAsc(); // 날짜순으로 모든 이동 데이터를 찾기 위한 새로운 메서드
    List<Movement> findAllByOrderByMovdateDesc();
    
    // 모바일 - gcode 로 데이터 조회
    List<Movement> findByGcode(String gcode);
    
}
