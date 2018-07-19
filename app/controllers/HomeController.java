package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import common.secure.AppAuthenticator;
import models.User;
import play.cache.SyncCacheApi;
import play.mvc.Result;
import play.mvc.Security.Authenticated;

/**
 * ホーム画面
 */
@Singleton
public class HomeController extends AppController {
    @Inject
	public HomeController(SyncCacheApi cache) {
		super(cache);
	}

	@Override
	public Result get() {
		return ok(views.html.index.render(null));
	}

	@Authenticated(AppAuthenticator.class)
	public Result home() {
		/*
		 * ユーザ情報でホーム画面を作成し返却する。
		 */
		User user = new LoginController(cache).getCacheUser();
		return ok(views.html.index.render(user));
	}

	@Authenticated(AppAuthenticator.class)
	@Override
	public Result post() {  //ログアウト
		/*
		 * キャッシュからユーザ情報を消去し、
		 * サインイン画面にリダイレクトする。
		 */
		new LoginController(cache).clearCacheUser();
		return redirect("/get");
	}

}
