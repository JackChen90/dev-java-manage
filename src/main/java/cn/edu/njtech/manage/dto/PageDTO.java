package cn.edu.njtech.manage.dto;

/**
 * @author jackie chen
 * @create 2018/1/31
 * @description PageDTO
 */
public class PageDTO {

	/**
	 * 总数
	 */
	private Integer recordsCount;

	/**
	 * 当前页码
	 */
	private Integer pageNum;

	/**
	 * 每页数据量
	 */
	private Integer pageSize;

	public Integer getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(Integer recordsCount) {
		this.recordsCount = recordsCount;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PageDTO{" +
				"recordsCount=" + recordsCount +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				'}';
	}
}
