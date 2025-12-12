package sunshine.interfaces.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sunshine.interfaces.api.service.SearchService;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/search")
	public Map<String, Object> searchWeather(@RequestParam String city) {

		return searchService.searchWeather(city);
	}
}
