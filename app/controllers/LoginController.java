package controllers;

import java.time.LocalDateTime;

import javax.inject.Inject;

import forms.LoginForm;
import io.ebean.Ebean;
import models.User;
import play.cache.SyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;

public class LoginController extends AppController {



	@Inject
	private FormFactory formFactory;
	/** ユーザ情報キー */
	public static final String USER_KEY = "USER";

	@Inject
    public LoginController(SyncCacheApi cache) {
        super(cache);
    }
	@Override
	public Result get() {
		Form<LoginForm> form = formFactory.form(LoginForm.class);
		return ok(views.html.login.render(form, ""));
	}

	@Override
	public Result post() {
		Form<LoginForm> form = formFactory.form(LoginForm.class).bindFromRequest();
		if(form.hasErrors()){
			Messages messages = Http.Context.current().messages();
			return badRequest(views.html.login.render(form, messages.at("wrong-login-info")));
		}

		try{
			/*
			 * ユーザのログイン日時を更新する。
			 */
			Ebean.beginTransaction();
			User user = Ebean.find(User.class).where().eq("email", form.get().email).findUnique();
			user.lastLoginedDate = LocalDateTime.now();
			Ebean.update(user);
			Ebean.commitTransaction();
			setCacheUser(user);
		}catch(Exception e){
			Ebean.rollbackTransaction();
			Messages messages = Http.Context.current().messages();
			return badRequest(views.html.login.render(form, messages.at("login-error")));
		}finally {
			Ebean.endTransaction();
		}
		System.out.println("ログインできました");
		return redirect("/home");
	}

	/**
	 * キャッシュにユーザ情報保存
	 * @param user ユーザ情報
	 */
	public void setCacheUser(User user){
		setCache(USER_KEY, user);
		this.cache.set((USER_KEY + user.id), getClientId(), savetime);
	}

	/**
	 * キャッシュからユーザ情報取得
	 * @return ユーザ情報
	 */
	public User getCacheUser(){
		Object objectUser = getCache(USER_KEY);
		if(objectUser == null) return null;

		/*
		 * ユーザ情報を保存したプラウザのセッションUUIDと
		 * 現在アクセスしているセッションのUUIDを比較し、
		 * 異なる場合、ユーザ情報を取得させない。
		 */
		User user = User.class.cast(objectUser);
		String uuid = this.cache.get(USER_KEY + user.id).toString();
		if(!uuid.equals(getClientId())) return null;

		return user;
	}

	/**
	 * キャッシュのユーザ情報消去
	 */
	public void clearCacheUser(){
		clearCache(USER_KEY);
	}
}
