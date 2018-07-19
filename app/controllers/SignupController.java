package controllers;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import forms.SignupForm;
import io.ebean.Ebean;
import models.User;
import play.cache.SyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

public class SignupController extends AppController {

	@Inject
    public SignupController(SyncCacheApi cache) {
        super(cache);
    }
    @Inject
    private FormFactory formFactory;

    @Override
    public Result get() {
        Form<SignupForm> form = formFactory.form(SignupForm.class);
        return ok(views.html.signup.render(form));
    }

    @Override
    public Result post() {
        Form<SignupForm> form = formFactory.form(SignupForm.class).bindFromRequest();
        if(form.hasErrors()){
        	System.out.println("サインアップできない");
            return badRequest(views.html.signup.render(form));
        }
            /*
             * ユーザ情報を新規登録する。
             */
        Ebean.beginTransaction();
        try{
            User user = new User();
            user.email = form.get().email;
            user.password = BCrypt.hashpw(form.get().password, BCrypt.gensalt());
            user.lastLoginedDate = LocalDateTime.now();
//            Ebean.insert(user);
//            Ebean.save(user);
            user.save();
            System.out.println("ここまできた"+ user.email + user.password);
            Ebean.commitTransaction();
            new LoginController(cache).setCacheUser(user);
            System.out.println("登録OK");
        }catch(Exception e){
            Ebean.rollbackTransaction();
            System.out.println("サインアップ失敗:" + e);
            return badRequest(views.html.signup.render(form));
        }finally {
            Ebean.endTransaction();
        }

        return redirect("/");
    }


//	public Result signup() {
//
//		return ok(views.html.login.render());
//	}

}
