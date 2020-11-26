package com.pengjv.gmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class MallManageWebApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	public void uploadfile() throws IOException, MyException {

		String file = this.getClass().getResource("/tracker.conf").getFile();
		ClientGlobal.init(file);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageClient storageClient = new StorageClient(trackerServer,null);
		String[] jpgs = storageClient.upload_file("E:\\img\\p1.jpg", "jpg", null);
		for (int i = 0; i < jpgs.length; i++) {
			String jpg = jpgs[i];
			System.out.println(jpg);
		}
	}

}
