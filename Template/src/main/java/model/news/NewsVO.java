package model.news;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class NewsVO {
	public interface IAddNews {
	}

	public interface IModifyNews {
	}

	@NotNull(message = "ID不能为空", groups = { IModifyNews.class })
	private Integer id;
	@NotNull(message = "标题不能为空", groups = { IAddNews.class, IModifyNews.class })
	private String title;
	private String publisher;
	@NotNull(message = "内容不能为空", groups = { IAddNews.class, IModifyNews.class })
	private String content;
	private String source;
	private String imageUrl;
	private String summary;
	private Date createTime;
	private Date modifyTime;
	@NotNull(message = "状态不能为空", groups = { IAddNews.class, IModifyNews.class })
	private String status;
	@NotNull(message = "新闻类型不能为空", groups = { IAddNews.class, IModifyNews.class })
	private int newsTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(int newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

}
