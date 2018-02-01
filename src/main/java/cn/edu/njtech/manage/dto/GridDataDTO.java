package cn.edu.njtech.manage.dto;

/**
 * @author jackie chen
 * @create 2018/2/1
 * @description GridDataDTO jqGrid url中所带参数
 */
public class GridDataDTO {
	//filters=&searchField=userName&searchString=jackie&searchOper=eq
	/**
	 *
	 */
	private String _search;

	/**
	 * 时间戳
	 */
	private Long nd;

	/**
	 * 每页数据量
	 */
	private Integer rows;

	/**
	 * 当前页码，从1开始
	 */
	private Integer page;

	/**
	 *
	 */
	private String sidx;

	/**
	 * asc/desc
	 */
	private String sord;

	/**
	 *
	 */
	private String filters;

	/**
	 * 搜索列
	 */
	private String searchField;

	/**
	 * 搜索输入内容
	 */
	private String searchString;

	/**
	 * 搜索操作符
	 */
	private String searchOper;

	public String get_search() {
		return _search;
	}

	public void set_search(String _search) {
		this._search = _search;
	}

	public Long getNd() {
		return nd;
	}

	public void setNd(Long nd) {
		this.nd = nd;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	@Override
	public String toString() {
		return "GridDataDTO{" +
				"_search=" + _search +
				", nd=" + nd +
				", rows=" + rows +
				", page=" + page +
				", sidx='" + sidx + '\'' +
				", sord='" + sord + '\'' +
				", filters='" + filters + '\'' +
				", searchField='" + searchField + '\'' +
				", searchString='" + searchString + '\'' +
				", searchOper='" + searchOper + '\'' +
				'}';
	}
}
