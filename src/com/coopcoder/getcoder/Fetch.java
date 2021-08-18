package com.coopcoder.getcoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Fetch {
	Config config;

	public Fetch(Config config) {
		this.config = config;
	}
	
	public static void getSingleTest()
	{
		String serviceString = "http://192.168.2.168/task/server/arws.php";
		Config config = new Config(serviceString, "seraagaldnialaldshgadl12312lasdfaaa");
		config.setUserAuthKey("kkkkkkkkkkkkkkkkkkkk");
		Fetch fetch = new Fetch(config);
		
		List<String> params = new ArrayList<>();
		params.add("helloaa");
		JsonObject jObject;
		try {
			jObject = fetch.getObject("server.ctl.main.Test", "t4", params);
			System.out.println(jObject.get("msg"));
			System.out.println(jObject.get("msg").toString().length());
			System.out.println(jObject);
			
			
			String jString = "{\"msg\": aa,\"abc\":[\"aaaa\",\"a2\"]}";
			
			JsonParser parser = new JsonParser();
			JsonObject jObject2 = parser.parse(jString).getAsJsonObject();
			
			System.out.println(jObject2.get("msg").getAsString().toString());
			System.out.println(jObject2.get("msg").getAsString().toString().length());
			System.out.println(jObject2.get("msg").getAsString().toString().equals("aa"));
		    String abc = "aa";
		    System.out.println(abc.toString());
		} catch (ServerResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getSingleTest();
		
//		try {
//			String serviceString = "http://120.24.7.188:8082";
////			String serviceString = "http://192.168.2.168:8082";
//			Config config = new Config(serviceString, "seraagaldnialaldshgadl12312lasdfaaa");
//			config.setUserAuthKey("kkkkkkkkkkkkkkkkkkkk");
//
//			Fetch fetch = new Fetch(config);
//			
//
//			
//			
//
//			// 服务名称
//			String apiName = "server.ctl.main.Test";
//			// 接口名称
//			String workerName = "t1";
//
//			// 请求参数
//			List<String> params = new ArrayList<>();
//			params.add("helloaa");
//
//			// 查看原始数据
//			String response = fetch.sendRequest(apiName, workerName, params);
//			System.out.println(response);
//
//			// 获取JSON
//			JsonArray jArray = fetch.getArray("server.ctl.main.Test", "t1", params);
//			System.out.println(jArray.toString());
//
//			// 获取int
////			Integer intVal = fetch.getInt("server.ctl.main.Test", "t2", params);
////			System.out.println(intVal);
//
//			// 获取bool
//			Boolean isbool = fetch.getBool("server.ctl.main.Test", "t3", params);
//			System.out.println(isbool);
//
//			// 获取jObject
//			JsonObject jObject = fetch.getObject("server.ctl.main.Test", "t4", params);
//			System.out.println(jObject);
//			
//			
//			List<String> params2 = new ArrayList<>();
//			params2.add("1");
//			params2.add("2");
//			params2.add("123456");
//			// 获取bool
//			JsonObject products = fetch.getObject("server.ctl.wallet.WalletCD", "buyProduct", params2);
//			System.out.println(products);
//
//			// 其他可用 getString getFloat getDouble
//
//		} catch (ServerResponseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public String getResponse(String ws) throws ServerResponseException {
//		String response = HttpRequest.post(config.SERVER_ADDRESS, true, "ws", ws).body();

		Map<String, String> data = new HashMap<String, String>();
		data.put("ws", ws);

		String response = HttpRequest.post(config.SERVER_ADDRESS).form(data).body();

		int tagIndex = response.indexOf(config.SERVER_RESPONSE_TAG);
		if (tagIndex < 0) {
			throw new ServerResponseException("SERVER CONNECTED ERROR:" + response);
		} else {
			response = response.substring(tagIndex + config.SERVER_RESPONSE_TAG.length());
		}
		response = Cipher.hexStr2Str(response);
		return response;
	}

	public String sendRequest(String api, String workerName, List<String> params) throws ServerResponseException {

		Map<String, String> authSign = new HashMap<String, String>();
		authSign.put("AUTH_SERVER_OPKEY", config.AUTH_SERVER_OPKEY);
		authSign.put("USER_AUTH_KEY", config.USER_AUTH_KEY);
		authSign.put("USER_ACCESS_TOKEN", config.USER_ACCESS_TOKEN);

		Map<String, Object> map = new HashMap();
		map.put("class", api);
		map.put("method", workerName);
		map.put("param", params);

		map.put("authSign", authSign);
		map.put("CLIENT_SERVER", "GETCODER-JAVA-SDK-20200821");

		Map<String, Object> dataMap = new HashMap();
		dataMap.put("type", "array");
		dataMap.put("data", map);

		Gson gson = new Gson();

		String sendJsonString = gson.toJson(dataMap).toString();

//		System.out.println("sendJsonString:" + sendJsonString);

		String ws = Cipher.str2HexStr(sendJsonString);

//        System.out.println("ws sendJsonString:" + ws);
		return getResponse(ws);

	}

	public JsonArray getJson(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("array")) {
				return jObject.get("data").getAsJsonArray();
			} else {
				throw new ServerResponseException("type check error, 'array' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}
	
	public JsonArray getArray(String api, String workerName, List<String> params) throws ServerResponseException {
		return getJson(api, workerName, params);
	}

	public JsonObject getObject(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("object")) {
				return jObject.get("data").getAsJsonObject();
			} else {
				throw new ServerResponseException("type check error, 'object' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}

	public Boolean getBool(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("bool")) {
				return jObject.get("data").getAsBoolean();
			} else {
				throw new ServerResponseException("type check error, 'bool' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}

	public Integer getInt(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("int")) {
				return jObject.get("data").getAsInt();
			} else {
				throw new ServerResponseException("type check error, 'int' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}

	public String getString(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("string")) {
				return jObject.get("data").getAsString();
			} else {
				throw new ServerResponseException("type check error, 'string' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}

	public Double getFloat(String api, String workerName, List<String> params) throws ServerResponseException {
		String response = sendRequest(api, workerName, params);

		JsonParser parser = new JsonParser();
		JsonObject jObject = parser.parse(response).getAsJsonObject();

		if (jObject.has("type")) {
			if (jObject.get("type").getAsString().equals("float")) {
				return jObject.get("data").getAsDouble();
			} else {
				throw new ServerResponseException("type check error, 'float' expected but "
						+ jObject.get("type").getAsString() + " provided," + response);
			}
		} else {
			throw new ServerResponseException("type error");
		}
	}

	public Double getDouble(String api, String workerName, List<String> params) throws ServerResponseException {
		return getFloat(api, workerName, params);
	}

}
