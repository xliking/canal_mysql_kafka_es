package com.easy.api.bean.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.api.bean.annotations.ElasticField;
import com.easy.api.bean.annotations.ElasticSync;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

/**
 * @author muchi
 */
@Data
@ElasticSync(indexName = "ai.test")
@TableName("test")
@Repository
@ToString
public class muTest {

    @Id
    @ElasticField(name = "id")
    private Long id;

    @ElasticField(name = "thisHeight")
    private String height;

    @ElasticField(name = "thisWidth")
    private int width;

}
