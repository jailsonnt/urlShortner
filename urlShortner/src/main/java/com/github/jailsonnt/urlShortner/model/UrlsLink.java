package com.github.jailsonnt.urlShortner.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UrlsLink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String originalURL;
	private String createdURL;
	private Date expirationDate;
	
	public UrlsLink(String originalURL) {
		this.originalURL = originalURL;
	}

	public Long getId() {
		return id;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public String getCreatedURL() {
		return createdURL;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setCreatedURL(String createdURL) {
		this.createdURL = createdURL;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
