package com.changhf.service.oss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.changhf.common.ResponseMessageMap;
import com.changhf.service.oss.OssService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * oss业务实现类
 *
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/08/22
 */
@Service("ossService")
public class OssServiceImpl implements OssService {
    @Value("${endpoint}")
    private String endpoint;
    @Value("${accessKeyId}")
    private String accessKeyId;
    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Override
    public Map<String, Object> query() {
        Map<String, Object> rtnMap = Maps.newHashMap();
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<Bucket> buckets = client.listBuckets();
        rtnMap.put("list", buckets);
        return ResponseMessageMap.successMap(buckets);
    }
}
