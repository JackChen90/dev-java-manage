package cn.edu.njtech.manage.util;

import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import org.springframework.stereotype.Component;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description GridUtil
 */
@Component
public class GridUtil {

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	public HandleResult judgeRequest(GridDataDTO dto) {
		HandleResult result;
		if (null == dto) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "params empty");
			return result;
		}
		//校验page
		if (null == dto.getPage()) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "page is null");
			return result;
		}
		//校验rows
		if (null == dto.getRows()) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "rows is null");
			return result;
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}
}
