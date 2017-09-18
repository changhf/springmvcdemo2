package com.changhf.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.changhf.common.Constants;
import com.changhf.common.filter.SimplePropertyExcludeFilter;
import com.changhf.utils.ParamUtil;
import com.changhf.utils.WebUtil;
import com.changhf.utils.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebActionSupport {

    @Autowired
    protected RedisService redisService;
    public static final String RELOAD = "reload";
    public static final String DEFAULT_RETURN_URL = "index.action";
    protected Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /***********************************************/
    /**
     * post到input页面时，以redirect方式重新打开input页面
     */
    public static final String RELOAD_INPUT = "reload-input";
    /**
     * post到view页面时，以redirect方式重新打开view页面
     */
    public static final String RELOAD_VIEW = "reload-view";

    public static final String VIEW = "view";
    public static final String PROMPT = "prompt";
    /***********************************************/

    protected String searchParamsString = "";
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    @SuppressWarnings("unused")
    private String metaTitle;

    @ModelAttribute
    public void prepare() throws Exception {
        // 放置需要在所有或绝大部分Action执行前都需要初始化的一些东西
        // request.setAttribute("loginReturnUrl", getEncodingURI());//
        // /在此处设置登陆验证成功返回的url地址
    }

    protected int getPageOffset() {
        int pageIndex = ParamUtil.getIntParameter(request, "pageIndex", 0);
        return pageIndex * getPageSize();
    }

    protected int getPageOffsetNew() {
        int pageOffset = ParamUtil.getIntParameter(request, "pageOffset", 0);
        return pageOffset;
    }

    protected int getPageSize() {
        int pageSize = ParamUtil.getIntParameter(request, "pageSize", 20);
        return (pageSize > 100 || pageSize < 1) ? 20 : pageSize;
    }

    // protected Map<String, Object> getEncrptyParam() {
    // String param = ParamUtil.getStringParameter(request, "param", null);
    // return ParamUtils.decodeParam(param);
    // }

    /***
     * JSONP格式转化
     *
     * @param p
     *            JSON格式字符串：JSONObject,JSONArray callback jsonp参数
     * @return
     */
    public <P> void convertToJSONP(P p) {
        String callback = ParamUtil.getFilteredParameter(request, "jsonp", 0, "");

        WebUtil.returnJSON(response, callback + "(" + p.toString() + ")", "jsonp");
    }

    // protected void returnJSON(Object obj) {
    //     if (obj != null) {
    //         WebUtil.returnJSON(response, JSONObject.fromObject(obj).toString(), "json");
    //     } else {
    //         WebUtil.returnJSON(response, "{返回值为null}", "json");
    //     }
    //
    // }

    protected void returnFastJSON(Object obj) {
        if (obj != null) {
            WebUtil.returnJSON(response, JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue).toString(),
                    "json");
        } else {
            WebUtil.returnJSON(response, "{返回值为null\"}", "json");
        }

    }

    /**
     * <pre>
     *     需要排除哪些属性的对象或者集合转化为JSON（支持对象实体、集合List、Map）
     * </pre>
     *
     * @param obj        单个对象/集合/Map
     * @param clazz      对象的Class，比如HashMap.class
     * @param properties 哪些属性是需要排除的，可以通过逗号分隔或者字符串数组，如："name","age" 或者 String []strs =
     *                   {"name","age"}
     */
    protected void returnFastJsonExcludeProperties(Object obj, Class<?> clazz, String... properties) {
        if (obj != null) {
            SimplePropertyExcludeFilter filter = new SimplePropertyExcludeFilter(clazz, properties);
            WebUtil.returnJSON(response,
                    com.alibaba.fastjson.JSONObject.toJSONString(obj, filter, SerializerFeature.WriteMapNullValue),
                    "json");
        } else {
            WebUtil.returnJSON(response,
                    "{\"" + Constants.MOBILE_RECODE + "\":-1,\"" + Constants.MOBILE_MSG + "\":\"返回值为null\"}",
                    "json");
        }
    }

    /**
     * 返回json
     *
     * @param obj
     * @param properties
     */
    protected void returnFastJsonExcludeProperties(Object obj, String... properties) {
        if (obj != null) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            for (String str : properties) {
                filter.getExcludes().add(str);
            }
            WebUtil.returnJSON(response, JSONObject.toJSONString(obj, filter, SerializerFeature.WriteMapNullValue), "json");
        } else {
            WebUtil.returnJSON(response, "{\"" + Constants.MOBILE_RECODE + "\":-1,\"" + Constants.MOBILE_MSG + "\":\"返回值为null\"}", "json");
        }
    }
}
