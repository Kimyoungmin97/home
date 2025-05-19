package com.ssafy.home.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.home.user.service.HouseRedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiUtil {
	
	// 실행 오류나서 redis 주석 처리해둠(혜원)
	// private final HouseRedisService houseRedisService;
	
	@Value("${api.key_data}")
    private String serviceKey;
	
	public void fetchAndStore() throws ParseException, UnsupportedEncodingException {
		String lawdCd = "11710"; // 송파구
        String dealYmd = "202403";

        String url = "https://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev"
    		+ "?serviceKey=" + URLEncoder.encode(serviceKey,StandardCharsets.UTF_8.toString())
            + "&LAWD_CD=" + lawdCd
            + "&DEAL_YMD=" + dealYmd
            + "&_type=json"
            + "&numOfRows=100";
        URI uri = URI.create(url);
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);
        log.debug("호출 uri:{}", uri);
        log.debug("응답 json:{}", json);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
        JSONArray jsonItemList = (JSONArray) jsonItems.get("item");
        for (Object o : jsonItemList) {
            JSONObject item = (JSONObject) o;
//            House house = House.builder()
//                    .aptNm(String.valueOf(item.get("aptNm")))
//                    .dealAmount(String.valueOf(item.get("dealAmount")))
//                    .dealYear(String.valueOf(item.get("dealYear")))
//                    .dealMonth(String.valueOf(item.get("dealMonth")))
//                    .dealDay(String.valueOf(item.get("dealDay")))
//                    .excluUseAr(String.valueOf(item.get("excluUseAr")))
//                    .floor(String.valueOf(item.get("floor")))
//                    .aptDong(String.valueOf(item.get("aptDong")))
//                    .jibun(String.valueOf(item.get("jibun")))
//                    .buildYear(String.valueOf(item.get("buildYear")))
//                    .sggCd(String.valueOf(item.get("sggCd")))
//                    .umdCd(String.valueOf(item.get("umdCd")))
//                    .umdNm(String.valueOf(item.get("umdNm")))
//                    .roadNmSggCd(String.valueOf(item.get("roadNmSggCd")))
//                    .roadNm(String.valueOf(item.get("roadNm")))
//                    .roadNmBonbun(String.valueOf(item.get("roadNmBonbun")))
//                    .roadNmBubun(String.valueOf(item.get("roadNmBubun")))
//                    .build();
//
//                house.setId(houseRedisService.generateId(house));
//                houseRedisService.saveToRedis(house);
        }

        System.out.println("API로부터 가져온 데이터를 Redis에 저장 완료!");
	}
	
    @Scheduled(initialDelay = 5000, fixedDelay = Long.MAX_VALUE)
    public void runOnceOnStartup() throws Exception {
        fetchAndStore();
    }
    
    @Scheduled(cron = "0 0 5 * * *")
    public void fetchEveryMorning() throws Exception {
        fetchAndStore();
    }
}
