package com.jf.game.subPackage;

/**
 *  分包基础信息
 *  注意：
 *    各种路劲和名词不能带空格
 * @author sun
 * TODO
 * Jul 18, 2017
 */
public class SdkInfo {
    /**
     * 游戏名称首字母 比如:王者荣耀    wzry
     */
	private String gameCode;
	/**
	 * 游戏ID
	 */
	private String gameId;
	/**
	 * 渠道编码  数字或字母   比如：  21512
	 */
	private String agentCode;
    /**
     * 默认 Android
     */
	private int    clientType=1;
	/**
	 * 当前app 的版本
	 */
	private String apiVersion="v1";
	/**
	 * 是否上传到云上面 ，默认上传
	 */
	private boolean upToCloudFlag = true;
	/**
	 * 是的哪个云,默认绝峰
	 */
	private String cloudFlag = "jfyouxi";
	/**
	 * CP ID ，用以区分是哪个CP 是否使用新的分包方式
	 */
	private  Long cpMemId;



    /**
     * 获取写入子包的数据
     * 默认写入 客户端 key
     * @return
     * @author sun
     * Jul 18, 2017
     */
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
     * 创建写入APP info
     * 规则描述：
     *  游戏名ID_渠道ID_接口版本
     * @author sun
     * Jul 19, 2017
     */
	public String getAppInfo() {
		return gameId+"_"+agentCode+"_"+apiVersion;
	}

	public boolean isUpToCloudFlag() {
		return upToCloudFlag;
	}

	public void setUpToCloudFlag(boolean upToCloudFlag) {
		this.upToCloudFlag = upToCloudFlag;
	}

	public String getCloudFlag() {
		return cloudFlag;
	}

	public void setCloudFlag(String cloudFlag) {
		this.cloudFlag = cloudFlag;
	}

	public Long getCpMemId() {
		return cpMemId;
	}

	public void setCpMemId(Long cpMemId) {
		this.cpMemId = cpMemId;
	}
}
