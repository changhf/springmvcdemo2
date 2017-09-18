package com.changhf.plugin.dialect;

/***
* @ClassName: MySqlDialect 
* @Description: MySql数据库方言实现
 */
public class MySqlDialect implements Dialect {

    public String getPageSql(String sql, int offset, int limit, String orderBy, String order) {
        sql = getLineSql(sql);
        if (orderBy != null && order != null && sql.toLowerCase().lastIndexOf("order by") == -1) {
            sql = sql + " order by ";
            String[] orderBys = orderBy.split(",");
            String[] orders = order.split(",");

            if (orderBys.length == orders.length) {
                for (int i = 0; i < orderBys.length; i++) {
                    sql += orderBys[i] + " " + orders[i] + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
            }
        }
        if (limit != 0) {
            sql = sql + " limit " + offset + " ," + limit;
        }
        return sql;
    }

    private String getLineSql(String sql) {
        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
    }

    public static final void main(String[] args) {
        String sql = "select id,name , size ,width  from   t_app ";
        Dialect dialect = new MySqlDialect();
        System.out.println(dialect.getPageSql(sql, 0, 20, "id,name", "desc,asc"));
    }

}
