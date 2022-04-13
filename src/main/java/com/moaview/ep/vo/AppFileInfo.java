package com.moaview.ep.vo;

import org.apache.commons.io.FilenameUtils;

public class AppFileInfo {
	private String fileGroup;

	private String fileContId;

	private String fileId;

	private String fileFieldName;

	private String fileName;

	private String orginFileName;

	private String fileDownloadUri;

	private String fileType;

	private String filePath;

	private String fileExt;

	private String userId;

	private long fileSize;

	public AppFileInfo(String fileGroup, String fileContId, String fileId, String fileFieldName, String fileName,
			String orginFileName, String fileDownloadUri, String fileType, long fileSize, String filePath) {
		this.fileGroup = fileGroup;
		this.fileContId = fileContId;
		this.fileId = fileId;
		this.fileFieldName = fileFieldName;
		this.fileName = fileName;
		this.orginFileName = orginFileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.fileExt = FilenameUtils.getExtension(fileName);
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return this.fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileGroup() {
		return this.fileGroup;
	}

	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}

	public String getFileContId() {
		return this.fileContId;
	}

	public void setFileContId(String fileContId) {
		this.fileContId = fileContId;
	}

	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileFieldName() {
		return this.fileFieldName;
	}

	public void setFileFieldName(String fileFieldName) {
		this.fileFieldName = fileFieldName;
	}

	public long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getOrginFileName() {
		return this.orginFileName;
	}

	public void setOrginFileName(String orginFileName) {
		this.orginFileName = orginFileName;
	}
}
