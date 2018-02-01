package cn.edu.njtech.manage.util;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/1
 * @description JqGrid
 */
public class JqGrid<T> {
	/**
	 * 总页数
	 */
	private Integer total;

	/**
	 * 当前页码
	 */
	private Integer page;

	/**
	 * 数据总量
	 */
	private Integer records;

	/**
	 * 行信息
	 */
	private List<T> rows;

	@Override
	public String toString() {
		return "JqGrid{" +
				"total=" + total +
				", page=" + page +
				", records=" + records +
				", rows=" + rows +
				'}';
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
