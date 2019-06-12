package com.github.jailsonnt.urlShortner.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public interface UrlsLinkRepository extends JpaRepository<UrlsLink, Long> {
	@Query("SELECT u FROM UrlsLink u WHERE u.originalURL = ?1 and u.expirationDate > ?2")
	public Optional<List<UrlsLink>> findByOriginalURLAndExpirationDate(String originalURL, Date now);
	
	//Before findByStartDateBefore … where x.startDate < ?1
	
}
