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
@ElasticSync(indexName = "ai.muchi")
@TableName("muchi")
@Repository
@ToString
public class muChi {

    @Id
    @ElasticField(name = "id")
    private String id;

    @ElasticField(name = "userName")
    private String name;
}
