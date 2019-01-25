package com.gestion.rh.offre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OffreRepository extends JpaRepository<Offre, Long> {

	@Query("select o from Offre o where o.employee.id=:x")
	public List<Offre> getOffresByEmployee(@Param("x")long emplyeeId);
	
	@Query("select o from Offre o where o.id=:x")
	public Offre getOffreByID(@Param("x")long offreId);
	
	@Query("select o from Offre o order by o.date desc")
	public List<Offre> getallOffres();
	
	@Query("select o from Offre o where o.employee.entreprise.city=:x order by o.date desc")
	public List<Offre> searchByCity(@Param("x")String city);
	
	@Query("select o from Offre o where o.domaine=:x order by o.date desc")
	public List<Offre> searchByDomain(@Param("x")String domaine);
	
	@Query("select o from Offre o where o.type=:x order by o.date desc")
	public List<Offre> searchByType(@Param("x")String type);
	
	@Query("select o from Offre o where o.type=:x and o.domaine=:y order by o.date desc")
	public List<Offre> searchByTypeDomain(@Param("x")String type, @Param("y")String domaine);
	
	@Query("select o from Offre o where o.type=:x and o.employee.entreprise.city=:y order by o.date desc")
	public List<Offre> searchByTypeCity(@Param("x")String type, @Param("y")String city);
	
	@Query("select o from Offre o where o.domaine=:x and o.employee.entreprise.city=:y order by o.date desc")
	public List<Offre> searchByDomainCity(@Param("x")String domaine, @Param("y")String city);
	
	@Query("select o from Offre o where o.domaine=:x and o.type=:y and o.employee.entreprise.city=:z order by o.date desc")
	public List<Offre> searchByDomainTypeCity(@Param("x")String domaine, @Param("y")String type, @Param("z")String city);
	
}
