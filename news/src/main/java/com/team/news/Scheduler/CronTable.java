package com.team.news.Scheduler;

import com.team.news.Analysis.Morphological;
import com.team.news.Repository.MainNewsListRepository;
import com.team.news.Repository.NewsRepository;
import com.team.news.WebCrawler.Crawling_naver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 스케줄러가 할 일 정의
 */
@Component
public class CronTable {

    private final NewsRepository newsRepository;
    private final MainNewsListRepository mainNewsListRepository;

    @Autowired
    public CronTable(NewsRepository newsRepository, MainNewsListRepository mainNewsListRepository) {
        this.newsRepository = newsRepository;
        this.mainNewsListRepository = mainNewsListRepository;
    }

//    // 매일 21시 30분 0초에 실행
//    @Scheduled(cron = "0 30 21 * * *")
//    public void dayJob() {
//
//    }
//
//    // 매월 1일 0시 0분 0초에 실행
//    @Scheduled(cron = "0 0 0 1 * *")
//    public void monthJob() {
//
//    }

    //서버 시작하고 10초후에 실행 후 30분마다 실행
    @Scheduled(initialDelay = 10000, fixedDelay = 1800000)
    public void Job() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm ");
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.MINUTE, -30 );    // 30분 이내
        String beforeTime = date.format(cal.getTime());

        Crawling_naver crawlingNaver = new Crawling_naver();
        crawlingNaver.start();

        Morphological morphological = new Morphological();
        morphological.analysis(newsRepository, mainNewsListRepository, beforeTime);




    }
}
