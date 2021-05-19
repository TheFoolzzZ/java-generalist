package org.geektimes.projects.user.dubbo;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 虚拟节点一致性hash
 * @description
 * @Author chengde.tan
 * @Date 2021/5/18 22:43
 */
public class VirtualNodeConsistentHashing {

    //真实结点列表
    private final List<String> realNodes = new LinkedList<>();

    private final List<String> servers;

    //key表示服务器的hash值，value表示服务器
    private final SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    //虚拟节点的数目，一个真实结点对应5个虚拟节点
    private static final int VIRTUAL_NODES = 5;



    public static void main(String[] args) {
        String[] keys = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        VirtualNodeConsistentHashing node = new VirtualNodeConsistentHashing(Arrays.asList("192.168.0.1:8888", "192.168.0.2:8888", "192.168.0.3:8888", "192.168.0.4:8888"));
        for (String key : keys) {
            System.out.println("[" + key + "]的hash值为" + node.getHash(key) + ", 被路由到结点[" + node.getServer(key) + "]");
        }
        System.out.println("--------------------------------------------");
        node.addServer("192.168.0.5:8888");
        for (String key : keys) {
            System.out.println("[" + key + "]的hash值为" + node.getHash(key) + ", 被路由到结点[" + node.getServer(key) + "]");
        }
        System.out.println("--------------------------------------------");
        node.removeServer("192.168.0.1:8888");
        node.removeServer("192.168.0.2:8888");
        for (String key : keys) {
            System.out.println("[" + key + "]的hash值为" + node.getHash(key) + ", 被路由到结点[" + node.getServer(key) + "]");
        }
    }

    VirtualNodeConsistentHashing(List<String> servers) {
        this.servers = new ArrayList<>(servers.size());
        this.servers.addAll(servers);
        this.refreshNode();
    }

    public void addServer(String server) {
        this.servers.add(server);
        this.refreshNode();
    }

    public void removeServer(String server) {
        if (this.servers.contains(server)) {
            this.servers.remove(server);
            this.refreshNode();
        }
    }

    private void refreshNode() {
        this.virtualNodes.clear();
        this.realNodes.clear();
        this.realNodes.addAll(servers);

        //再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String str : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = str + "&&VN" + i;
                int hash = getHash(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }

    }


    //得到应当路由到的结点
    public String getServer(String key) {
        //得到该key的hash值
        int hash = getHash(key);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        String virtualNode;
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = virtualNodes.firstKey();
            //返回对应的服务器
            virtualNode = virtualNodes.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            virtualNode = subMap.get(i);
        }
        //virtualNode虚拟节点名称要截取一下
        if (StringUtils.isNotBlank(virtualNode)) {
//            return virtualNode.substring(0, virtualNode.indexOf("&&"));
            return virtualNode;
        }
        return null;

    }

    //使用FNV1_32_HASH算法计算服务器的Hash值
    public int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
