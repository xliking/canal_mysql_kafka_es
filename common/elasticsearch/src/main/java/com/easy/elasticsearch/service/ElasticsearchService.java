package com.easy.elasticsearch.service;

import com.easy.framework.bean.base.PageVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * elasticsearch服务
 * </p>
 *
 * @author muchi
 */
@Service
public class ElasticsearchService {
    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * 简单条件查询es集合
     * Criteria criteria = new Criteria("newsPublishTime").between(tongLianNewsInfo.getNewsPublishTime(), _36HoursAgo);
     * Query query = new CriteriaQuery(criteria);
     * query.addSort( Sort.by(Sort.Direction.DESC, "newsPublishTime.keyword"));
     *
     * @param query {@link Query} 条件
     * @param clazz 查询的实体类
     * @param <T>   泛型
     * @return {@link List}查询结果
     */
    public <T> List<T> simpleConditionalList(Query query, Class<T> clazz) {
        SearchHits<T> searchHits = elasticsearchOperations.search(query, clazz);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    /**
     * 简单条件查询es集合
     * Criteria = new Criteria("username").is("测试3").or("id").is("4");
     *
     * @param criteria {@link Criteria} 条件
     * @param clazz    查询的实体类
     * @param <T>      泛型
     * @return {@link List}查询结果
     */
    public <T> List<T> simpleConditionalList(Criteria criteria, Class<T> clazz) {
        Query query = new CriteriaQuery(criteria);
        SearchHits<T> searchHits = elasticsearchOperations.search(query, clazz);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    /**
     * 分页查询es
     *
     * @param criteria {@link Criteria} 条件
     * @param clazz    查询的实体类
     * @param current  页码1-X，默认会减1
     * @param size     每页数量
     * @param <T>      泛型
     * @return Page<T>
     */
    public <T> PageVO<T> page(Criteria criteria, Class<T> clazz, long current, long size) {
        PageRequest pageRequest = PageRequest.of((int) (current - 1), (int) size);
        return page(pageRequest, criteria, clazz, current, size);
    }

    /**
     * 分页查询es
     *
     * @param criteria {@link Criteria} 条件
     * @param clazz    查询的实体类
     * @param current  页码1-X，默认会减1
     * @param size     每页数量
     * @param <T>      泛型
     * @return Page<T>
     */
    public <T> PageVO<T> page(PageRequest pageRequest, Criteria criteria, Class<T> clazz, long current, long size) {
        Query query = new CriteriaQuery(criteria).setPageable(pageRequest);
        SearchHits<T> searchHits = elasticsearchOperations.search(query, clazz);
        List<T> esResult = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
        PageVO<T> page = new PageVO<>(current, size);
        page.setTotal(searchHits.getTotalHits());
        page.setRecords(esResult);
        return page;
    }


}