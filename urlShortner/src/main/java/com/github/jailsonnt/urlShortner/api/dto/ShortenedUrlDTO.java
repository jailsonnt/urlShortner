package com.github.jailsonnt.urlShortner.api.dto;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public class ShortenedUrlDTO {
	private String newUrl;
	private String expiresAt;
	
	public ShortenedUrlDTO(UrlsLink linkedUrls) {
		this.newUrl = linkedUrls.getCreatedURL();
		this.expiresAt = String.valueOf(linkedUrls.getExpirationDate().getTime());
	}

	public String getNewUrl() {
		return newUrl;
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}
}
