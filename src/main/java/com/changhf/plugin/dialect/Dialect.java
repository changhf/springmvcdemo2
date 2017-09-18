package com.changhf.plugin.dialect;

/**
* @ClassName: Dialect 
* @Description: 数据库方言接口
 */
public interface Dialect {

    public static final String MYSQL_DIALECT = "mysql";
    public static final String SQLSERVER_DIALECT = "sqlserver";
    public static final String ORACLE_DIALECT = "oracle";

    public static enum Type {
        MYSQL {
            @Override
            public String toString() {
                return MYSQL_DIALECT;
            }
        },
        SQLSERVER {
            @Override
            public String toString() {
                return SQLSERVER_DIALECT;
            }
        },
        ORACLE {
            @Override
            public String toString() {
                return ORACLE_DIALECT;
            }
        }
    }

    /**
     * @Descrption  获取分页SQL
     * @param  sql 原始查询SQL
     * @param  offset 开始记录索引（从零开始）
     * @param  limit 每页记录大小
     * @param  orderBy 排序字段
     * @param  order 排序方向
     * @return  返回数据库相关的分页SQL语句
     */
    public abstract String getPageSql(String sql, int offset, int limit, String orderBy, String order);
}
