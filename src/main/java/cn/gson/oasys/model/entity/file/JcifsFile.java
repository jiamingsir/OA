package cn.gson.oasys.model.entity.file;

import java.util.Date;


public class JcifsFile {

	private Long id;
	private String fileName;	//文件名字
	private String filePath;	//文件路径
	private Long size;	//文件大小
	private String contentType;	//文件类型id
	private Long uploadTime;	//上传时间
	private String model;		//所属模块
	private String fileShuffix;	//文件后缀名
	private Long fileIstrash = 0L;
	private Long fileIsshare = 0L;



	public JcifsFile() {
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFileShuffix() {
		return fileShuffix;
	}

	public void setFileShuffix(String fileShuffix) {
		this.fileShuffix = fileShuffix;
	}

	public Long getFileIstrash() {
		return fileIstrash;
	}

	public void setFileIstrash(Long fileIstrash) {
		this.fileIstrash = fileIstrash;
	}

	public Long getFileIsshare() {
		return fileIsshare;
	}

	public void setFileIsshare(Long fileIsshare) {
		this.fileIsshare = fileIsshare;
	}
}
