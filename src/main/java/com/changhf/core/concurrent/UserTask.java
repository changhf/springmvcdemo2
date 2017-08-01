package com.changhf.core.concurrent;

import com.changhf.domain.ValueEvent;

public class UserTask extends AbstractLoanTask {
//    private Logger logger = Logger.getLogger(TenderTask.class);

    public UserTask() {
        super();
        task.setName("User.Task");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doLoan() {

        JobQueue<ValueEvent> queue = JobQueue.getUserInstance();
//        UserCacheService userCacheService = (UserCacheService) BeanUtil.getBean("userCacheService");
//        IpsService ipsService=(IpsService)BeanUtil.getBean("ipsService");
        while (!queue.isEmpty()) {
            ValueEvent event = queue.peek();//返回队列头部的元素，如果为空，返回null
            if (event != null) {
                String result = "success";
                try {
                	System.out.println("something is executing ------");
//                    if ("ipsRegister".equals(event.getOperate())) {
//                        //环讯开户
//                        userCacheService.ipsRegisterCall(event.getUser(), event.getIpsRegister());
//                    }else if ("doIpsRegisterGuarantor".equals(event.getOperate())) {
//                        //环讯担保开户回调处理
//                        ipsService.doIpsRegisterGuarantor(event.getBorrowModel());
//                    }
                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                    if (e instanceof BussinessException) {// 业务异常，保存业务处理信息
//                        result = e.getMessage();
//                    } else {
//                        result = "系统异常，业务处理失败";
//                    }
                } finally {
                    queue.remove(event);//移除返回队列头部的元素，如果为空，返回NoSuchElementException
                }
//                if (event.getResultFlag() != null) {// 在需要保存系统处理信息的地方直接保存进来
//                    Global.RESULT_MAP.put(event.getResultFlag(), result);
//                }
            }
        }

    }

    @Override
    public Object getLock() {
        return UserTask.USER_STATUS;
    }

}