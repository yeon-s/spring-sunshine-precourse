package sunshine.interfaces.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final RestTemplate restTemplate;

	public Map<String, Object> searchWeather(String city) {

		Map<String,Object> matrix =  getMatrix(city);

		String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s"
			+ "&current=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m,apparent_temperature&wind_speed_unit=ms", matrix.get("latitude"), matrix.get("longitude"));

		Map<String,Object> response = restTemplate.getForObject(url,Map.class);

		return response;
	}

	public Map<String, Object> getMatrix(String city) {

		String url = String.format("https://geocoding-api.open-meteo.com/v1/search?name=%s&count=10&language=en&format=json", city);

		List<Map<String,Object>> response = (List<Map<String,Object>>) restTemplate.getForObject(url,Map.class).get("results");

		Map<String, Object> matrix = (Map<String,Object>) response.get(0);

		return matrix;
	}
}
