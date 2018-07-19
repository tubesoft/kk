package controllers;

import javax.inject.Inject;

import play.cache.SyncCacheApi;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * ログイン情報を持ったページのためのコントローラーの抽象クラス（他のコントローラーはこれを継承）
 * @author inotazo
 *
 */
public abstract class AppController extends Controller {
	/** UUID セッションキー */
	public static final String UUID = common.global.AppActionCreator.UUID;
	/** キャッシュ */
	protected SyncCacheApi cache;
	/** キャッシュ保存時間 */
	protected int savetime = 60 * 15;

	@Inject
    public AppController(SyncCacheApi cache) {
        this.cache = cache;
    }
    /**
     * GET処理
     * @return　返却画面
     */
	public abstract Result get();

	/**
	 * POST処理
	 * @return　返却画面
	 */
	public abstract Result post();

	/**
	 * セッションにUUIDを設定
	 * @param uuid
	 */
	public static void setClientId(String uuid){
		session(UUID, uuid);
	}

	/**
	 * セッションからUUIDを取得
	 * @return
	 */
	public static String getClientId(){
		return session(UUID);
	}

	/**
	 * キャッシュにデータ保存
	 * @param keyword 保存キー
	 * @param object 保存データ
	 */
	protected void setCache(String keyword, Object object){
		this.cache.set(getClientId() + keyword, object, savetime);
	}

	/**
	 * キャッシュからデータ取得
	 * @param keyword 保存キー
	 * @return 保存データ
	 */
	protected Object getCache(String keyword){
		return this.cache.get(getClientId() + keyword);
	}

	/**
	 * キャッシュの保存データ消去
	 * @param keyword 保存キー
	 */
	protected void clearCache(String keyword){
		this.cache.remove(getClientId() + keyword);
	}
}
