package com.github.jailsonnt.urlShortner.api;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jailsonnt.urlShortner.api.dto.ShortenedUrlDTO;
import com.github.jailsonnt.urlShortner.model.UrlsLink;
import com.github.jailsonnt.urlShortner.services.IUrlFinder;
import com.github.jailsonnt.urlShortner.services.IUrlShortnerService;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlExpiredException;
import com.github.jailsonnt.urlShortner.services.exceptions.UrlNotFoundException;

@CrossOrigin
@RestController
@RequestMapping()
public class UrlShortnerEndpoint {
	private Logger logger = LogManager.getLogger(getClass());
	private IUrlFinder urlFinder;
	private IUrlShortnerService urlShortnerService;

	@Autowired
	public UrlShortnerEndpoint(IUrlFinder urlFinder, IUrlShortnerService urlShortnerService) {
		this.urlFinder = urlFinder;
		this.urlShortnerService = urlShortnerService;
	}

	@PostMapping
	public ResponseEntity<ShortenedUrlDTO> shortenUrl(@RequestParam String originalUrl, @RequestParam Optional<Integer> daysOfValidity) {
		try {
			UrlsLink foundValidUrl = urlFinder.findOriginalUrl(originalUrl);
			logger.info("returning existing URL");
			return ResponseEntity.ok().body(new ShortenedUrlDTO(foundValidUrl));
		} catch (UrlNotFoundException e1) {
			return generateShortUrl(originalUrl, daysOfValidity);
		}
	}

	private ResponseEntity<ShortenedUrlDTO> generateShortUrl(String originalUrl, Optional<Integer> daysOfValidity) {
		try {
			UrlsLink shortenedUrl = urlShortnerService.shortenUrl(originalUrl, daysOfValidity);
			logger.info("returning new URL");
			return ResponseEntity.ok().body(new ShortenedUrlDTO(shortenedUrl));
		} catch (Exception e) {
			logger.error("Error creating new Url");
			return new ResponseEntity<ShortenedUrlDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{shortenedIdentifier}")
	public ResponseEntity<Void> acessarUrl(@PathVariable String shortenedIdentifier) {
		try {
			UrlsLink shortenedUrl = urlFinder.findCreatedUrl(shortenedIdentifier);
			HttpHeaders header = new HttpHeaders();
			header.add("Location", shortenedUrl.getOriginalURL());
			logger.info("Found Original URL: Redirecting");
			return new ResponseEntity<Void>(header, HttpStatus.FOUND);
		} catch (UrlExpiredException e) {
			logger.info("Expired URL");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		} catch (UrlNotFoundException e) {
			logger.info("URL not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

}
