package com.example.demo.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ShortnerDTO;
import com.example.demo.entity.ShortUrl;
import com.example.demo.repository.ShortUrlRepository;
import com.example.demo.service.ShortnerService;
import com.example.demo.util.EnvUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Raveena
 *
 */
@Slf4j
@Service
public class ShortnerServiceImpl implements ShortnerService {

	
	 Logger logger = LoggerFactory.getLogger(ShortnerServiceImpl.class);
	 
	@Autowired
	private ShortUrlRepository repository;

	@Autowired
	private EnvUtil envUtil;

	@Override
	public Map<String, String> saveUrl(ShortnerDTO dto) {
		Map<String, String> response = new LinkedHashMap<String, String>();
		try {
			if (dto.getUrl() != null && !dto.getUrl().isEmpty()) {
				ShortUrl shortUrl = new ShortUrl();
				if (dto.getCustomCode() != null && !dto.getCustomCode().isEmpty()) {
					List<ShortUrl> urls = repository.findByShortCode(dto.getCustomCode());
					if (urls != null && !urls.isEmpty() &&  urls.get(0) != null && urls.get(0).getFullUrl() != null) {
						response.put("responseCode", "400");
						response.put("responseMessage", "Custome Code already exist.");
						return response;
					} else {
						shortUrl.setShortCode(dto.getCustomCode());
						shortUrl.setShortUrl(envUtil.getServerUrlPrefi() + "/" + dto.getCustomCode());
						shortUrl.setCustomUrl(true);
					}
				} else {
					int count = 8;
					String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
					StringBuilder builder = new StringBuilder();
					while (count-- != 0) {
						int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
						builder.append(ALPHA_NUMERIC_STRING.charAt(character));
					}
					shortUrl.setShortCode(builder.toString());
					shortUrl.setShortUrl(envUtil.getServerUrlPrefi() +"/" + builder.toString());
					shortUrl.setCustomUrl(false);
				}
				shortUrl.setFullUrl(dto.getUrl());
				shortUrl.setHit(1);
				repository.save(shortUrl);
				response.put("responseCode", "200");
				response.put("responseMessage", "Sucess");
				response.put("URL", shortUrl.getShortUrl());
			}else {
				response.put("responseCode", "400");
				response.put("responseMessage", "Please Enter url.");
			}
		} catch (Exception e) {
			logger.error("Error in save Url method : ", e);
			response.put("responseCode", "500");
			response.put("responseMessage", "Internal Server Error");
		}
		return response;
	}

	@Override
	public String findUrl(String url) {
		try {
			List<ShortUrl> urls = repository.findByShortCode(url);
			if (urls != null && !urls.isEmpty()  && urls.get(0) != null && urls.get(0).getFullUrl() != null) {
				ShortUrl shortUrl = urls.get(0);
				if (shortUrl.getHit() == null) {
					shortUrl.setHit(1);
				} else {
					shortUrl.setHit(shortUrl.getHit() + 1);
				}
				repository.save(shortUrl);
				return urls.get(0).getFullUrl();
			}
		} catch (Exception e) {
			logger.error("Error in Find url method : ", e);
		}
		return null;
	}
}
