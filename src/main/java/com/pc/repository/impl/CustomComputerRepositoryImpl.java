package com.pc.repository.impl;

import com.pc.entity.Computer;
import com.pc.repository.CustomComputerRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomComputerRepositoryImpl implements CustomComputerRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public List<Computer> select(Integer page, Integer limit, String name) {
        //查询策略
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("computer_name", name);
        //高亮查询 匹配所有属性
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*");
        //综合查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQueryBuilder)
                .withHighlightFields(highlightBuilder)
                .withPageable(PageRequest.of(page - 1, limit))
                .build();
        //执行查询
        AggregatedPage<Computer> computers = elasticsearchTemplate.queryForPage(searchQuery, Computer.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                //获取查询数据
                SearchHits hits = searchResponse.getHits();
                List<Computer> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    //获取非高亮部分数据
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //获取高亮部分数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField computer_name = highlightFields.get("computer_name");
                    if (computer_name != null) {
                        String s = computer_name.fragments()[0].toString();
                        //替换
                        sourceAsMap.put("computer_name", s);

                        Computer computer = new Computer();
                        if (sourceAsMap.get("computer_id") != null) computer.setComputer_id(Long.parseLong(sourceAsMap.get("computer_id").toString()));
                        if (sourceAsMap.get("computer_name") != null) computer.setComputer_name(sourceAsMap.get("computer_name").toString());
                        if (sourceAsMap.get("computer_price") != null) computer.setComputer_price(Double.parseDouble(sourceAsMap.get("computer_price").toString()));
                        if (sourceAsMap.get("computer_img") != null) computer.setComputer_img(sourceAsMap.get("computer_img").toString());
                        if (sourceAsMap.get("brand_id") != null) computer.setBrand_id(Long.parseLong(sourceAsMap.get("brand_id").toString()));
                        list.add(computer);
                    }
                }

                //返回包装后的数据
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });
        return computers.getContent();
    }
    @Override
    public List<Computer> selectPage(Integer page, Integer limit){
        //查询策略
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //综合查询
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(matchAllQueryBuilder)
                .withPageable(PageRequest.of(page - 1, limit))
                .build();
        //执行查询
        AggregatedPage<Computer> computers = elasticsearchTemplate.queryForPage(build, Computer.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                List<Computer> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Computer computer = new Computer();
                    if (sourceAsMap.get("computer_id") != null) computer.setComputer_id(Long.parseLong(sourceAsMap.get("computer_id").toString()));
                    if (sourceAsMap.get("computer_name") != null) computer.setComputer_name(sourceAsMap.get("computer_name").toString());
                    if (sourceAsMap.get("computer_price") != null) computer.setComputer_price(Double.parseDouble(sourceAsMap.get("computer_price").toString()));
                    if (sourceAsMap.get("computer_img") != null) computer.setComputer_img(sourceAsMap.get("computer_img").toString());
                    if (sourceAsMap.get("brand_id") != null) computer.setBrand_id(Long.parseLong(sourceAsMap.get("brand_id").toString()));
                    list.add(computer);
                }
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });
        return computers.getContent();
    }
}
