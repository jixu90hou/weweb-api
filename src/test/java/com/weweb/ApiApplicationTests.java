package com.weweb;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.weweb.common.util.HttpUtils;
import com.weweb.common.util.JsonUtils;
import com.weweb.po.entity.PoOrderRequest;
import com.weweb.po.mapper.PoOrderRequestDao;
import com.weweb.po.service.PoVendorService;
import com.weweb.sys.service.SysUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private PoVendorService poVendorService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PoOrderRequestDao poOrderRequestDao;

	@Test
	public void contextLoads() {
		String json = JsonUtils.toString(sysUserService.getSysUserByUserId(985L));
		System.out.println("json:" + json);
		PoOrderRequest orderRequest = poOrderRequestDao.getOrderRequest(346l);
		String json1 = JsonUtils.toString(orderRequest);
		System.out.println("json1:" + json1);
	}

	@Test
	public void uploadFiles() throws Exception {
		String fileUrl = "D://test/email_log.png";
		// String file2Url = "D://test/guide1.png";

		String app_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYWlfaHVhbmh1aUAxMjYuY29tIn0.rpoWTDecYlb00UVjm7Petk4ExPs5NNFzEdsXFA41knVwGKrkolH-ASzDfLUJ0YtPMcVbSkEby4mHCCLKeLDZDw";
		app_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9.O24tv6a76BA39D6bdpYnuUBE5EMTCbkk4OYu143aCWiLIdv9JOOQ_iQ70SZ2LXkRdfnPrjLlHJgMMcmwQPJwRQ";
		app_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9.ga0gIr4ZHC2AVu96PXiyYwRnstxCuPVWLBmUdrSJ2hdVZFTo4ZEsWPdvF2B15IsE_wdzWCWG167i8_E0qhk6tQ";
		app_token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3c2hlbiJ9.aUkReYj18prjWpZSHgDaQaTf4YimefY3GM3jRDNoTIRpmo85-CGtZvgtVEZAxDQClAvNRSzBl6NXjCSF8noABQ";
		// http://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
		InputStream inputStream = new FileInputStream(new File(fileUrl));
		// InputStream inputStream2 = new FileInputStream(new File(file2Url));
		MockMultipartFile firstFile = new MockMultipartFile("wang", fileUrl, "application/octet-stream", inputStream);
		// MockMultipartFile secondFile = new MockMultipartFile("wang22",
		// file2Url, "application/octet-stream", inputStream2);

		// MockMultipartFile secondFile = new MockMultipartFile("zhang2",
		// file2Url, null, FileUtil.File2byte(file2Url));
		String url = "/po/api/sendItemImage";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.fileUpload(url).file(firstFile)
						// .file(secondFile)
						.header("app_token", app_token).param("some-random", "4").param("recordId", "17"))
				.andExpect(status().isOk()).andReturn();

		String jsonData = mvcResult.getResponse().getContentAsString();
		System.err.println(jsonData);
	}


	@Test
	public void uploadRemoteFiles() throws Exception {
		String fileUrl = "D://test/email_log.png";
		String file2Url = "D://test/aaa.jpeg";
		String file3Url = "D://test/product1.png";

		String app_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYWlfaHVhbmh1aUAxMjYuY29tIn0.rpoWTDecYlb00UVjm7Petk4ExPs5NNFzEdsXFA41knVwGKrkolH-ASzDfLUJ0YtPMcVbSkEby4mHCCLKeLDZDw";
		app_token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3c2hlbiJ9.0QT4hmHgxA70Wq2sVhaWnXgsVjZWIxPOttq6yruPNOKuwYJk1pNUHHwUKevJkeNM7jTEASIXANhwxSToFgFQ8Q";

				// http://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
		// InputStream inputStream2 = new FileInputStream(new File(file2Url));
		// MockMultipartFile secondFile = new MockMultipartFile("zhang2",
		// file2Url, null, FileUtil.File2byte(file2Url));
		String url = "http://webdev05.sstparts.com:4001/po/api/sendItemImage";
		InputStream inputStream2 = new FileInputStream(new File(fileUrl));
		//url = "http://10.3.1.183:4001/po/api/sendItemImage";
		Map<String, String> headers = new HashMap<>();
		headers.put("app_token", app_token);
		Map<String, String> params = new HashMap<>();
		params.put("recordId", "590");
		Map<String,File> files=new HashMap<>();
		files.put("p1",new File(fileUrl));
		files.put("p2",new File(file3Url));
		String json=HttpUtils.uploadRemoteFile(url, headers, params, files, null);
		System.out.println("json:"+json);
	}
}
