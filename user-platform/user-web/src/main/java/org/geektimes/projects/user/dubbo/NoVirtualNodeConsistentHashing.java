package org.geektimes.projects.user.dubbo;

import java.util.*;

/**
 * 虚拟节点一致性hash
 * @description
 * @Author chengde.tan
 * @Date 2021/5/18 22:43
 */
public class NoVirtualNodeConsistentHashing {
    private final List<String> servers;

    //key表示服务器的hash值，value表示服务器
    private final SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();


    public static void main(String[] args) {
        String[] keys = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        NoVirtualNodeConsistentHashing node = new NoVirtualNodeConsistentHashing(Arrays.asList("192.168.0.1:8888", "192.168.0.2:8888", "192.168.0.3:8888", "192.168.0.4:8888"));
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

    NoVirtualNodeConsistentHashing(List<String> servers) {
        this.servers = new LinkedList<String>();
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
        this.sortedMap.clear();
        for (String server : servers) {
            int hash = getHash(server);
            System.out.println("[" + server + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, server);
        }
    }


    //得到应当路由到的结点
    public String getServer(String key) {
        //得到该key的hash值
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
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
