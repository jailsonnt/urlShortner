package com.github.jailsonnt.urlShortner.services;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;
import com.github.jailsonnt.urlShortner.services.mock.UrlLinkRepositoryMock;

public class UrlShortnerServiceUT {
	private IUrlFinder urlFinderService;
	private IUrlShortnerService urlShortnerService;

	
	@Before
	public void init() {
		UrlLinkRepositoryMock repositorio = new UrlLinkRepositoryMock();
		this.urlFinderService = new UrlShortenerService(repositorio);
		this.urlShortnerService = new UrlShortenerService(repositorio);
		try {
			this.urlShortnerService.shortenUrl("urlOriginalTest1", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest2", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest3", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest4", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest5", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest6", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest7", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest8", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest9", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest10", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest11", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest12", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest13", Optional.of(5));
			this.urlShortnerService.shortenUrl("urlOriginalTest10", Optional.of(0));
			this.urlShortnerService.shortenUrl("urlOriginalTest11", Optional.of(0));
			this.urlShortnerService.shortenUrl("urlOriginalTest12", Optional.of(0));
			this.urlShortnerService.shortenUrl("urlOriginalTest13", Optional.of(0));
			this.urlShortnerService.shortenUrl("urlOriginalTest14", Optional.of(0));
			this.urlShortnerService.shortenUrl("urlOriginalTest1", Optional.of(10));
			this.urlShortnerService.shortenUrl("urlOriginalTest15", Optional.of(10));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreatedUrlOriginalUrl() {
		try {
			UrlsLink url = urlFinderService.findCreatedUrl("shorta");
			assertEquals("urlOriginalTest10", url.getOriginalURL());
		} catch (UrlExpiredException | UrlNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			UrlsLink url = urlFinderService.findCreatedUrl("short14");
			assertEquals("urlOriginalTest15", url.getOriginalURL());
		} catch (UrlExpiredException | UrlNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOriginalUrlId() {
		try {
			UrlsLink url = urlFinderService.findOriginalUrl("urlOriginalTest13");
			assertEquals(new Long(13), url.getId());
		} catch (UrlNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			UrlsLink url = urlFinderService.findOriginalUrl("urlOriginalTest15");
			assertEquals(new Long(20), url.getId());
		} catch (UrlNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExceptions() {
		try {
			UrlsLink url = urlFinderService.findCreatedUrl("shorte");
		} catch (UrlExpiredException | UrlNotFoundException e) {
			assertEquals(new UrlExpiredException(), e);
		}
		
		try {
			UrlsLink url = urlFinderService.findCreatedUrl("short15");
		} catch (UrlExpiredException | UrlNotFoundException e) {
			assertEquals(new UrlNotFoundException(), e);
		}
		
		try {
			UrlsLink url = urlFinderService.findOriginalUrl("urlOriginalTest23");
		} catch (UrlNotFoundException e) {
			assertEquals(new UrlNotFoundException(), e);
		}
		
		try {
			UrlsLink url = urlFinderService.findOriginalUrl("urlOriginalTest14");
		} catch (UrlNotFoundException e) {
			assertEquals(new UrlNotFoundException(), e);
		}
	}

}
