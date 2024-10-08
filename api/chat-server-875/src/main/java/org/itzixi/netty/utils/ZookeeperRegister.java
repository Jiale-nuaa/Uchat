package org.itzixi.netty.utils;

import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther 风间影月
 */
public class ZookeeperRegister {

    public static void registerNettyServer(String nodeName,
                                           String ip,
                                           Integer port) throws Exception {
        CuratorFramework zkClient = CuratorConfig.getClient();
        String path = "/" + nodeName;
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT).forPath(path);
        } else {
            System.out.println(stat.toString());
        }

        // 创建对应的临时节点，值可以放在线人数，默认为初始化的0
        NettyServerNode serverNode = new NettyServerNode();
        serverNode.setIp(ip);
        serverNode.setPort(port);
        String nodeJson = JsonUtils.objectToJson(serverNode);

        zkClient.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path + "/im-", nodeJson.getBytes());
    }

    public static String getLocalIp() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress();
        System.out.println("本机IP地址：" + ip);
        return ip;
    }

    /**
     * 增加在线人数
     * @param serverNode
     */
    public static void incrementOnlineCounts(NettyServerNode serverNode) throws Exception {
        dealOnlineCounts(serverNode, 1);
    }

    /**
     * 减少在线人数
     * @param serverNode
     */
    public static void decrementOnlineCounts(NettyServerNode serverNode) throws Exception {
        dealOnlineCounts(serverNode, -1);
    }

    /**
     * 处理在线人数的增减
     * @param serverNode
     * @param counts
     */
    public static void dealOnlineCounts(NettyServerNode serverNode,
                                        Integer counts) throws Exception {

        CuratorFramework zkClient = CuratorConfig.getClient();

        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(zkClient,
                                                                        "/rw-locks");
        readWriteLock.writeLock().acquire();

        try {

            String path = "/server-list";
            List<String> list = zkClient.getChildren().forPath(path);
            for (String node:list) {
                String pendingNodePath = path + "/" + node;
                String nodeValue = new String(zkClient.getData().forPath(pendingNodePath));
                NettyServerNode pendingNode = JsonUtils.jsonToPojo(nodeValue,
                                                                   NettyServerNode.class);

                // 如果ip和端口匹配，则当前路径的节点则需要累加或者累减
                if (pendingNode.getIp().equals(serverNode.getIp()) &&
                        (pendingNode.getPort().intValue() == serverNode.getPort().intValue())) {
                    pendingNode.setOnlineCounts(pendingNode.getOnlineCounts() + counts);
                    String nodeJson = JsonUtils.objectToJson(pendingNode);
                    zkClient.setData().forPath(pendingNodePath, nodeJson.getBytes());
                }
            }

        } finally {
            readWriteLock.writeLock().release();
        }


    }

}

