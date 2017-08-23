package com.changhf.service.oss;

import java.util.Map;

/**
 * oss操作接口
 *
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/08/22
 */
public interface OssService {
    /**
     * 查询存储空间列表
     *
     * @return
     */
    Map<String, Object> query();
}
