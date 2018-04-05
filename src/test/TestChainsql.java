package test;

import java.util.List;
import java.util.Arrays;
import java.util.logging.Level;
import java.lang.String;

import BaseFunction.CommonFunction;
import BaseFunction.TxTData;
import com.peersafe.base.core.coretypes.AccountID;
import com.peersafe.chainsql.crypto.Aes;
import org.json.JSONObject;
import org.json.JSONArray ;

import java.util.Timer;
import java.util.TimerTask;

import com.peersafe.chainsql.core.Chainsql;
import com.peersafe.chainsql.core.Submit.SyncCond;
import com.peersafe.chainsql.util.Util;
import com.peersafe.base.client.requests.*;
import com.peersafe.base.client.responses.*;
import org.omg.CORBA.Object;

public class TestChainsql {
	public static final Chainsql c = Chainsql.c;
	public static String sTableName, sTableName2, sReName;
	public static String sNewAccountId, sNewSecret;

	public  static  Boolean isUCloud = false; //是否切换到阿里云环境

 	public static String rootAddress = "zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh";
 	public static String rootSecret = "xnoPBzXtMeMyMHUVTgbuqAfg1SUTb";

	// 阿里云上的根账户
	public static String rootAddress2 = "zHyz3V6V3DZ2fYdb6AUc5WV4VKZP1pAEs9";
	public static String rootSecret2 = "xxprJNCURq7J9BVpeox38CGq2nwLM";


//	public static String rootAddress = "zUVhbXTCkt2TYw7EAGoGWNdK1Cusc1uLgg";
//	public static String rootSecret = "xnrbWYMGXAnAPJmFzhSPHLaCUhzSM";


	public  static  Boolean isThread = false;

	public static void main(String[] args) {

		// 阿里云地址
 		if (isUCloud){
			c.connect("ws://101.201.40.124:5006");
			c.as(rootAddress2, rootSecret2);
			c.use("zHyz3V6V3DZ2fYdb6AUc5WV4VKZP1pAEs9");
		}else {
//			c.connect("ws://192.168.0.183:6001");
			c.connect("ws://10.100.0.163:6006");
 //			c.connect("ws://192.168.0.152:6006",(client)->{
//				System.out.println("*******   connect success  *******");
//			});
			c.as(rootAddress, rootSecret); // 操作者
			c.use("zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh");// 拥有者

//			c.as("zpZ7c81S1zH6jnUkYNYeQ6pqfJFwTGLg47","xnsTpUxbLjF9DyYAr14YQLkoABw94");
//			c.use("zpZ7c81S1zH6jnUkYNYeQ6pqfJFwTGLg47");
		}

		//设置chainsql日志级别
		c.connection.client.logger.setLevel(Level.SEVERE);

 		testChainSql();

//		c.disconnect();
	}

	private static void testChainSql() {
		TestChainsql test = new TestChainsql();

//		test.getAccountBalance("zHyz3V6V3DZ2fYdb6AUc5WV4VKZP1pAEs9");

        // 账号
//        testAccount();

		sTableName = "peer_2";
		sNewAccountId = "zwxWcoM8P8iTAm61tnpMzuDa8Tro67wztg";  //"zzzzzzzzzzzzzzzzzzzzBZbvji";

//		for (int i =0; i<500;i++){
//			sTableName = "peersaef_"+i;
//			System.out.println("sTableName------"+sTableName);
			test.testCreateTable(false);
//		}

//		createAccount();

//		test.testdrop(sTableName);

//		test.getAccountBalance(rootAddress);

//		插入数据
//      for (int i=0;i<500;i++) {
//	     test.testinsert();
//	     System.out.println("insert------"+i);


		//授权
//		test.grant(sTableName, sNewAccountId);

//		更新表数据
//		test.testUpdateTable();


		//删除表数据
//		 test.testdelete();

		//重命名表
//		test.testrename(sTableName, sTableName);

//		查询表数据
//		test.testget();

		//删除表
//		test.testdrop(sTableName);

		// 订阅表
//		test.testSubscribe();

		// 事务
//		test.testts();
//
		// 取消订阅表
//		test.testUnSubscribe();

		// 订阅交易
		test.testSubscribeTX();

		// 行级控制
// 		test.testOperationRule();

  		// 授权
		test.grant(sTableName,sNewAccountId);

		// 部分字段加密
//		testEncrypt();

		// 表的重建
//		test.testRecreateTable();

		// AES算法
//		testAES();

	}

	private  static  void  testAES(){
		String password = "123456789qwertyuiopasdfghjk";
		String content = "";

		String encryptStr =	Aes.aesEncrypt(password,content);
		System.out.println("加密 = "+encryptStr);

		byte[] bytes = Aes.aesDecrypt(password,encryptStr);
		System.out.println("解密 = "+ new String(bytes));

	}

	private  static  void createAccount(){
		TestChainsql test = new TestChainsql();
		TxTData txt = new TxTData();
// 		List<String> allData = txt.readTxtData("account");

		for (int i=0;i<1000;i++) {
//			String accountData = allData.get(i);
//			String[] accountAll = accountData.split(",");
//			System.out.println("accountAll:" + accountAll +"account:"+ accountAll[0]);
//			test.activateAccount(accountAll[0]);

			JSONObject obj = c.generateAddress();
			System.out.println("new account:" + obj);
			sNewAccountId = obj.getString("address");
			sNewSecret = obj.getString("secret");

//			test.activateAccount(sNewAccountId);

//			String tableName= "BB_"+i;
//			String accountInfo = sNewAccountId+ ","+sNewSecret+ ","+tableName;

			String accountInfo = sNewAccountId+ ","+sNewSecret;
			txt.writeToFile("account", accountInfo);
		}
	}


	// 生成账户并激活
	private static void testAccount() {
		TestChainsql test = new TestChainsql();

		//生成新账户
		test.generateAccount();
//		给新账户打钱
		test.activateAccount(sNewAccountId);

		//查询根账户余额
//		test.getAccountBalance(sNewAccountId);

//		test.activateAccount("zJ5xJfgHWCcok8EaykYerFSaCVsoD4Le2c");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 生成账户
	public void generateAccount() {
		JSONObject obj = c.generateAddress();
		System.out.println("new account:" + obj);
		sNewAccountId = obj.getString("address");
		sNewSecret = obj.getString("secret");
	}

	// 激活账户
	public void activateAccount(String account) {
		//RPC是drop   java和node.js api是zxc
		JSONObject ret = c.pay(account, "10");
		System.out.println("pay result:" + ret);
    }

	//查询根账户余额
	public void getAccountBalance(String address) {
		String balance = c.getAccountBalance(address);
		System.out.println("AccountBalanc=="+balance);
	}

	// 生成表 isconfidtiong: true 加密表,false 不加密表
	public void testCreateTable(Boolean isconfidtiong) {

//		"{'field':'id','type':'int','length':11,'PK':1,'AI':1}"
 		List<String> args = Util.array("{'field':'id','type':'int','length':11,'PK':1,'AI':1}","{'field':'name','type':'text'}","{'field':'age','type':'int'}"
				 );
  		JSONObject obj;
		obj = c.createTable(sTableName, args, isconfidtiong).submit(SyncCond.db_success);
		System.out.println("create result:" + obj);
	}

	// 插入数据
	public void testinsert() {
		List<String> orgs = Util.array("{'name':'33','age':10}","{'name':'33','age':10}","{'name':'33','age':10}");
		JSONObject obj;
		obj = c.table(sTableName).insert(orgs).submit(SyncCond.db_success);
		System.out.println("insert result:" + obj);
	}

   //更新表数据
	public void testUpdateTable() {
		//"{'$or':[{'id':8} ,{'$and':[{'id':1},{'age':890}]}] }"

//		String  conStr = "{'$or':[{'id':8} ,{'$and':[{'id':1},{'age':890}]}] }";
		// "{'$and':[{'id':{'$gt':3}}, {'$or':[{'name':'name_888'},{'name':'name_2'}]}]}"

//		List<String> arr1 = Util.array("{'id':3}","{'id':4}");
//		{'$and': [{'id':3},{'name':'excel_id'}]}

		JSONObject obj;
		obj = c.table(sTableName).get("{'id':3}").update("{'name':'peersafe_80'}").submit(SyncCond.db_success);
		System.out.println("update result:" + obj);
	}

	//删除表数据
	public void testdelete() {
		List<String> arr1 = Util.array("{'id':110}","{'id':3}","{'id':4}");

    	JSONObject obj;
		obj = c.table(sTableName).get("{'id':2}").delete().submit(SyncCond.db_success);
		System.out.println("delete result:" + obj);
	}

	// 重命名
	public void testrename(String oldTableName, String newTableName) {
		JSONObject obj;
		obj = c.renameTable(oldTableName, newTableName).submit(SyncCond.db_success);
		System.out.println("rename result:" + obj);
	}

	// 查询表数据
	public void testget() {
		//查询单条数据
//		 JSONObject obj = c.table(sTableName).get("").withFields("[]").submit();
		//"{'name':{'$regex':'/^a/'}}"

//		JSONObject obj= c.table(sTableName).get("{'name':'bb'}").withFields("['COUNT(*)']").submit();

		//查询所有数据
//	    List<String> args = Util.array("{'$group':['name']}","{'$having':{'SUM(age)':{'$lt':10}}}");

//		JSONObject obj = c.table(sTableName).get(args).withFields("['id','name','SUM(age)']").order(Util.array("{'id':-1}")).submit();
//		JSONObject obj = c.table(sTableName).get("").withFields("").submit();

//		List<String> args = Util.array("{'$group':['name']}","{'$order':['id':-1]}");

//		List<String> args = Util.array("{'$group':['name']}","{'$having':{'age':{'$ge':5}}}");

//		JSONObject obj = c.table(sTableName).get("{'name':{'$regex':'/e/'}}").withFields("['id','name']").order(Util.array("{'id':-1}","{'name':-1}")).submit();

//		JSONObject obj = c.table(sTableName).get("{'$group':['id']}").withFields("['id','name','SUM(age)']").order(Util.array("{'id':1}")).submit();

//		JSONObject obj= c.table(sTableName).get("{'$group':['name','phone']}").withFields("['id','name','SUM(age)']").submit();

//		JSONObject obj= c.table(sTableName).get(args).withFields("['id','name','SUM(age) as age']").order(Util.array("{'id':1}")).submit();

//		List<String> arr1 = Util.array("{'id':2}","{'id':3}","{'id':4}");

		JSONObject obj= c.table(sTableName).get("").withFields("").limit("{'index':0,'total':1}").submit();
		System.out.println("get result:" + obj);

	}

	// 用户授权
	public void grant(String tableName, String newAccount) {
		JSONObject obj = new JSONObject();
//		String pubKey = "cBRXPrQzvJRvu3iBqRBDy5dfdYcBVadKGMj25Rcg5qZfJHn3pMez";
////		 加密表授权要加 publicKey
//		obj = c.grant(tableName,newAccount,pubKey,"{select:true,insert:true,update:true,delete:true}").submit(SyncCond.db_success);

		Boolean isCanCel = false;

		String granting = isCanCel ? "{select:false,insert:false,update:false,delete:false}" :"{select:true,insert:true,update:true,delete:true}";

		// 不加密表授权
    	obj = c.grant(tableName, newAccount, granting)
				.submit(SyncCond.validate_success);
		System.out.println("grant result:" + obj.toString());
    }

	// 删除表
	public void testdrop(String reName) {
		JSONObject obj;
		obj = c.dropTable(reName).submit(SyncCond.db_success);
		System.out.println("drop result:" + obj);
	}

	// 创建行级控制
	public void testOperationRule() {
		sTableName = "rule_30";
		List<String> args = Util.array("{'field':'id','type':'int','length':11,'PK':1,'NN':1,'UQ':1,'AI':1}",
				"{'field':'name','type':'varchar','length':50,'default':null}",
				"{'field':'age','type':'int'}",
				"{'field':'account','type':'varchar','length':64}",
				"{'field':'txid','type':'varchar','length':64}");

		String operationRule = "{" +
				"'Insert':{" +
			    " 'Condition':{'account':'$account','txid':'$tx_hash'}," +
 				" 'Count':{'AccountField':'account','CountLimit':5}" +
				"}," +
				"'Update':{" +
				" 'Condition':{'id':{'$ge':2}}," +
				" 'Fields':['age']" +
				"}," +
				"'Delete':{" +
				"	'Condition':{'account':'$account'}" +
				"}," +
				"'Get':{" +
 				"	'Condition':{'id':{'$ge':2}}" +
				"}" +
				"}";

		JSONObject obj;
//		obj = c.createTable(sTableName, args, Util.StrToJson(operationRule)).submit(SyncCond.db_success);
//		System.out.println("create result:" + obj);
//
		List<String> orgs1 =Util.array("{'id':2,'name':'aa'}"  );
		obj = c.table(sTableName).insert(orgs1).submit(SyncCond.db_success);
		System.out.println("insert result:" + obj);

//		obj = c.table(sTableName).get("{'id':4}").update("{'name':'name_44'}").submit(SyncCond.db_success);
//		System.out.println("update result:" + obj);

//		obj = c.table(sTableName).get().delete().submit(SyncCond.db_success);
//		System.out.println("delete result:"+obj);
//
//		obj = c.table(sTableName).get().withFields("").submit();
//		System.out.println("get result:"+obj);

//		obj = c.table(sTableName).get().limit("{index:0,total:2}").withFields("[]").submit();

//		obj = c.table(sTableName).get().withFields("[]").submit();
		System.out.println("get result:" + obj);
  }

	// 订阅表
	private  void testSubscribe() {
		System.out.println("---testSubscribe---");

 		String tableName = sTableName;
		c.event.subscribeTable(tableName,"zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh", (data) -> {
			System.out.println("subTable="+tableName+data);

		});

	  c.onReconnecting((data) -> {
		 System.out.println("Reconnecting started");
	  });
	  c.onReconnected((data) -> {
			System.out.println("Reconnected");
	  });
}

	// 取消订阅
	private   void  testUnSubscribe(){
        System.out.println(sTableName);
        // 延迟2分钟执行取消订阅操作
        Timer  timer = new Timer(true);
        timer.schedule(new java.util.TimerTask() {
            public void run()
                {
					System.out.println("---testUnSubscribe---");
                    c.event.unsubscribeTable(sTableName,"zHb9CJAWyB4zj91VRWn96DkukG4bwdtyTh",(data)->{
                        System.out.println("unsubTable----"+data);
                });
                }
            }, 2*60*1000);
   }


	// 订阅交易
	private  void testSubscribeTX(){

		System.out.println("++testSubscribeTX ++");

		// 签名交易
		JSONObject obj =  TestSign.testSignPayment();
		String txHash = obj.getString("hash");
        String tx_blob = obj.getString("tx_blob");

		// 订阅交易
		c.event.subscribeTx(txHash,(data) -> {
			System.out.println("subscribeTX----"+data);
			// 取消订阅交易
			c.event.unsubscribeTx(txHash,(data2) -> {
				System.out.println("unsubscribeTX----"+data2);
			});
		});

		// 提交交易
		Request req = c.connection.client.submit(txHash,true);
 		req.json("tx_blob", tx_blob);
		req.request();

		req.once(Request.OnSuccess.class, new Request.OnSuccess() {
			public void called(Response response) {
				System.out.println("success response:" + response.message.toString());
			}
		});

		req.once(Request.OnError.class, new Request.OnError() {
			@Override
			public void called(Response response) {
				System.out.println("error response:" + response.message.toString());
			}
		});

	}

	// 事务交易
    public void testts() {
//		sTableName = "hao_01";
  		c.beginTran();
  		c.setNeedVerify(true);
		c.setRestrict(true);

		List<String> args =
				Util.array("{'field':'id','type':int,'length':111,'PK':1,'AI':1}",
						"{'field':'name','type':'varchar','length':50,'default':'abc'}",
						"{'field':'age','type':'int'}"
				);

//  	c.createTable(sTableName,args,false);

//		c.table(sTableName).insert(Util.array("{'id':500,'name':'a1'}","{'id':501,'name':'a2'}"));
//
//		List<String> gg = Util.array("{'id':5}","{'id':9}");
//		c.table(sTableName).get("{'id':400}").update("{'name':'400'}");
//
//		c.table(sTableName).get("{'id':10}").delete();

		List<String> parm1 = Util.array("{'$IsExisted':1}");
		c.table(sTableName).sqlAssert(parm1);
//
//		List<String>parm2 = Util.array("{'$RowCount':0}","{'id':'500'}");
//		c.table(sTableName).sqlAssert(parm2);

		JSONObject obj = c.commit(SyncCond.db_success);
		System.out.println("transaction  result:" + obj);
	}

	// 部分字段加密
    private static void testEncrypt() {
		String tableName = "encrypt_119";
  //	建表，注意name字段定义成text 类型，以防止varchar长度不够大插入失败
		List<String> args = Util.array("{'field':'id','type':'int','length':11,'PK':1,'NN':1,'UQ':1,'AI':1}",
				"{'field':'name','type':'text'}", "{'field':'age','type':'int'}","{'field':'info','type':'text'}");
		JSONObject obj;
		obj = c.createTable(tableName,args,false).submit(SyncCond.db_success);
		System.out.println("create result:" + obj);

   //给字段加密
		String cip = "name_11111"; //16
		List<String> listPub = Arrays.asList("cBMyLgN4JsXVybpCRRtLZWCzoHitSj2kMZW2yo4CZAL7HsZ672hv");
		String cipher1 =  c.encrypt(cip, listPub) ;
		System.out.println("cipher1 encrypt result:" + cipher1);

		String cip2 = "info_2222"; //16
		List<String> listPub2 = Arrays.asList("cBMyLgN4JsXVybpCRRtLZWCzoHitSj2kMZW2yo4CZAL7HsZ672hv");
		String cipher2 =  c.encrypt(cip2, listPub) ;
		System.out.println("cipher2 encrypt result:" + cipher2);



//		//插入数据
		args = Util.array("{'id':1,'age': 20,'name':'" + cipher1 + "','info':'" + cipher2 + "'}");//"{'id':1,'age': 20,'name':'" + cipher + "'}"
 		obj = c.table(tableName).insert(args).submit(SyncCond.db_success);
		System.out.println("insert result:" + obj);

		//查询刚刚插入的数据
		obj = c.table(tableName).get("{id:1}").submit();
		System.out.println(obj);

// 		//获取加密字段的值
		JSONArray lines = obj.getJSONArray("lines");
		JSONObject line = lines.getJSONObject(0);
		String nameCipher = line.getString("name");
		System.out.println("get cipher name="+ nameCipher);

		String nameCipher2 = line.getString("info");
		System.out.println("get cipher info="+ nameCipher);

// 		//解密加密字段的值
		String plainGet = c.decrypt(nameCipher,"xhxjp4snqU6L78uY2gMCFgZBpehHm" );
		System.out.println("账户1解密结果:name--" + plainGet);

		String plainGet2 = c.decrypt(nameCipher2,"xhxjp4snqU6L78uY2gMCFgZBpehHm" );
		System.out.println("账户1解密结果:info--" + plainGet2);

//	    String	plainGet = c.decrypt(nameCipher, "xnZmmiotxfmckQZ6at3rALKbSmRcq");
//		System.out.println("账户2解密结果:" + plainGet);

	}

	// 重建表
	public void testRecreateTable() {
		System.out.println("---testRecreateTable-----");
		c.recreateTable(sTableName).submit((data) -> {
			System.out.println("***"+data+"**");
		});
	}

}

