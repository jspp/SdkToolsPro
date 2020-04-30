package com.jf.game.subPackage;

/**
 * 分包结果信息
 * @author sun
 * TODO
 * Jul 18, 2017
 */
public class SubResult {
    /**
     * 结果状态吗   1=成功 其他失败
     */
	private int code;
	/**
	 * 错误描述
	 */
	private String desc;
    /**
     * 下载链接
     */
	private String downloadUrl;
	
	
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public SubResult(int code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

	public SubResult() {
		super();
	}
	
}
