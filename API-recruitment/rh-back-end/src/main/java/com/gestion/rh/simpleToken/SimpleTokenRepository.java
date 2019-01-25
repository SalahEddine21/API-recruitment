package com.gestion.rh.simpleToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SimpleTokenRepository extends JpaRepository<SimpleToken, Long> {
	
	@Query("select t from SimpleToken t where t.tokenValue=:x")
	public SimpleToken getTokenByValue(@Param("x") String tokenValue);

}
