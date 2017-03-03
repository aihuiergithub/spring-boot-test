package me.wanghui.framework.registry;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 向zookeeper注册服务节点
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/1
 * Time: 下午3:18
 */
@Component
public class ServiceRegistryImpl implements ServiceRegistry, Watcher {

    private static final String REGISTRY_PATH = "/registry";
    private static final int SESSION_TIMEOUT = 5000;

    private Logger logger = Logger.getLogger(ServiceRegistryImpl.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private ZooKeeper zk;

    public ServiceRegistryImpl() {
    }

    public ServiceRegistryImpl(String zkServers) {
        //创建zookeeper客户端
        try {
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();
            logger.debug("connected to zookeeper");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create zookeeper client failure", e);
        }

    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        //创建跟节点
        String registryPath = REGISTRY_PATH;
        try {
            //创建节点,创建
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.debug("create registry node :" + registryPath);
            }
            //创建服务节点,持久节点
            String servicePath = registryPath + "/" + serviceName;
            servicePath =  servicePath.replace("//", "/");
            if (zk.exists(servicePath, false) == null) {
                String addressNode = zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.debug("create service node:" + addressNode);
            }
            //创建地址节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.debug("create address node serviceAddress:" + serviceAddress + " addressNode" + addressNode);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create node failure", e);
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }


    /**
     *
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
     *@author cuiran
     *@createDate 2013-01-16 15:08:38
     *@param path
     *@param data
     *@throws KeeperException
     *@throws InterruptedException
     */
    public void create(String path,byte[] data)throws KeeperException, InterruptedException{
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
        this.zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    /**
     *
     *<b>function:</b>获取节点信息
     *@author cuiran
     *@createDate 2013-01-16 15:17:22
     *@param path
     *@throws KeeperException
     *@throws InterruptedException
     */
    public void getChild(String path) throws KeeperException, InterruptedException{
        try{
            List<String> list=this.zk.getChildren(path, false);
            if(list.isEmpty()){
                logger.debug(path+"中没有节点");
            }else{
                logger.debug(path+"中存在节点");
                for(String child:list){
                    logger.debug("节点为："+child);
                }
            }
        }catch (KeeperException.NoNodeException e) {
            // TODO: handle exception
            throw e;

        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return  this.zk.getData(path, false,null);
    }

//    public static void main(String[] args) {
//        try {
//            ZooKeeperOperator zkoperator             = new ZooKeeperOperator();
//            zkoperator.connect("192.168.0.138");
//
//            byte[] data = new byte[]{'a','b','c','d'};
//
////              zkoperator.create("/root",null);
////              System.out.println(Arrays.toString(zkoperator.getData("/root")));
////
////              zkoperator.create("/root/child1",data);
////              System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));
////
////              zkoperator.create("/root/child2",data);
////              System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));
//
//            String zktest="ZooKeeper的Java API测试";
//            zkoperator.create("/root/child3", zktest.getBytes());
//            log.debug("获取设置的信息："+new String(zkoperator.getData("/root/child3")));
//
//            System.out.println("节点孩子信息:");
//            zkoperator.getChild("/root");
//
//            zkoperator.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
