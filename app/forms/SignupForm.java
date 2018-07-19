package forms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import io.ebean.Ebean;
import models.User;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Validatable;
import play.data.validation.ValidationError;

@Validated
public class SignupForm extends AppForm implements Validatable<List<ValidationError>>  {


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

	/**	姓 */
//	@Required
//	public String lastName;
//
//	/**	名 */
//	@Required
//	public String firstName;
//
//	/**	表示名 */
//	@Required
//	public String nickName;




	@Override
	public List<ValidationError> validate() {
		// TODO 自動生成されたメソッド・スタブ
		List<ValidationError> list = new ArrayList<>();
		/*
		 * 入力されたメールアドレスが現在使用中がどうかチェックする。
		 */
		User user = Ebean.find(User.class).where().eq("email", this.email).findUnique();
		if(user != null){
			list.add(new ValidationError("email", "すでに使用されているメールアドレスです。"));
		}
		return list;
	}

}
