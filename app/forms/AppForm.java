package forms;

import java.util.List;

import play.data.validation.ValidationError;

public abstract class AppForm {
	/**
	 * validation処理
	 * @return エラー情報
	 */
	public abstract List<ValidationError> validate();
}
