package com.github.jailsonnt.urlShortner.services.mock;

import com.github.jailsonnt.urlShortner.model.UrlsLink;

public class UrlsLinkMock extends UrlsLink{
	
	public UrlsLinkMock(UrlsLink urlsLink) {
		super(urlsLink.getOriginalURL());
		super.id = urlsLink.getId();
		super.setCreatedURL(urlsLink.getCreatedURL());
		super.setExpirationDate(urlsLink.getExpirationDate());
	}
	
	public void setId(Long id) {
		super.id = id;
	}
}
