package me.wanghui.framework.registry;

/**
 * 服务注册表
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/1
 * Time: 下午3:16
 */
public interface ServiceRegistry {

    /**
     * 注册服务信息
     * @param serviceName 服务名称
     * @param serviceAddress 注册服务的地址
     */
    void register(String serviceName,String serviceAddress);
}