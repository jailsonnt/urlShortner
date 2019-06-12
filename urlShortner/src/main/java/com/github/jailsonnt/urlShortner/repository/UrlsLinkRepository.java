package com.github.jailsonnt.urlShortner.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public interface UrlsLinkRepository extends JpaRepository<UrlsLink, Long> {
	
}
