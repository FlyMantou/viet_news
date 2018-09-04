package com.viet.news.di

import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDex
import com.squareup.leakcanary.LeakCanary
import com.viet.news.core.BaseApplication
import com.viet.news.core.utils.LanguageUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @Author Aaron
 * @Date 2018/5/7
 * @Email aaron@magicwindow.cn
 * @Description
 */
class App : BaseApplication(), HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        LanguageUtil.setApplicationLanguage(this)
        AppInjector.init(this)
        //方法已过期 看注释说是会自动调用，暂时不删
//        FacebookSdk.sdkInitialize(applicationContext);
//        AppEventsLogger.activateApp(this);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUtil.setLocal(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        //保存系统选择语言
        LanguageUtil.onConfigurationChanged(applicationContext)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    companion object {

        //方式1.通过标准代理实现late init
        var instance: BaseApplication by Delegates.notNull()
            private set
    }

}