package cn.edu.njtech.manage.constant;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description OperationConstant
 */
public class OperationConstant {

	/**
	 * 新增
	 */
	private Boolean add = false;

	/**
	 * 删除
	 */
	private Boolean del = false;

	/**
	 * 编辑
	 */
	private Boolean edit = false;

	/**
	 * 搜索
	 */
	private Boolean search = false;

	@Override
	public String toString() {
		return "OperationConstant{" +
				"add=" + add +
				", del=" + del +
				", edit=" + edit +
				", search=" + search +
				'}';
	}

	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Boolean getDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}
