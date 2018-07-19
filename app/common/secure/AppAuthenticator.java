package common.secure;

import javax.inject.Inject;

import controllers.LoginController;
import models.User;
import play.cache.SyncCacheApi;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class AppAuthenticator extends Authenticator {
	private SyncCacheApi cache;
	@Inject
    public AppAuthenticator(SyncCacheApi cache) {
        this.cache = cache;
    }
	@Override
	public String getUsername(Context ctx) {
		/*
		 * キャッシュからユーザ情報を取得する。
		 * ユーザ情報が存在すれば認証中としアクセスを許可する。
		 */
		LoginController signinController = new LoginController(cache);
		User user = signinController.getCacheUser();
		if(user != null){
			signinController.setCacheUser(user);
			return user.toString();
		}else{
			return null;
		}
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		/*
		 * アクセスが許可されなかった場合、
		 * ログイン画面にリダイレクトする。
		 */
		return redirect("/login");
	}
}
