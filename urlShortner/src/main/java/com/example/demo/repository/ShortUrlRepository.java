/**
 * 
 */
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ShortUrl;

/**
 * @author Raveena
 *
 */
@Repository
public interface ShortUrlRepository  extends JpaRepository<ShortUrl, Integer>{

	List<ShortUrl> findByShortCode(String url);

}
