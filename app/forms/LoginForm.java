package forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Validatable;
import play.data.validation.Constraints.Validate;
import play.data.validation.ValidationError;

@Validate
public class LoginForm extends AppForm implements Validatable<List<ValidationError>> {
	/** メールアドレス */
	@Required
	@Email
	public String email;

	/** パスワード */
	@Required
	@MinLength(8)
	@MaxLength(25)
	@Pattern("^[0-9a-zA-Z!\"#$%&\'()*+,-./:;<=>?@\\[\\]\\^_`{}|~]*$")
	public String password;

	@Override
	public List<ValidationError> validate() {
		// TODO 自動生成されたメソッド・スタブ
		List<ValidationError> list = new ArrayList<>();
		/*
		 * 入力されたメールアドレスとパスワードを持つユーザが
		 * 存在するかをチェックする。
		 */
//		UserModel user = Ebean.find(UserModel.class).where().eq("email", this.email).findUnique();
//		if(user == null ? true : !BCrypt.checkpw(this.password, user.password)){
//			list.add(new ValidationError("email", "正しくありません"));
//			list.add(new ValidationError("password", "正しくありません"));
//		}
		return list;
	}

}
