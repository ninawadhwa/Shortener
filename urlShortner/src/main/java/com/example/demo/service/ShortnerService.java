/**
 * 
 */
package com.example.demo.service;

import java.util.Map;

import com.example.demo.dto.ShortnerDTO;

/**
 * @author Raveena
 *
 */
public interface ShortnerService {
	
	Map<String , String> saveUrl(ShortnerDTO dto);
	
	String findUrl(String url);

}
