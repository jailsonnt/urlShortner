package com.github.jailsonnt.urlShortner.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	private IUrlFinder urlFinder;
	private IUrlShortnerService urlShortnerService;
	@Value("${urlShortener.baseurl:http://localhost:8443}")
	private String urlBase;

	@Autowired
	public UrlShortnerEndpoint(IUrlFinder urlFinder, IUrlShortnerService urlShortnerService) {
		this.urlFinder = urlFinder;
		this.urlShortnerService = urlShortnerService;
	}

	@PostMapping
	public ResponseEntity<ShortenedUrlDTO> shortenUrl(@RequestParam String originalUrl) {
		try {
			UrlsLink shortenedUrl = urlShortnerService.shortenUrl(originalUrl, urlBase);
			return ResponseEntity.ok().body(new ShortenedUrlDTO(shortenedUrl));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ShortenedUrlDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{shortenedIdentifier}")
	public ResponseEntity<Void> acessarUrl(@PathVariable String shortenedIdentifier) {
		try {
			UrlsLink shortenedUrl = urlFinder.findUrl(shortenedIdentifier);
			HttpHeaders header = new HttpHeaders();
			header.add("Location", shortenedUrl.getOriginalURL());
			return new ResponseEntity<Void>(header, HttpStatus.FOUND);
		} catch (UrlExpiredException e) {
			return new ResponseEntity<Void>(HttpStatus.GONE);
		} catch (UrlNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

}
