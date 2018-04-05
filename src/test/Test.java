package test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit.SyncCond;
import com.peersafe.chainsql.util.Util;

public class Test {
	public static final Chainsql c = Chainsql.c;
	public static String sTableName,sTableName2,sReName;
	public static String sNewAccountId,sNewSecret;
	public static void main(String[] args) {

//		c.connect("ws://101.201.40.124:5006");

//		c.connect("ws://192.168.0.171:6006");
		c.connect("ws://wsapi.chainsql.net");

//		c.connect("ws://101.201.40.124:5006");

		//c.connect("wss://192.168.0.194:5005", "server.jks", "changeit");
		
		sTableName = "hello333";
		sTableName2 = "boy2";
		sReName = "boy1";

		//设置日志级别
		c.connection.client.logger.setLevel(Level.SEVERE);
				
//		c.as("zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh", "xnoPBzXtMeMyMHUVTgbuqAfg1SUTb");

		//阿里云
//		c.as("zHyz3V6V3DZ2fYdb6AUc5WV4VKZP1pAEs9", "xxprJNCURq7J9BVpeox38CGq2nwLM");



 		//testSubscribe();
		testRippleAPI();
//
//		 testAccount();
//   	 testChainSql();

	//	c.disconnect();
	}

	private static void testRippleAPI() {
		Test test = new Test();

//		test.testValidationCreate(); // 生成验证节点的账号
//		test.getLedgerVersion(); // 区块高度
//		test.getLedger(); // 区块信息
//
//		test.getUnlList();//链接的验证节点信息
//
//		test.getTransactions();// 账号20个最新交易
//		test.getTransaction();//  交易详情
//
//		test.getServerInfo();// 获取

//		test.getCrossChainTxs();// 跨链
//		test.getChainInfo();
	}

	private static void testAccount() {
		Test test = new Test();
//		test.generateAccount();
//		test.activateAccount(sNewAccountId);
		
		test.activateAccount("rsadxc3pw976e3hfaxUijhbR3ye2orJS6x");
//		test.activateAccount("rU3S9xL7xdMcQPgB5oir2dzDp5s19eTJ7f");
//		test.activateAccount("rEE12BEg5D3fp9ZazVMGayCQWd7hCjfgh6");
//		test.activateAccount("rsrc4M7tS5ur1UJ7kEVWSvoStzCLB2HNv6");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//  激活账户
	public boolean activateAccount(JSONObject account) {
		c.as("rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh", "snoPBrXtMeMyMHUVTgbuqAfg1SUTb");
		JSONObject ret = c.pay(account.getString("account_id"), "20");
		if (!ret.getString("status").equals("success")) {
			System.out.println(ret.toString());
			return false;
		}
		return true;
	}

	// 生成验证节点的公私钥
	public void testValidationCreate() {
		JSONArray ret = c.validationCreate(1);
		System.out.println(ret);
	}


    // 查询ledger
	public void getLedger() {
		c.getLedger(77, (data) -> {
			System.out.println("getLedger------" + data);
		});
	}

	//获取链接的所有验证节点信息
	public void getUnlList(){
		System.out.println("UnlList:" + c.getUnlList());
	}

	// 获取区块高度 
	public void getLedgerVersion() {
		c.getLedgerVersion((data) -> {
			System.out.println("getLedgerVersion------" + data);
		});
	}

	// 获取指定账户下的交易,默认返回20条交易
	public void getTransactions() {
		System.out.println("- getTransactions - " );
		JSONObject obj = c.getTransactions("zcUDp5cRWQLn6vzi8c8aZ9LvY1fBeNQevp");

  		System.out.println("getTransactions------" + obj);
	}

	//查看交易详情
	public void getTransaction() {
		String hash = "620522E66160D54563B433F4FDDAE64C596C66B8736C07F1090B2C82960EBA3E";
		JSONObject obj = c.getTransaction(hash);
		System.out.println("getTransaction------" + obj);
		// c.getTransaction(hash, (data)->{
		// System.out.println(data);
		// });

	}

    // 跨链
	public void getCrossChainTxs() {
		long tm1 = System.currentTimeMillis();
		JSONObject obj = c.getCrossChainTxs("", 10, true);
		System.out.println(obj);
		System.out.println(System.currentTimeMillis() - tm1);

	}

	// 重建表
	public void testRecreateTable() {
		c.recreateTable("abcde").submit((data) -> {
			System.out.println(data);
		});
	}

	public void getChainInfo() {
		JSONObject ret = c.getChainInfo();
		System.out.println(ret);
	}

	// 获取serverInfo
	public void getServerInfo() {
		JSONObject ret = c.getServerInfo();
		System.out.println(ret);
	}

	// 生成账户
	public void generateAccount() {
		JSONObject obj = c.generateAddress();
		System.out.println(obj);
		sNewAccountId = obj.getString("account_id");
		sNewSecret = obj.getString("secret");
	}

	// 激活账户
	public void activateAccount(String account) {
		JSONObject ret = c.pay(account, "200");
		System.out.println("pay result:" + ret);
	}


	public ArrayList<String> select(String tableName, String filterWith, String whereCond) {
		ArrayList<String> cond = new ArrayList<String>();
		cond.add(whereCond);
		JSONObject json = c.table(tableName).withFields(filterWith).get(cond).submit((data) -> {
			System.out.println("creat------" + data);
		});
		if (json == null) {
			System.out.println("鏌ヨ缁撴灉闆嗕负绌�");
			return null;
		}
		System.out.println("json:" + json.toString());
		// TODO 鍥炶皟鏈哄埗鏈夊緟琛ュ厖
		return null;
	}

}
