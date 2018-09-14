package com.viet.follow.viewmodel

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import com.safframework.ext.then
import com.viet.follow.R
import com.viet.follow.repository.PersonalPageRepository
import com.viet.news.core.api.HttpResponse
import com.viet.news.core.domain.response.NewsListResponse
import com.viet.news.core.domain.response.UserInfoResponse
import com.viet.news.core.ext.toast
import com.viet.news.core.ui.App
import com.viet.news.core.ui.BaseActivity
import com.viet.news.core.ui.BaseFragment
import com.viet.news.core.viewmodel.BaseViewModel
import com.viet.news.core.vo.Resource

/**
 * @Description
 * @Author sean
 * @Email xiao.lu@magicwindow.cn
 * @Date 03/09/2018 1:37 PM
 * @Version
 */
class PersonalPageModel(var repository: PersonalPageRepository = PersonalPageRepository()) : BaseViewModel() {

    var page_number = 0
    var userId: String? = ""

    fun getlist4User(): LiveData<Resource<HttpResponse<NewsListResponse>>> {
        return repository.getlist4User(page_number, userId)
    }

    fun getUserInfo(owner: LifecycleOwner, function: (user: UserInfoResponse?) -> Unit) {
        repository.getUserInfo(userId).observe(owner, Observer {
            it?.data?.isOkStatus?.then({
                function(it.data?.data)
            }, {
                toast(App.instance.resources.getString(R.string.error_msg)).show()
            })
        })
    }

    fun follow(owner: LifecycleOwner, function: () -> Unit) {
        repository.follow(userId).observe(owner, Observer {
            it?.data?.isOkStatus?.then({
                function()
            }, {
                toast(App.instance.resources.getString(R.string.error_msg)).show()
            })
        })
    }

    /**
     * 点赞
     */
    private lateinit var owner:LifecycleOwner
    fun like(context: Context, contentId: String, function: (num: Int?) -> Unit) {
        if (context is BaseFragment) {
            owner = context
        }else if (context is BaseActivity) {
            owner = context
        }
        repository.like(contentId).observe(owner, Observer {
            it?.data?.isOkStatus?.then({
                function(it.data?.data)
            }, {
                toast(App.instance.resources.getString(R.string.error_msg)).show()
            })
        })
    }

    fun collection(context: Context, contentId: String, function: (num: Int?) -> Unit) {
        if (context is BaseFragment) {
            owner = context
        }else if (context is BaseActivity) {
            owner = context
        }
        repository.collection(contentId).observe(owner, Observer {
            it?.data?.isOkStatus?.then({
                function(it.data?.data)
            }, {
                toast(App.instance.resources.getString(R.string.error_msg)).show()
            })
        })
    }
}