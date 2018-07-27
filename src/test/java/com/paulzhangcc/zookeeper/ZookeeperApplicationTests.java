package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.zookeeper.ChildListener;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;
import com.alibaba.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;
import com.alibaba.dubbo.remoting.zookeeper.zkclient.ZkclientZookeeperTransporter;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZookeeperApplicationTests {
	public static void nativeClient() throws IOException{
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", (int) TimeUnit.MINUTES.toMillis(1), new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
	}


	public static void zkClient() throws IOException{
		ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000);
	}
	public static void zkDubboClient() throws IOException{
		ZookeeperTransporter zookeeperTransporter = new ZkclientZookeeperTransporter();
		ZookeeperClient zookeeperClient = zookeeperTransporter.connect(URL.valueOf("zookeeper://127.0.0.1:2181?client=curator"));
		List<String> strings = zookeeperClient.addChildListener("/", new ChildListener() {
			public void childChanged(String path, List<String> children){
				System.out.println(path+":"+ children);
			}
		});
	}

	public static void curatorClient() throws IOException{
		ZookeeperTransporter zookeeperTransporter = new CuratorZookeeperTransporter();
		ZookeeperClient zookeeperClient = zookeeperTransporter.connect(URL.valueOf("zookeeper://127.0.0.1:2181?client=curator"));
		List<String> strings = zookeeperClient.addChildListener("/", new ChildListener() {
			public void childChanged(String path, List<String> children){
				System.out.println("==========="+path+":"+ children);
			}
		});
	}
	public static void main(String[] args) throws Exception {
		curatorClient();

		Thread.sleep(Integer.MAX_VALUE);
	}

}
